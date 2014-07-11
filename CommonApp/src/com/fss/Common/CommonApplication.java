package com.fss.Common;

import android.app.Application;
import com.fss.Common.commonUtils.urlUtils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by cym on 14-4-30.
 */
public class CommonApplication extends Application {

    public static CommonApplication mDemoApp;

    @Override
    public void onCreate() {
        super.onCreate();
        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = UniversalImageLoader.getDefaultImageLoaderConfiguration(getApplicationContext());
        ImageLoader.getInstance().init(config);
        //  ActiveAndroid.initialize(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // ActiveAndroid.dispose();
    }


    public static CommonApplication getInstance() {
        return mDemoApp;
    }
}
