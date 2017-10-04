package com.ugureratalar.popularmoviesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by u. on 2.10.2017.
 */

public class Utils {

    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null) {
            if (connectivityManager.getActiveNetworkInfo().isConnected() && connectivityManager.getActiveNetworkInfo().isAvailable()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
