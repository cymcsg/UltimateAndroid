package com.marshalchen.common.commonUtils.urlUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Get Config of UniversalImageLoader
 */
public class UniversalImageLoader {
    /**
     * Get default ImageLoaderConfiguration
     *
     * @param context
     * @return ImageLoaderConfiguration
     */
    public static ImageLoaderConfiguration getDefaultImageLoaderConfiguration(Context context) {
        return getDefaultImageLoaderConfiguration(context, false);
    }

    /**
     * Get default ImageLoaderConfiguration and you can choose the ImageLoader will write log or not.
     * @param context
     * @param isWriteLog
     * @return
     */
    public static ImageLoaderConfiguration getDefaultImageLoaderConfiguration(Context context, boolean isWriteLog) {
        ImageLoaderConfiguration.Builder builder = getDefaultImageLoaderConfigurationBuilder(context);
        if (isWriteLog) {
            builder.writeDebugLogs();
        }
        ImageLoaderConfiguration config = builder.build();
        return config;
    }

    /**
     * Get default ImageLoaderConfiguration.Builder,and you can easily change the builder.
     * @param context
     * @return
     */
    public static ImageLoaderConfiguration.Builder getDefaultImageLoaderConfigurationBuilder(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
//                .discCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(1000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                        //   .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .defaultDisplayImageOptions(getDefaultImageOptions());
        return builder;
    }

    /**
     * Return default DisplayImageOptions
     * @return
     */
    public static DisplayImageOptions getDefaultImageOptions() {
        DisplayImageOptions options = getDefaultImageOptionsBuilder()
                .build();
        return options;
    }

    /**
     * Return default DisplayImageOptions.Builder
     * @return
     */
    public static DisplayImageOptions.Builder getDefaultImageOptionsBuilder() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)  // default
                        //  .delayBeforeLoading(1000)
              //  .showImageOnLoading(R.drawable.titanic_wave_black)
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .cacheInMemory(true)
                .cacheOnDisk(true);
        return builder;
    }
}
