package com.riseworld.reddit.android.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.riseworld.reddit.android.ui.util.RedditRiseFileHelper;
import com.riseworld.reddit.android.ui.view.supportphonetable.ExceptionHandlerActivity;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * error info class
 **/

public class RedditRiseExceptionHandler implements
        java.lang.Thread.UncaughtExceptionHandler {

    private final Activity myContext;


    public RedditRiseExceptionHandler(Activity context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append(" CAUSE OF ERROR: \n\n");
        errorReport.append(stackTrace.toString());

        errorReport.append("\n DEVICE INFORMATION: \n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        String LINE_SEPARATOR = "\n";
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n FIRMWARE: \n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK_INT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        RedditRiseFileHelper.writeCrashToFile(errorReport.toString());

        Intent intent = new Intent(myContext, ExceptionHandlerActivity.class);
        intent.putExtra("error", errorReport.toString());
        myContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}