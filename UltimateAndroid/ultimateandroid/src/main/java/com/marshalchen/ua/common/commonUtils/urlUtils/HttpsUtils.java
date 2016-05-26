package com.marshalchen.ua.common.commonUtils.urlUtils;


import android.content.Context;

import com.marshalchen.ua.common.commonUtils.logUtils.Logs;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import java.io.*;

import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Send https request
 */
public class HttpsUtils {

    /**
     * Get X509 Certificate
     *
     * @param certFilePath
     * @return Certificate
     */
    public static Certificate getX509CertifaceteFromCrtFile(String certFilePath) {
        InputStream inStream = null;
        X509Certificate cert = null;
        try {
            inStream = new FileInputStream(certFilePath);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) cf.generateCertificate(inStream);
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
        } finally {
            return cert;
        }
    }

    /**
     * Build SSLSocketFactory using certificate file from assets.
     *
     * @param context
     * @param certFilePath
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory(Context context, String certFilePath) throws NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException, CertificateException, IOException {

        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        InputStream is = context.getResources().getAssets().open(certFilePath);
        InputStream caInput = new BufferedInputStream(is);
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            // System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext contexts = SSLContext.getInstance("TLS");
        contexts.init(null, tmf.getTrustManagers(), null);
        return contexts.getSocketFactory();


    }

    /**
     * Build SSLSocketFactory using certificate file from assets.
     * @param context
     * @param certFilePath
     * @param key
     * @param keyPassword
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws CertificateException
     * @throws IOException
     */
    public static SSLSocketFactory getSSLSocketFactory(Context context, String certFilePath, InputStream key, String keyPassword) throws NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException, CertificateException, IOException {

        InputStream is = context.getResources().getAssets().open(certFilePath);
        return getSSLSocketFactory(is, key, keyPassword);

    }

    /**
     * Build SSLSocketFactory using certificate InputStream
     * @param certificates
     * @param key
     * @param keyPassword
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws CertificateException
     * @throws IOException
     */
    public static SSLSocketFactory getSSLSocketFactory(InputStream certificates, InputStream key, String keyPassword) throws NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException, CertificateException, IOException {


        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        InputStream caInput = new BufferedInputStream(certificates);
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        KeyManagerFactory kmf = null;
        if (key != null && keyPassword != null) {
            kmf = getKeyManagerFactory(key, keyPassword);
        }
        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext contexts = SSLContext.getInstance("TLS");
        contexts.init(kmf == null ? null : kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return contexts.getSocketFactory();

    }

    /**
     * @deprecated
     */
    public static void sendWithSSlSocket(String urlLink) {
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        URL url = null;
        try {
            url = new URL(urlLink);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(sslsocketfactory);
            InputStream inputstream = conn.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                System.out.println("Received " + string);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
        }

    }

    /**
     * Send https request with certificate file in assets.
     *
     * @param context
     * @param certFilePath
     * @param uri
     * @return The response from server.
     * @throws Exception
     */
    public static String sendWithSSlSocketWithCrt(Context context, String certFilePath, String uri) throws Exception {
        SSLSocketFactory sslsocketfactory = getSSLSocketFactory(context, certFilePath);
        URL url;
        InputStream inputstream = null;
        InputStreamReader inputstreamreader = null;
        BufferedReader bufferedreader = null;
        StringBuilder content = new StringBuilder("");
        try {
            url = new URL(uri);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(sslsocketfactory);
            inputstream = conn.getInputStream();
            inputstreamreader = new InputStreamReader(inputstream);
            bufferedreader = new BufferedReader(inputstreamreader);
            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                content.append(string);

            }
            bufferedreader.close();
            return content.toString();
        } catch (Exception e) {
            Logs.e(e, "");
            throw e;
        } finally {
            if (inputstream != null)
                inputstream.close();
            if (inputstreamreader != null)
                inputstreamreader.close();
            if (bufferedreader != null)
                bufferedreader.close();
        }

    }

    private static KeyManagerFactory getKeyManagerFactory(InputStream key, String keyPassword) {
        KeyManagerFactory kmf = null;

        try {
            String keyStoreType = "BKS";
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(key, keyPassword.toCharArray());

            String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
            kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
            kmf.init(keyStore, keyPassword.toCharArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return kmf;
    }
}
