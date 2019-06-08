package com.riseworld.reddit.android.ui.view.supportphonetable;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.riseworld.reddit.android.ui.common.RedditRiseExceptionHandler;

/**
 * This class helps to handle the errors, but It is still in progress
 **/
public class ExceptionHandlerActivity extends Activity {
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new RedditRiseExceptionHandler(this));
        /*setContentView(R.layout.activity_reddit_exception_handler);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        error = (TextView) findViewById(R.id.error);
        error.setText(getIntent().getStringExtra("error"));*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                closeApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeApp() {
        finish();
        ActivityManager activityMgr = (ActivityManager) this.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses("com.riseworld.reddit.android");
        System.exit(0);
    }
}