package com.marshalchen.common.commonUtils.urlUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;
@Deprecated
/**
 * Use {@link com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync} instead.
 */
public class HttpUtils_Deprecated {

    public static String TAG = "Chen";
    public static String rst = "";

    /*
     */
    public static String getResponseFromPostUrl(String url, String logininfo, List<NameValuePair> params) throws Exception {

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
                Logs.d(result);
            } else {
                Logs.d("HttpPost方式请求失败" + "    " + httpResp.getStatusLine().getStatusCode() + "   " + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
                result = "connect_failed";
            }
        } catch (ConnectTimeoutException e) {
            result = "over_time";
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
            Logs.e(e.getMessage(), "");
            result = "over_time";
        }
        return result;
    }


    /*
     * 发送POST请求，通过URL和参数获取服务器反馈应答，通过返回的应答对象 在对数据头进行分析(如获得报文头 报文体等)
     */
    public static Map getResponseStatusCodeFromPostUrl(String url,
                                                       String logininfo, List<NameValuePair> params) throws Exception {

        Map rstmap = new HashMap();

        if (url.contains("http")) {

        } else {
            url = "http://t.qingdaonews.com" + url;
        }

        HttpPost httpRequest = new HttpPost(url);
        httpRequest.addHeader("Cookie", logininfo);
        if (null != params && params.size() > 0) {

			/* 添加请求参数到请求对象 */
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        }

        HttpResponse httpResponse = new DefaultHttpClient()
                .execute(httpRequest);
        StringBuffer sb = new StringBuffer();
        String inputLine = "";
        rstmap.put("code", httpResponse.getStatusLine().getStatusCode());
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            InputStreamReader is = new InputStreamReader(httpResponse
                    .getEntity().getContent());
            BufferedReader in = new BufferedReader(is);
            while ((inputLine = in.readLine()) != null) {

                sb.append(inputLine);
            }

            in.close();

            rstmap.put("content", sb.toString());

        } else {

            rstmap.put("content", null);
        }

        return rstmap;

    }


    public static String GetJson_Cookie(String httpUrl) {
        String strResult = null;
        try {
            HttpGet httpRequest = new HttpGet(httpUrl);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            StringBuffer sb = new StringBuffer();
            String inputLine = "";
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Header[] hds = httpResponse.getAllHeaders();

                int isok = 0;

                for (int index = 0; index < hds.length; index++) {

                    if ("Set-Cookie".equals(hds[index].getName())) {

                        String value = hds[index].getValue();

                        String[] vs = value.split(";");

                        for (int i = 0; i < vs.length; i++) {

                            String[] vss = vs[i].split("=");

                            if ("member".equals(vss[0])) {
                                rst = vs[i] + ";";

                                isok++;
                            }
                        }

                    }

                }
            }
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else {

            }
        } catch (Exception e) {

            strResult = "net_ex";
        }
        return strResult;
    }


    public static String getResponseFromGetUrl(String url, String logininfo,
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

        httpRequest.addHeader("Cookie", logininfo);

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
                return "not_modify";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "net_error";
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return sb.toString();

    }


    public static byte[] loadImageFromUrl(String url) {

        InputStream i = null;
        byte[] filename = null;
        try {

            byte[] dbfilename = null;

            DefaultHttpClient httpClient = new DefaultHttpClient();
            String geturl = url;
            HttpGet request = new HttpGet(geturl);
            request.setHeader("referer", "http://pic.qingdaonews.com");
            HttpResponse response = httpClient.execute(request);
            i = response.getEntity().getContent();

            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();

            int ch;
            while ((ch = i.read()) != -1) {
                bytestream.write(ch);
            }

            filename = bytestream.toByteArray();

            bytestream.close();
            i.close();

            return filename;

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return filename;
    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImageFromUrl(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(12 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStreamFromUrl(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(12 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    public static int getResourceLength(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(12 * 1000);
        conn.setRequestMethod("GET");
        return conn.getContentLength();
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    public int uploadFile(String sourceFileUri, String targetUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        int serverResponseCode = 0;
        if (!sourceFile.isFile()) {
            return -1;
        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(targetUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Logs.d("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {


                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                Logs.e(ex, "");
                ex.printStackTrace();

                return 0;
            } catch (Exception e) {

                Logs.e(e, "");
                Log.e("Upload file to server Exception", "Exception : "
                        + e.getMessage(), e);
                return 0;
            }

            return serverResponseCode;

        } // End else block
    }


    public static void enableHttpResponseCache(String cachePath) {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(cachePath, "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception httpResponseCacheNotAvailable) {
            Log.d(TAG, "HTTP response cache is unavailable.");
        }
    }

}
