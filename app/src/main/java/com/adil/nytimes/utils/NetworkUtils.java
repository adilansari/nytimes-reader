package com.adil.nytimes.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by adil on 2/14/16.
 */
public class NetworkUtils {
    private static boolean isNetworkAvailable(Context context){
        ConnectivityManager mgr =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = mgr.getActiveNetworkInfo();
        return nwInfo != null && nwInfo.isConnectedOrConnecting();
    }

    public static boolean isOnline(Context context){
        return isNetworkAvailable(context);
    }

    public static void verifyConnectivity(Context context){
        if (!isOnline(context))
            Toast.makeText(context, "No internet connection detected", Toast.LENGTH_LONG).show();
    }

}
