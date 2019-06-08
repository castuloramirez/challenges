package com.riseworld.reddit.android.ui.view.supportphonetable;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

public class TabFragment extends Fragment implements ViewPager.OnPageChangeListener{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        myAdapter=new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(this);



        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        Fragment mFragment = new ContentListFragment();
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.replace(R.id.container, mFragment);
        ft.addToBackStack(null);
        ft.commit();
        return x;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Fragment fragment = myAdapter.getItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("DETAIL_ITEM_POSITION", getArguments().getInt(MainFragment.DETAIL_ITEM_POSITION));
            Fragment fragment=null;
            switch (position){
                case 0:
                    bundle.putString("2","1");
                    fragment= new ContentDetailFragment();
                    fragment.setArguments(bundle);
                    return fragment;
                case 1 :
                    bundle.putString("2","2");
                    fragment= new ContentDetailFragment();
                    fragment.setArguments(bundle);
                    return fragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Details";
                case 1 :
                    return "Comments";

            }
            return null;
        }
    }

}