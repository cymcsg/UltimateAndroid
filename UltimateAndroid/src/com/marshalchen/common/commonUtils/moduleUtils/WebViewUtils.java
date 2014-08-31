package com.marshalchen.common.commonUtils.moduleUtils;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.marshalchen.common.commonUtils.logUtils.Logs;

/**
 * Some default value of webview
 * User: cym
 * Date: 13-10-31
 */
//WebViewUtils
public class WebViewUtils {
    private static WebView webView;

    public static WebView getCustomWebView(Context context, String appCacheDir) {
        webView = new WebView(context);

        WebSettings wSet = webView.getSettings();
        // wSet.setAppCacheMaxSize();
        wSet.setJavaScriptEnabled(true);
        wSet.setDomStorageEnabled(true);
        // String appCacheDir = this.getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        wSet.setAppCachePath(appCacheDir);
        wSet.setAllowFileAccess(true);
        wSet.setAppCacheEnabled(true);
        return webView;
    }

    ;

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
        //    CookieSyncManager.getInstance().stopSync();
    }
}
