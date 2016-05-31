package com.marshalchen.ultimateandroid.demo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by cymcsg on 31/5/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
