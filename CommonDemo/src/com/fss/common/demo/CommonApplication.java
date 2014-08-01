package com.fss.common.demo;

import android.app.Application;
import com.fss.Common.commonUtils.urlUtils.UniversalImageLoader;
import com.fss.Common.uiModule.customFonts.CalligraphyConfig;
import com.fss.Common.uiModule.passcodelock.AppLockManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by cym on 14-4-30.
 */
public class CommonApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = UniversalImageLoader.getDefaultImageLoaderConfiguration(getApplicationContext());
        ImageLoader.getInstance().init(config);
        //  ActiveAndroid.initialize(this);
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);
        CalligraphyConfig.initDefault("fonts/Satisfy-Regular.ttf", R.attr.fontPath);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // ActiveAndroid.dispose();
    }

}
