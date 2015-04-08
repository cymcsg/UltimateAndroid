package com.marshalchen.common.uimodule.passcodelock;

import android.app.Application;

public abstract class AbstractAppLock implements Application.ActivityLifecycleCallbacks {
    public static final int DEFAULT_TIMEOUT = 2; //2 seconds
    public static final int EXTENDED_TIMEOUT = 60; //60 seconds
    
    protected int lockTimeOut = DEFAULT_TIMEOUT;
    protected String[] appLockDisabledActivities = new String[0];
    
    /*
     * There are situations where an activity will start a different application with an intent.  
     * In these situations call this method right before leaving the app.
     */
    public void setOneTimeTimeout(int timeout) {
        this.lockTimeOut = timeout;
    }

    /*
     * There are situations where we don't want call the AppLock on activities (sharing items to out app for example).  
     */
    public void setDisabledActivities( String[] disabledActs ) {
    	this.appLockDisabledActivities = disabledActs;
    }
    
    public abstract void enable();
    public abstract void disable();
    public abstract void forcePasswordLock();
    public abstract boolean verifyPassword( String password );
    public abstract boolean isPasswordLocked();
    public abstract boolean setPassword(String password);
}
