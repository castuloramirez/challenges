package com.riseworld.reddit.android.util;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * This class checks the Android network and internet connectivity status
 **/
public class DetectConnection {
    /**
     *
     * @param context
     * @return
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
    }
}