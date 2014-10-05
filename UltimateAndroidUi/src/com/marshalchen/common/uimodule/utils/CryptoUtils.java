package com.marshalchen.common.uimodule.utils;

import android.content.Context;

import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.cipher.NativeGCMCipher;
import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.mac.NativeMac;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;
import com.marshalchen.common.commonUtils.fileUtils.FileUtils;
import com.marshalchen.common.commonUtils.logUtils.Logs;

import java.io.*;
import java.util.Arrays;

/**
 * Created by cym on 14-7-22.
 */
public class CryptoUtils {
    /**
     * could be replace by new SharedPrefsBackedKeyChain(context)
     */
    static KeyChain keyChain = new FakeKeyChain();

    public static void encryptingContent(Context context, File file, byte[] plainTextBytes) throws Exception {
// Creates a new Crypto object with default implementations of
// a key chain as well as native library.
        Crypto crypto = new Crypto(
                new SharedPrefsBackedKeyChain(context),
                new SystemNativeCryptoLibrary());


// Check for whether the crypto functionality is available
// This might fail if android does not load libaries correctly.
        if (!crypto.isAvailable()) {
            return;
        }

        OutputStream fileStream = new BufferedOutputStream(
                new FileOutputStream(file));

// Creates an output stream which encrypts the data as
// it is written to it and writes it out to the file.
        OutputStream outputStream = crypto.getCipherOutputStream(
                fileStream,
                new Entity("TEST1"));

// Write plaintext to it.
        outputStream.write(plainTextBytes);
        outputStream.close();
    }


    public static void decryptingContent(Context context, File file, String newPath) throws Exception {
        // Get the file to which ciphertext has been written.
        FileInputStream fileStream = new FileInputStream(file);
        Crypto crypto = new Crypto(
                new SharedPrefsBackedKeyChain(context),
                new SystemNativeCryptoLibrary());
// Creates an input stream which decrypts the data as
// it is read from it.
        InputStream inputStream = crypto.getCipherInputStream(
                fileStream,
                new Entity("TEST1"));

// Read into a byte array.
        int read;
        byte[] buffer = new byte[1024];

// You must read the entire stream to completion.
// The verification is done at the end of the stream.
// Thus not reading till the end of the stream will cause
// a security bug.
        FileOutputStream fs = new FileOutputStream(newPath);
        while ((read = inputStream.read(buffer)) != -1) {
            fs.write(buffer, 0, read);
        }

        inputStream.close();
    }

    public static void testCrypto(Context context) {
        try {
            CryptoUtils.encryptingContent(context,
                    new File(FileUtils.getCurrentDataPath(context, "test") + "/aaab"), "hehe1".getBytes());
            CryptoUtils.decryptingContent(context, new File(FileUtils.getCurrentDataPath(context, "test") + "/aaab"), FileUtils.getCurrentDataPath(context, "test") + "/aaabc");
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
        }
    }


}

class FakeKeyChain implements KeyChain {

    private final byte[] mKey = new byte[NativeGCMCipher.KEY_LENGTH];
    private final byte[] mIV = new byte[NativeGCMCipher.IV_LENGTH];
    private final byte[] mMacKey = new byte[NativeMac.KEY_LENGTH];
    public static final int NUM_DATA_BYTES = 2752;
    public static final int KEY_BYTES = 42;
    public static final int MAC_KEY_BYTES = 44;
    public static final int IV_BYTES = 43;
    public static final String ENTITY_NAME = "TEST";
    public static final String FAKE_ENTITY_NAME = "FAKE";

    public FakeKeyChain() {
        Arrays.fill(mKey, (byte) KEY_BYTES);
        Arrays.fill(mIV, (byte) IV_BYTES);
        Arrays.fill(mMacKey, (byte) MAC_KEY_BYTES);
    }

    @Override
    public byte[] getCipherKey() {
        return mKey;
    }

    @Override
    public byte[] getMacKey() {
        return mMacKey;
    }

    @Override
    public byte[] getNewIV() {
        return mIV;
    }

    @Override
    public void destroyKeys() {
    }
}

