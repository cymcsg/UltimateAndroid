package com.marshalchen.common.commonUtils.urlUtils;

import android.util.Log;
import com.marshalchen.common.commonUtils.logUtils.Logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.CoreConnectionPNames;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

@Deprecated
/**
 * HttpUtils which use apache http client.
 * Use {@link com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync} instead.
 */
public class HttpUtils {

    public static String getResponseFromPostUrl(String url, List<NameValuePair> params) throws Exception {

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        httpPost.setEntity(entity);

        HttpClient httpClient = new DefaultHttpClient();

        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);

        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        try {

            HttpResponse httpResp = httpClient.execute(httpPost);

            if (httpResp.getStatusLine().getStatusCode() == 200) {

                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                Logs.d("HttpPost success :");
                Logs.d(result);
            } else {
                Logs.d("HttpPost failed" + "    " + httpResp.getStatusLine().getStatusCode() + "   " + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
                result = "connect_failed";
            }
        } catch (ConnectTimeoutException e) {
            result = "";
            Logs.e("HttpPost overtime:  " + "");
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
            result = "";
        }
        return result;
    }


    public static String getResponseFromGetUrl(String url,
                                               String params) throws Exception {
        Log.d("Chen", "url--" + url);
        if (null != params && !"".equals(params)) {

            url = url + "?";

            String[] paramarray = params.split(",");

            for (int index = 0; null != paramarray && index < paramarray.length; index++) {

                if (index == 0) {

                    url = url + paramarray[index];
                } else {

                    url = url + "&" + paramarray[index];
                }

            }

        }

        HttpGet httpRequest = new HttpGet(url);

//        httpRequest.addHeader("Cookie", logininfo);

        HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        int timeoutConnection = 30000;
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = 30000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);

        // DefaultHttpClient httpclient = new DefaultHttpClient();
        StringBuffer sb = new StringBuffer();


        try {
            HttpResponse httpResponse = httpclient.execute(httpRequest);

            String inputLine = "";
            // Log.d("Chen","httpResponse.getStatusLine().getStatusCode()"+httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                InputStreamReader is = new InputStreamReader(httpResponse
                        .getEntity().getContent());
                BufferedReader in = new BufferedReader(is);
                while ((inputLine = in.readLine()) != null) {

                    sb.append(inputLine);
                }

                in.close();

            } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_MODIFIED) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return sb.toString();

    }

    public static void enableHttpResponseCache(String cachePath) {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(cachePath, "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception httpResponseCacheNotAvailable) {
            Logs.d("HTTP response cache is unavailable.");
        }
    }


}
