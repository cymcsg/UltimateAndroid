package com.marshalchen.common.commonUtils.urlUtils;

import android.content.Context;
import com.loopj.android.http.*;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HttpUtils which use asynchoronous method to help you use network method without
 * using an addtional Thread
 * <p>
 * {@link #get(String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #post(String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #getUseCookie(android.content.Context, String, java.util.HashMap, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #getWithCookie(android.content.Context, String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #postWithCookie(android.content.Context, String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #postUseCookie(android.content.Context, String, java.util.HashMap, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 */
public class HttpUtilsAsync {
    private static final String BASE_URL = "http://api.fss.com/1/";
    private static final int TIME_OUT = 25000;
    private AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIME_OUT);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIME_OUT);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIME_OUT);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void getWithCookie(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        //  myCookieStore.clear();
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), responseHandler);
//        Iterator iterator = myCookieStore.getCookies().iterator();
//        while (iterator.hasNext()) {
//            Cookie cookie = (Cookie) iterator.next();
//            Logs.d("cookie name--" + cookie.getName());
//            Logs.d("cookie value--" + cookie.getValue());
//        }

//        BasicCookieStore basicCookieStore = new BasicCookieStore();
//
//        client.setCookieStore(basicCookieStore);
//        client.get(getAbsoluteUrl(url), responseHandler);
//        Iterator iterator = basicCookieStore.getCookies().iterator();
//        while (iterator.hasNext()) {
//            Cookie cookie = (Cookie) iterator.next();
//            Logs.d("sssss" + cookie.getName());
//            Logs.d("sssss" + cookie.getValue());
//        }


    }

    public static void getUseCookie(Context context, String url, HashMap hashMap, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        if (BasicUtils.judgeNotNull(hashMap)) {
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                Cookie cookie = new BasicClientCookie(key.toString(), value.toString());
                myCookieStore.addCookie(cookie);
            }
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void getWithCookie(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    public static void postWithCookie(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        //  myCookieStore.clear();
        client.setCookieStore(myCookieStore);
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    public static void postWithCookie(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postUseCookie(Context context, String url, HashMap hashMap, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        if (BasicUtils.judgeNotNull(hashMap)) {
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                Cookie cookie = new BasicClientCookie(key.toString(), value.toString());
                myCookieStore.addCookie(cookie);
            }
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setCookieStore(myCookieStore);
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
//        return BASE_URL + relativeUrl;
        return relativeUrl;
    }

    public static String getUrlFromHashMap(String originUrl, HashMap hashMap) {
        String returnUrl = originUrl;

        if (BasicUtils.judgeNotNull(hashMap)) {
            returnUrl = returnUrl + "?";
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                returnUrl += key + "=" + value + "&";
            }
            if (returnUrl.endsWith("&")) {
                returnUrl = returnUrl.substring(0, returnUrl.length() - 1);
            }
        }
        Logs.d(returnUrl);
        return returnUrl;
    }

    public static void uploadFiles(String url, List<NameValuePair> paramsList, String fileParams, List<File> files, AsyncHttpResponseHandler responseHandler) {
        SyncHttpClient syncHttpClient = new SyncHttpClient();

        RequestParams params = new RequestParams();
        try {
            if (BasicUtils.judgeNotNull(paramsList)) {
                for (NameValuePair nameValuePair : paramsList) {
                    params.put(nameValuePair.getName(), nameValuePair.getValue());
                }
            }
            if (BasicUtils.judgeNotNull(files))
                params.put(fileParams, files);
        } catch (Exception e) {
            Logs.e(e, "");
        }
        syncHttpClient.setTimeout(TIME_OUT);
        syncHttpClient.post(url, params, responseHandler);

    }
}
