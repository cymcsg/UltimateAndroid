package com.marshalchen.ua.common.commonUtils.networkUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


/**
 * Check network status
 */
public class NetworkUtils {

    /**
     * Check if the device has connected network.
     *
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context) {
        if (getNetworkInfo(context) != null && getNetworkInfo(context).isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Check the network is Wifi or not
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            return getNetworkInfo(context).getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    /**
     * Check the network is LTE or not
     *
     * @param context
     * @return
     */
    public static boolean isLTE(Context context) {
        return getNetworkInfo(context) != null && getNetworkInfo(context).isAvailable() && getNetworkInfo(context).getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }


    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }
}
