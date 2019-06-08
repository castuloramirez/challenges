package com.riseworld.reddit.android.ui.view.supportphonetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riseworld.reddit.android.ui.adapter.MyRecyclerViewAdapter;
import com.riseworld.reddit.android.ui.common.Controller;
import com.riseworld.reddit.android.util.GlobalREDDIT;

public class ContentListFragment extends Fragment {
    private static String LOG_TAG = "ContentListFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_list_view, null, false);
        //Pull to refresh
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                refreshContent(mSwipeRefreshLayout);
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) this.getActivity().getApplicationContext();
        Controller ctrl = globalOCTAVE.getCtrl();
        if ((ctrl.getListData() != null) && (ctrl.getListData().size() > 0)) {
            mAdapter = new MyRecyclerViewAdapter(ctrl.getListData());
            mRecyclerView.setAdapter(mAdapter);
            addListener();
        }
        return root;
    }


    /**
     * @param mSwipeRefreshLayout Refresh the content of the RecyclerView
     */
    private void refreshContent(final SwipeRefreshLayout mSwipeRefreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Controller ctrl = new Controller();
                    ctrl.getDataSetAsStringArray();
                    final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) getActivity().getApplicationContext();
                    globalOCTAVE.setCtrl(ctrl);
                    ctrl.fillDatabase(getActivity().getApplicationContext(), ctrl.getListData());
                    if ((ctrl.getListData() != null) && (ctrl.getListData().size() > 0)) {
                        mAdapter = new MyRecyclerViewAdapter(ctrl.getListData());
                        mRecyclerView.setAdapter(mAdapter);
                        addListener();
                    }
                } catch (Exception ignored) {
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 4000);

    }


    protected void addListener() {
        if (mAdapter != null) {
            ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                    .MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Log.i(LOG_TAG, " Clicked on Item " + position);
                    String url = ((MyRecyclerViewAdapter) mAdapter).getItemDataObject(position).getUrl();
                    Log.i(LOG_TAG, " Clicked on Item url " + url);
                    ((MainFragment) getParentFragment()).contentListItemClicked(position + 1);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        //noinspection deprecation
        super.onAttach(activity);
        //  ((MainActivity)getActivity()).setUpNavOff();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
