package com.marshalchen.common.commonUtils.urlUtils;

import android.util.Log;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * HttpUtils which use apache http client
 */
public class HttpUtils {

    public static String getResponseFromPostUrl(String url, List<NameValuePair> params) throws Exception {

        String result = null;
        // 新建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        // 设置字符集
        HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        // 设置参数实体
        httpPost.setEntity(entity);
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        //连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        //请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        try {
            // 获取HttpResponse实例
            HttpResponse httpResp = httpClient.execute(httpPost);
            // 判断是够请求成功
            if (httpResp.getStatusLine().getStatusCode() == 200) {
                // 获取返回的数据
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

    /**
     * Update Files via Multipart
     *
     * @param url
     * @param paramsList
     * @param fileParams
     * @param file
     * @param files
     * @return status
     * @deprecated
     */
    public static String uploadFilesMPE(String url, List<NameValuePair> paramsList, String fileParams, File file, File... files) {
        String result = "";
        try {
            DefaultHttpClient mHttpClient;
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
            mHttpClient = new DefaultHttpClient(params);
            HttpPost httpPost = new HttpPost(url);

            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (BasicUtils.judgeNotNull(paramsList)) {
                for (NameValuePair nameValuePair : paramsList) {
                    multipartEntity.addPart(nameValuePair.getName(), new StringBody(nameValuePair.getValue()));
                }
            }

            multipartEntity.addPart(fileParams, new FileBody(file));
            for (File f : files) {
                multipartEntity.addPart(fileParams, new FileBody(f));
            }
            httpPost.setEntity(multipartEntity);
            HttpResponse httpResp = mHttpClient.execute(httpPost);
            // 判断是够请求成功
            if (httpResp.getStatusLine().getStatusCode() == 200) {
                // 获取返回的数据
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                Logs.d("HttpPost success :");
                Logs.d(result);
            } else {
                Logs.d("HttpPost failed" + "    " + httpResp.getStatusLine().getStatusCode() + "   " + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
                result = "HttpPost failed";
            }
        } catch (ConnectTimeoutException e) {
            result = "ConnectTimeoutException";
            Logs.e("HttpPost overtime:  " + "");
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
            result = "Exception";
        }

        return result;
    }

    public static String uploadFiles(String url, List<NameValuePair> paramsList, String fileParams, File[] files) throws Exception {
        List<File> fileList = new ArrayList<>();

        for (File f : files) {
            fileList.add(f);
        }
        return uploadFiles(url, paramsList, fileParams, fileList);

    }

    public static String uploadFiles(String url, List<NameValuePair> paramsList, String fileParams, List<File> files) throws Exception {
        String result = "";
        try {
            DefaultHttpClient mHttpClient;
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
            mHttpClient = new DefaultHttpClient(params);
            HttpPost httpPost = new HttpPost(url);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            if (BasicUtils.judgeNotNull(paramsList)) {
                for (NameValuePair nameValuePair : paramsList) {
                    entityBuilder.addTextBody(nameValuePair.getName(), nameValuePair.getValue()
                           );
                }
            }

            // entityBuilder.addBinaryBody(fileParams, file);
            for (File f : files) {
                if (f != null && f.exists())
                    entityBuilder.addBinaryBody(fileParams, f);
            }
            HttpEntity entity = entityBuilder.build();
            httpPost.setEntity(entity);
            HttpResponse httpResp = mHttpClient.execute(httpPost);

            if (httpResp.getStatusLine().getStatusCode() == 200) {

                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                Logs.d("HttpPost success :");
                Logs.d(result);
            } else {
                Logs.d("HttpPost failed" + "    " + httpResp.getStatusLine().getStatusCode() + "   " + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
                result = "HttpPost failed";
            }
        } catch (ConnectTimeoutException e) {
            result = "ConnectTimeoutException";
            Logs.e("HttpPost overtime:  " + "");
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
            result = "Exception";
        }

        return result;

    }

    public static String uploadFiles(String url, List<NameValuePair> paramsList, String fileParams, File file, File... files) throws Exception {
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        for (File f : files) {
            fileList.add(f);
        }
        return uploadFiles(url, paramsList, fileParams, fileList);
    }
}
