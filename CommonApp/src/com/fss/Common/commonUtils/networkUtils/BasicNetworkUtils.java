package com.fss.Common.commonUtils.networkUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created with IntelliJ IDEA.
 * User: cym
 * Date: 13-9-22
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
public class BasicNetworkUtils {
    public static boolean checkNetwork(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }

}
