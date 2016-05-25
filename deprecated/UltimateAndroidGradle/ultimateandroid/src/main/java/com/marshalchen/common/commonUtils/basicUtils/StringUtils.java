package com.marshalchen.common.commonUtils.basicUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Some utils about String
 */
public class StringUtils {
    /**
     * Get Unicode Length.
     * Chinese word  accounted for two bytes.
     *
     * @param value
     * @return
     */
    public static int getStringUnicodeLength(String value) {
        int valueLength = 0;
        String chineseCode = "[\u4e00-\u9fa5]";
        //Judge the unicodelength is 1 or 2
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chineseCode)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * Convert InputStream To String
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder total = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        return total.toString();
    }

    /**
     * Decode Html String
     * @param string
     * @return
     */
    public static String decodeHtml(String string) {

        String rst = string;

        rst = rst.replaceAll("&lt;", "<");
        rst = rst.replaceAll("&gt;", ">");
        rst = rst.replaceAll("&quot;", "\"");
        rst = rst.replaceAll("&amp;", "&");


        return rst;

    }



    /**
     * Convert byte[] to Hex String
     * @param keyData
     * @return
     */
    public static String toHexString(byte[] keyData) {
        if (keyData == null) {
            return null;
        }
        int expectedStringLen = keyData.length * 2;
        StringBuilder sb = new StringBuilder(expectedStringLen);
        for (int i = 0; i < keyData.length; i++) {
            String hexStr = Integer.toString(keyData[i] & 0x00FF, 16);
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }



    /**
     * Judge if the ArrayList<String> contains the String variable
     * This method judges  trim String.
     *
     * @param s
     * @param arrayList
     * @return If a String in ArrayList contains the variable return true
     */
    public static boolean ifStringInList(String s, ArrayList<String> arrayList) {
        if (BasicUtils.judgeNotNull(s) && BasicUtils.judgeNotNull(arrayList)) {
            for (String str : arrayList) {
                if (str.trim().contains(s))
                    return true;
            }
        }

        return false;
    }

    /**
     * Judge if the ArrayList<String> contains the String variable
     *
     * @param s
     * @param arrayList
     * @return If only a String in ArrayList equals the variable return true
     */
    public static boolean ifStringExactlyInList(String s, ArrayList<String> arrayList) {
        if (BasicUtils.judgeNotNull(s) && BasicUtils.judgeNotNull(arrayList)) {
            for (String str : arrayList) {
                if (str.equals(s))
                    return true;
            }
        }

        return false;
    }
}
