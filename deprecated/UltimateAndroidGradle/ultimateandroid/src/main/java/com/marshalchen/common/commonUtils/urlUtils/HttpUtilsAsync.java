package com.marshalchen.common.commonUtils.urlUtils;

import android.content.Context;

import com.loopj.android.http.SyncHttpClient;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * HttpUtils which use asynchoronous method to help you use network method without
 * using an addtional Thread
 * <p>{@link #get(String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #post(String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #getUseCookie(android.content.Context, String, java.util.HashMap, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #getWithCookie(android.content.Context, String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #postWithCookie(android.content.Context, String, com.loopj.android.http.RequestParams, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 * <p>{@link #postUseCookie(android.content.Context, String, java.util.HashMap, com.loopj.android.http.AsyncHttpResponseHandler)}</p>
 */
public class HttpUtilsAsync {

    private static int timeout = 25000;

    public static int getTimeout() {
        return timeout;
    }

    public static void setTimeout(int timeout) {
        HttpUtilsAsync.timeout = timeout;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Perform a HTTP GET request with {@link com.loopj.android.http.RequestParams}
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(timeout);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Perform a HTTP POST request with {@link com.loopj.android.http.RequestParams}
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(timeout);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Perform a HTTP GET request, without any parameters.
     *
     * @param url
     * @param responseHandler
     */
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(timeout);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    /**
     * Perform a HTTP GET request with cookie which generate by own context
     *
     * @param context
     * @param url
     * @param responseHandler
     */
    public static void getWithCookie(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        //  myCookieStore.clear();
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), responseHandler);

    }

    /**
     * Perform a HTTP GET request with cookies which are defined in hashmap
     *
     * @param context
     * @param url
     * @param hashMap
     * @param responseHandler
     */
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


    /**
     * Perform a HTTP GET request with cookie which generate by own context
     *
     * @param context
     * @param url
     * @param responseHandler
     */
    public static void getWithCookie(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Perform a HTTP POST request, without any parameters.
     *
     * @param url
     * @param responseHandler
     */
    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    /**
     * Perform a HTTP POST request with cookie which generate by own context
     *
     * @param context
     * @param url
     * @param responseHandler
     */
    public static void postWithCookie(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        //  myCookieStore.clear();
        client.setCookieStore(myCookieStore);
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    /**
     * Perform a HTTP POST request with cookie which generate by own context
     *
     * @param context
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void postWithCookie(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Perform a HTTP POST request with cookies which are defined in hashmap
     *
     * @param context
     * @param url
     * @param hashMap
     * @param responseHandler
     */
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

    /**
     * To get the true url.
     * If you want to use some relative url,you should override this method.
     *
     * @param relativeUrl
     * @return the absolute url
     */
    protected static String getAbsoluteUrl(String relativeUrl) {
        return relativeUrl;
    }

    /**
     * To get the url with params.
     *
     * @param originUrl
     * @param hashMap
     * @return the url with params
     */
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

    /**
     * Upload files with {@link com.loopj.android.http.SyncHttpClient}
     *
     * @param url
     * @param paramsList
     * @param fileParams
     * @param files
     * @param responseHandler
     */
    public static void uploadFiles(String url, List<NameValuePair> paramsList, String fileParams, List<File> files, AsyncHttpResponseHandler responseHandler) throws Exception {
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();

        if (BasicUtils.judgeNotNull(paramsList)) {
            for (NameValuePair nameValuePair : paramsList) {
                params.put(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
        if (BasicUtils.judgeNotNull(files))
            params.put(fileParams, files);

        syncHttpClient.setTimeout(timeout);
        syncHttpClient.post(url, params, responseHandler);
    }

    /**
     * Upload file with {@link com.loopj.android.http.SyncHttpClient}
     *
     * @param url
     * @param paramsList
     * @param fileParams
     * @param file
     * @param responseHandler
     * @throws FileNotFoundException
     */
    public static void uploadFile(String url, List<NameValuePair> paramsList, String fileParams, File file, AsyncHttpResponseHandler responseHandler) throws FileNotFoundException {
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        if (BasicUtils.judgeNotNull(paramsList)) {
            for (NameValuePair nameValuePair : paramsList) {
                params.put(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
        if (BasicUtils.judgeNotNull(file))
            params.put(fileParams, file);
        syncHttpClient.setTimeout(timeout);
        syncHttpClient.post(url, params, responseHandler);
    }
}
