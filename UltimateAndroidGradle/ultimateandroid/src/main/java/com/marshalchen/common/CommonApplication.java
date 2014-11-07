package com.marshalchen.common;

import android.app.Application;
import com.activeandroid.ActiveAndroid;
import com.marshalchen.common.commonUtils.urlUtils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * CommonApplication which config ImageLoader and ActiveAndroid
 */
public class CommonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = UniversalImageLoader.getDefaultImageLoaderConfiguration(getApplicationContext());
        ImageLoader.getInstance().init(config);

        ActiveAndroid.initialize(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // ActiveAndroid.dispose();
    }

}
