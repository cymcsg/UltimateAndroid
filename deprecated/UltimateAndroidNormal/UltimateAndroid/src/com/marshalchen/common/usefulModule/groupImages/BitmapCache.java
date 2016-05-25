package com.marshalchen.common.usefulModule.groupImages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.marshalchen.common.commonUtils.uiUtils.ImageUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class BitmapCache {

    public Handler h = new Handler();
    public final String TAG = getClass().getSimpleName();
    private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

    public void put(String path, Bitmap bmp) {
        if (!TextUtils.isEmpty(path) && bmp != null) {
            imageCache.put(path, new SoftReference<Bitmap>(bmp));
        }
    }

    public void displayBmp(final ImageView iv, final String thumbPath,
                           final String sourcePath, final ImageCallback callback) {
        if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
            Log.e(TAG, "no paths pass in");
            return;
        }

        final String path;
        final boolean isThumbPath;
        if (!TextUtils.isEmpty(thumbPath)) {
            path = thumbPath;
            isThumbPath = true;
        } else if (!TextUtils.isEmpty(sourcePath)) {
            path = sourcePath;
            isThumbPath = false;
        } else {
            // iv.setImageBitmap(null);
            return;
        }

        if (imageCache.containsKey(path)) {
            SoftReference<Bitmap> reference = imageCache.get(path);
            Bitmap bmp = reference.get();
            if (bmp != null) {
                if (callback != null) {
                    callback.imageLoad(iv, bmp, sourcePath);
                }
                iv.setImageBitmap(bmp);
                Log.d(TAG, "hit cache");
                return;
            }
        }
        iv.setImageBitmap(null);

        new Thread() {
            Bitmap thumb;

            public void run() {

                try {
                    if (isThumbPath) {
                        thumb = BitmapFactory.decodeFile(thumbPath);
                        if (thumb == null) {
                            thumb = ImageUtils.revitionImageSize(sourcePath,256);
                        }
                    } else {
                        thumb = ImageUtils.revitionImageSize(sourcePath,256);
                    }
                } catch (Exception e) {

                }
                if (thumb == null) {
                    thumb = ImageBucketActivity.bimap;
                }
                Log.e(TAG, "-------thumb------" + thumb);
                put(path, thumb);

                if (callback != null) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.imageLoad(iv, thumb, sourcePath);
                        }
                    });
                }
            }
        }.start();

    }


    public interface ImageCallback {
        public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params);
    }
}
