package com.riseworld.reddit.android.ui.view.supportphonetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.riseworld.reddit.android.util.GlobalREDDIT;

public class ContentDetailFragment extends Fragment {
    // extras
    public static final String DETAIL_ITEM_POSITION = "DETAIL_ITEM_POSITION";
    // state
    public int detailItemPosition = -1;
    // views

    public View root;
    private final Context context = getActivity();

    /**
     * @param i
     * @param context
     * @return
     */
    public static Fragment newInstance(int i,Context context ) {
        Bundle bundle = new Bundle();
        bundle.putInt(DETAIL_ITEM_POSITION, i);
        boolean isSelf = checkSelfPost(i, context);
        Fragment fragment = null;
        if(isSelf) {
            fragment = new ContentDetailFragment();
        }else{
            fragment = new TabFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) {
            detailItemPosition = getArguments().getInt(MainFragment.DETAIL_ITEM_POSITION);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.content_detail_view, null, false);
        final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) this.getActivity().getApplicationContext();
        String url = globalOCTAVE.getCtrl().getURL(detailItemPosition);
        if((getArguments().getString("2")!=null)&&((getArguments().getString("2").equals("2")))){
                url = "https://www.reddit.com"+ globalOCTAVE.getCtrl().getPermalink(detailItemPosition);
        }
        setContentListPosition(detailItemPosition);
        WebView myWebView = (WebView)root.findViewById(R.id.mywebview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl(url);
        return root;
    }

    /**
     * @param pDetailItemPosition
     * @param context
     * @return
     */
    public static boolean checkSelfPost(int pDetailItemPosition,Context context ){
        final GlobalREDDIT globalOCTAVE = (GlobalREDDIT)context.getApplicationContext();
        String url = globalOCTAVE.getCtrl().getURL(pDetailItemPosition);
        String permalink = globalOCTAVE.getCtrl().getPermalink(pDetailItemPosition);
        if (url.contains(permalink)) {
            System.out.println("It's a match!");
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        //noinspection deprecation
        super.onAttach(activity);
        //  ((MainActivity)getActivity()).setUpNavOn();
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

    public void setContentListPosition(int contentListPosition) {
        this.detailItemPosition = contentListPosition;
       // textView.setText("Item " + detailItemPosition);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}