package io.anyline.android.challenge.animation.common;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import io.anyline.android.challenge.animation.R;

public class DialogHelper {

    public  static void alertView(String message , Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle( "Hello" )
                .setIcon(R.drawable.icon)
                .setMessage(message)
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }
}
