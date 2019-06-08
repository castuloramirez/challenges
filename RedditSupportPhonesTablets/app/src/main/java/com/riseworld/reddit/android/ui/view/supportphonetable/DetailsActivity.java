package com.riseworld.reddit.android.ui.view.supportphonetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Web view for the url
 **/
public class DetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if ((extrasBundle != null) && (!extrasBundle.isEmpty())) {
            String url = extrasBundle.getString("url");
            WebView mWebView = new WebView(this);
            mWebView.loadUrl(url);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

            this.setContentView(mWebView);
        }
    }
}