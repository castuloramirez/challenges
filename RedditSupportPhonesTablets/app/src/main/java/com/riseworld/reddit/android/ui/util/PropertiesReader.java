package com.riseworld.reddit.android.ui.util;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the property file from the assets folder
 **/
public class PropertiesReader {
    private static Properties properties;

    public static void Init(Context context) {
        try {
            properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        try {
            return properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param key
     * @param value
     */
    public static void setProperty(String key, String value) {
        try {
            properties.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}