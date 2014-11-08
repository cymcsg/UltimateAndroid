package com.marshalchen.common.commonUtils.urlUtils;

import android.os.Build;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 *
 * TripleDES Method
 */
public class TripleDES {
    private final static String token = "";
    public static byte[] copyOf(byte[] original, int newLength) {

        if (newLength < 0) {
            throw new NegativeArraySizeException(Integer.toString(newLength));
        }
        return copyOfRange(original, 0, newLength);
    }

    public static int judgeVerionOfSdk() {
        return Build.VERSION.SDK_INT;
    }

    public static String Base64encoding(byte[] context,int type) {
        String result="";
        if (judgeVerionOfSdk() > 7) {
            result= Base64.encodeToString(context, type);
        } else {
            result= com.marshalchen.common.commonUtils.urlUtils.Base64.encodeBytes(context);
        }
        return result;
    }
    public static byte[] Base64encodingByte(byte[] context,int type) {
        byte[] result;
        if (judgeVerionOfSdk() > 17) {
            result= Base64.encode(context, type);
        } else {
            result= com.marshalchen.common.commonUtils.urlUtils.Base64.encodeBytesToBytes(context);
        }
        return result;
    }
    public static byte[] Base64decoding(String context,int type) throws  Exception{
        byte[] result;
        if (judgeVerionOfSdk() > 17) {
            result= Base64.decode(context, type);
        } else {
            result= com.marshalchen.common.commonUtils.urlUtils.Base64.decode(context);
        }
        return result;
    }
    public static byte[] Base64decodingByte(byte[] context,int type) throws  Exception{
        byte[] result;
        if (judgeVerionOfSdk() > 17) {
            result= Base64.decode(context, type);
        } else {
            result= com.marshalchen.common.commonUtils.urlUtils.Base64.decode(context);
        }
        return result;
    }


    public static byte[] copyOfRange(byte[] original, int start, int end) {
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

    public static byte[] encrypt(String message) throws Exception {
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
        return Base64encodingByte(cipherText, 0);
    }

    public static String encrypts(String message) throws Exception {
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

    public static String decrypt(byte[] message) throws Exception {
        byte[] values =Base64decodingByte(message, 0);
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

    public static String decrypts(String message) throws Exception {
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


    //delete all the wrap of String
    public static String replaceNewLine(String strText) {
        String strResult = "";
        int intStart = 0;
        int intLoc = strText.indexOf("\n", intStart);
        while (intLoc != -1) {
            strResult += strText.substring(intStart, intLoc - 1);
            intStart = intLoc + 1;
            intLoc = strText.indexOf("\n", intStart);
        }
        strResult += strText.substring(intStart, strText.length());
        return strResult;
    }


    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs += ("0" + stmp);
            else
                hs += stmp;
        }
        return hs.toUpperCase();
    }


    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("wrong index");

        byte[] b2 = new byte[b.length / 2];

        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
