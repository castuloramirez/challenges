package com.riseworld.reddit.android.util;

import android.app.Application;
import android.os.StrictMode;

import com.riseworld.reddit.android.sqlite.Connection;
import com.riseworld.reddit.android.sqlite.RedditRiseDBHandler;
import com.riseworld.reddit.android.ui.common.Controller;
import com.riseworld.reddit.android.ui.util.PropertiesReader;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a singleton so only one instance of a class is instantiated , duty is provide a set of services.
 **/

public class GlobalREDDIT extends Application {

    private static Map data = new HashMap();
    private RedditRiseDBHandler redditRiseDBHandler = new RedditRiseDBHandler(this, null, null, 1);
    public Map getData() {
        return data;
    }

    public Controller getCtrl() {
        return ctrl;
    }

    public void setCtrl(Controller ctrl) {
        this.ctrl = ctrl;
    }

    private Controller ctrl;

    @Override
    public void onCreate() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate();
        initConfig();
    }

    public void initConfig() {
        initSetupRedditRise();
    }
    private void initSetupRedditRise() {
        PropertiesReader.Init(this);
    }

    public RedditRiseDBHandler getRedditRiseDBHandler() {
        return redditRiseDBHandler;
    }

    public void connect(String title, String autor, String url, String permalink) {
        Connection con = new Connection();
        con.setTitle(title);
        con.setAutor(autor);
        con.setUrl(url);
        con.setPermalink(permalink);
        redditRiseDBHandler.addConnection(con);
    }
}