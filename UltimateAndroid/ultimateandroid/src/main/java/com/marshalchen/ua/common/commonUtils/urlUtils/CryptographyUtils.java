package com.marshalchen.ua.common.commonUtils.urlUtils;

import android.text.TextUtils;

import com.marshalchen.ua.common.commonUtils.fileUtils.FileUtils;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Cryptography Utils including MD5,SHA1 etc.
 */
public class CryptographyUtils {
    /**
     * Get the MD5 of the String
     *
     * @param content
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(String content) throws NoSuchAlgorithmException {
        return getCryptography(content, "MD5");
    }

    /**
     * Get the SHA1 of the String
     *
     * @param content
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSHA1(String content) throws NoSuchAlgorithmException {
        return getCryptography(content, "SHA1");
    }

    public static String getCryptography(String content, String encryptType) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
            byte[] inputByteArray = content.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
    }

    /**
     * Get the MD5 of the file
     *
     * @param filePath
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getMd5FromFile(String filePath) throws IOException, NoSuchAlgorithmException {
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(filePath);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;
            messageDigest = digestInputStream.getMessageDigest();
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw e;
        } finally {
            if (digestInputStream != null) {
                digestInputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }

        }
    }

    public static String getMd5FromFile(File inputFile) throws IOException, NoSuchAlgorithmException {
        return getMd5FromFile(FileUtils.readFile(inputFile));
    }


    /**
     * Revert byte to hex
     *
     * @param bytes
     * @return
     */
    public static String byteArrayToHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int halfbyte = (bytes[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = bytes[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }


    /**
     * Revert hex byte to byte array
     *
     * @param hexByte
     * @return
     */
    public static byte[] hexToByteArray(byte[] hexByte) {
        return hexToByteArray(new String(hexByte));
    }

    /**
     * Revert hex String to byte array.
     *
     * @param hexString
     * @return
     */
    public static byte[] hexToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }


    /**
     * Check if the file's MD5 is equals to the string of MD5 which you provide.
     *
     * @param md5
     * @param updateFile
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkMD5(String md5, File updateFile) throws IOException, NoSuchAlgorithmException {
        if (TextUtils.isEmpty(md5) || updateFile == null) {
            return false;
        }
        String calculatedDigest = getMd5FromFile(updateFile);
        if (calculatedDigest == null) {

            return false;
        }
        return calculatedDigest.equalsIgnoreCase(md5);
    }


    public static String getSHA256(String data) {
        return getSHA256(data.getBytes());
    }


    public static String getSHA256(byte[] data) {
        return byteArrayToHex(encryptSHA256(data));
    }

    public static byte[] encryptSHA256(byte[] data) {
        return encryptAlgorithm(data, "SHA-256");
    }


    public static byte[] encryptAlgorithm(byte[] data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
