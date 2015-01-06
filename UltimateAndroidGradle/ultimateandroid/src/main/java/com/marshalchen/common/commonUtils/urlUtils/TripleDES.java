package com.marshalchen.common.commonUtils.urlUtils;

import android.os.Build;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * TripleDES Method.
 * Before use {@link #encrypt(String)} or {@link #decrypt(byte[])} {@link #decrypt(String)} directly,you should use {@link #setToken(String)} first.
 *
 */
public class TripleDES {
    private static String token = "";


    protected static byte[] copyOf(byte[] original, int newLength) throws NegativeArraySizeException {
        if (newLength < 0) {
            throw new NegativeArraySizeException(Integer.toString(newLength));
        }
        return copyOfRange(original, 0, newLength);
    }

    /**
     * Return the user-visible SDK version of the framework
     *
     * @return Build.VERSION.SDK_INT
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Base64-encode the given data and return a newly allocated String with the result.
     *
     * @param context
     * @param type
     * @return
     */
    public static String Base64encoding(byte[] context, int type) {
        String result = "";
        if (getSdkVersion() > 7) {
            result = Base64.encodeToString(context, type);
        } else {
            result = com.marshalchen.common.commonUtils.urlUtils.Base64.encodeBytes(context);
        }
        return result;
    }

    /**
     * Base64-encode the given data and return a newly allocated byte[] with the result.
     *
     * @param context
     * @param type
     * @return
     */
    public static byte[] Base64encodingByte(byte[] context, int type) {
        byte[] result;
        if (getSdkVersion() > 17) {
            result = Base64.encode(context, type);
        } else {
            result = com.marshalchen.common.commonUtils.urlUtils.Base64.encodeBytesToBytes(context);
        }
        return result;
    }

    /**
     * Decode the Base64-encoded data in input and return the data in a new byte array.
     * The padding '=' characters at the end are considered optional, but if any are present, there must be the correct number of them.
     *
     * @param context
     * @param type
     * @return
     * @throws IllegalArgumentException if the input contains incorrect padding
     */
    public static byte[] Base64decoding(String context, int type) throws IllegalArgumentException, IOException {
        byte[] result;
        if (getSdkVersion() > 17) {
            result = Base64.decode(context, type);
        } else {
            result = com.marshalchen.common.commonUtils.urlUtils.Base64.decode(context);
        }
        return result;
    }

    /**
     * Decode the Base64-encoded data in input and return the data in a new byte array.
     * The padding '=' characters at the end are considered optional, but if any are present, there must be the correct number of them.
     *
     * @param context
     * @param type
     * @return
     * @throws Exception
     */
    public static byte[] Base64decodingByte(byte[] context, int type) throws Exception {
        byte[] result;
        if (getSdkVersion() > 17) {
            result = Base64.decode(context, type);
        } else {
            result = com.marshalchen.common.commonUtils.urlUtils.Base64.decode(context);
        }
        return result;
    }


    private static byte[] copyOfRange(byte[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        int copyLength = Math.min(resultLength, originalLength - start);
        byte[] result = new byte[resultLength];
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }


    /**
     * Encrypt the message with TripleDES
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message) throws Exception {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        final byte[] digestOfPassword = md.digest(token
                .getBytes());
        byte[] keyBytes = copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8; ) {
            keyBytes[k++] = keyBytes[j++];
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        String s1 = "12345678";
        byte[] bytes = s1.getBytes();
        final IvParameterSpec iv = new IvParameterSpec(bytes);
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        final byte[] plainTextBytes = message.getBytes("utf-8");
        final byte[] cipherText = cipher.doFinal(plainTextBytes);
        String ss = Base64encoding(cipherText, 0);
        byte[] result = Base64encodingByte(cipherText, 0);
        return new String(result, "UTF-8");
    }

    /**
     * Decrypt the message with TripleDES
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] message) throws Exception {
        byte[] values = Base64decodingByte(message, 0);
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        final byte[] digestOfPassword = md.digest(token
                .getBytes("utf-8"));

        final byte[] keyBytes = copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8; ) {
            keyBytes[k++] = keyBytes[j++];
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        String s1 = "12345678";
        byte[] bytes = s1.getBytes();
        final IvParameterSpec iv = new IvParameterSpec(bytes);
        final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        final byte[] plainText = decipher.doFinal(values);
        return new String(plainText, "UTF-8");
    }

    /**
     * Decrypt the message with TripleDES
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) throws Exception {
        if (message == null || message == "") return "";
        byte[] values = Base64decoding(message, 0);
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        final byte[] digestOfPassword = md.digest(token
                .getBytes("utf-8"));
        final byte[] keyBytes = copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8; ) {
            keyBytes[k++] = keyBytes[j++];
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        String s1 = "12345678";
        byte[] bytes = s1.getBytes();
        final IvParameterSpec iv = new IvParameterSpec(bytes);
        final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        final byte[] plainText = decipher.doFinal(values);
        return new String(plainText, "UTF-8");
    }



    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TripleDES.token = token;
    }
}
