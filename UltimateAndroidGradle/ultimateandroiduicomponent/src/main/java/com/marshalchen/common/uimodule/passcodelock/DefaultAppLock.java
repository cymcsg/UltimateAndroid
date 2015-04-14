package com.marshalchen.common.uimodule.passcodelock;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DefaultAppLock extends AbstractAppLock {

    private Application currentApp; //Keep a reference to the app that invoked the locker
    private SharedPreferences settings;
    private Date lostFocusDate;

    //Add back-compatibility
    private static final String OLD_PASSWORD_SALT = "sadasauidhsuyeuihdahdiauhs";
    private static final String OLD_APP_LOCK_PASSWORD_PREF_KEY = "wp_app_lock_password_key";
    private static final String  PASSWORD_PREFERENCE_KEY="passcode_lock_prefs_password_key";
    private static final String PASSWORD_SALT="11-maggio-2014-osvaldo-al-49novesimo!";
    private static final String PASSWORD_ENC_SECRET="5-maggio-2002-Karel-Poborsky";
    public DefaultAppLock(Application currentApp) {
        super();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(currentApp);
        this.settings = settings;
        this.currentApp = currentApp;
    }

    public void enable(){
    	if (android.os.Build.VERSION.SDK_INT < 14)
    		return;

        if( isPasswordLocked() ) {
            currentApp.unregisterActivityLifecycleCallbacks(this);
            currentApp.registerActivityLifecycleCallbacks(this);
        }
    }

    public void disable( ){
    	if (android.os.Build.VERSION.SDK_INT < 14)
    		return;

        currentApp.unregisterActivityLifecycleCallbacks(this);
    }

    public void forcePasswordLock(){
        lostFocusDate = null;
    }

    public boolean verifyPassword( String password ){
    	String storedPassword = "";

    	if (settings.contains(OLD_APP_LOCK_PASSWORD_PREF_KEY)) { //add back-compatibility
    		//Check if the old value is available
    		storedPassword = settings.getString(OLD_APP_LOCK_PASSWORD_PREF_KEY, "");
    		password = OLD_PASSWORD_SALT + password + OLD_PASSWORD_SALT;
            password = StringUtils.getMd5Hash(password);
    	} else if (settings.contains(PASSWORD_PREFERENCE_KEY)) {
    		//read the password from the new key
    		storedPassword = settings.getString(PASSWORD_PREFERENCE_KEY, "");
    		storedPassword = decryptPassword(storedPassword);
    		password = PASSWORD_SALT + password +  PASSWORD_SALT;
    	}

        if( password.equalsIgnoreCase(storedPassword) ) {
            lostFocusDate = new Date();
            return true;
        } else {
            return false;
        }
    }

    public boolean setPassword(String password){
        SharedPreferences.Editor editor = settings.edit();

        if(password == null) {
            editor.remove(OLD_APP_LOCK_PASSWORD_PREF_KEY);
            editor.remove(PASSWORD_PREFERENCE_KEY);
            editor.commit();
            this.disable();
        } else {
            password = PASSWORD_SALT + password +  PASSWORD_SALT;
            password = encryptPassword(password);
            editor.putString(PASSWORD_PREFERENCE_KEY, password);
            editor.remove(OLD_APP_LOCK_PASSWORD_PREF_KEY);
            editor.commit();
            this.enable();
        }

        return true;
    }

    //Check if we need to show the lock screen at startup
    public boolean isPasswordLocked(){

    	if (settings.contains(OLD_APP_LOCK_PASSWORD_PREF_KEY)) //Check if the old value is available
    		return true;

    	if (settings.contains(PASSWORD_PREFERENCE_KEY))
    		return true;

    	return false;
    }

    private String encryptPassword(String clearText) {
        try {
            DESKeySpec keySpec = new DESKeySpec(
                    PASSWORD_ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText
                    .getBytes("UTF-8")), Base64.DEFAULT);
            return encrypedPwd;
        } catch (Exception e) {
        }
        return clearText;
    }

    private String decryptPassword(String encryptedPwd) {
        try {
            DESKeySpec keySpec = new DESKeySpec(PASSWORD_ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encryptedWithoutB64 = Base64.decode(encryptedPwd, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
            return new String(plainTextPwdBytes);
        } catch (Exception e) {
        }
        return encryptedPwd;
    }

    private boolean mustShowUnlockSceen() {

        if( isPasswordLocked() == false)
            return false;

        if( lostFocusDate == null )
            return true; //first startup or when we forced to show the password

        int currentTimeOut = lockTimeOut; //get a reference to the current password timeout and reset it to default
        lockTimeOut = DEFAULT_TIMEOUT;
        Date now = new Date();
        long now_ms = now.getTime();
        long lost_focus_ms = lostFocusDate.getTime();
        int secondsPassed = (int) (now_ms - lost_focus_ms)/(1000);
        secondsPassed = Math.abs(secondsPassed); //Make sure changing the clock on the device to a time in the past doesn't by-pass PIN Lock
        if (secondsPassed >= currentTimeOut) {
            lostFocusDate = null;
            return true;
        }

        return false;
    }

    @Override
    public void onActivityPaused(Activity arg0) {

        if( arg0.getClass() == PasscodeUnlockActivity.class )
            return;

        if( ( this.appLockDisabledActivities != null ) && Arrays.asList(this.appLockDisabledActivities).contains( arg0.getClass().getName() ) )
     	   return;

        lostFocusDate = new Date();

    }

    @Override
    public void onActivityResumed(Activity arg0) {

        if( arg0.getClass() == PasscodeUnlockActivity.class )
            return;

       if(  ( this.appLockDisabledActivities != null ) && Arrays.asList(this.appLockDisabledActivities).contains( arg0.getClass().getName() ) )
    	   return;

        if(mustShowUnlockSceen()) {
            //uhhh ohhh!
            Intent i = new Intent(arg0.getApplicationContext(), PasscodeUnlockActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            arg0.getApplication().startActivity(i);
            return;
        }

    }

    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {
    }

    @Override
    public void onActivityDestroyed(Activity arg0) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
    }

    @Override
    public void onActivityStarted(Activity arg0) {
    }

    @Override
    public void onActivityStopped(Activity arg0) {
    }
}
