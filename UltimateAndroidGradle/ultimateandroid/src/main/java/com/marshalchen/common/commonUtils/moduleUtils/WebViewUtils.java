package com.marshalchen.common.commonUtils.moduleUtils;

import android.content.Context;
import android.webkit.*;
import com.marshalchen.common.commonUtils.logUtils.Logs;

/**
 * Some utils of WebView
 */

public class WebViewUtils {

    /**
     * A webview setting which enable JavaScript ,DomStorage and file access.
     * @param webView
     * @param appCacheDir
     * @return
     */
    public static WebSettings getWebSettings(WebView webView, String appCacheDir) {
        WebSettings wSet = webView.getSettings();
        // wSet.setAppCacheMaxSize();
        wSet.setJavaScriptEnabled(true);
        wSet.setDomStorageEnabled(true);
        // String appCacheDir = this.getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        wSet.setAppCachePath(appCacheDir);
        wSet.setAllowFileAccess(true);
        wSet.setAppCacheEnabled(true);
        wSet.setCacheMode(WebSettings.LOAD_DEFAULT);
        return wSet;

    }

    /**
     * @deprecated
     * Forces sync manager to sync now
     * @param context
     * @param domainNameUrl
     * @param strings
     */
    public static void syncCookie(Context context, String domainNameUrl, String... strings) {
        CookieSyncManager.createInstance(context);
        //CookieSyncManager.getInstance().startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        for (String s : strings) {
            cookieManager.setCookie(domainNameUrl, s);

        }
        Logs.d(cookieManager.getCookie(domainNameUrl));
        CookieSyncManager.getInstance().sync();
    }

    /**
     * Get a historical list of webview
     * @param webView
     * @return
     */
    public static WebBackForwardList getHistoricalList(WebView webView) {
        return webView.copyBackForwardList();
    }

    /**
     * Get the latest url of webview
     * @param webView
     * @return
     */
    public static String getHistoricalUrl(WebView webView) {
        WebBackForwardList webBackForwardList = getHistoricalList(webView);
        return webBackForwardList.getItemAtIndex(webBackForwardList.getCurrentIndex() - 1).getUrl();

    }
}
