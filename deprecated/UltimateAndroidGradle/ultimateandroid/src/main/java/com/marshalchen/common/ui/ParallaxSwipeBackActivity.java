package com.marshalchen.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.marshalchen.common.R;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

/**
 * @Title: ${file_name}
 * @Description: Parallax Swipe Back
 * Created by bushijie33@gmail.com on 2015/7/5.
 */
public class ParallaxSwipeBackActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {

    private final static String TAG = ParallaxSwipeBackActivity.class.getSimpleName();
    private final static String WINDOWBITMAP = "screenshots.jpg";
    private File mFileTemp;
    private SlidingPaneLayout slidingPaneLayout;
    private FrameLayout frameLayout;
    private ImageView behindImageView;
    private ImageView shadowImageView;
    private int defaultTranslationX = 100;
    private int shadowWidth = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //通过反射来改变SlidingPanelayout的值
        try {
            slidingPaneLayout = new SlidingPaneLayout(this);
            Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            f_overHang.setAccessible(true);
            f_overHang.set(slidingPaneLayout, 0);
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        mFileTemp = new File(getCacheDir(), WINDOWBITMAP);
        defaultTranslationX = dip2px(defaultTranslationX);
        shadowWidth = dip2px(shadowWidth);
        //behindframeLayout
        FrameLayout behindframeLayout = new FrameLayout(this);
        behindImageView = new ImageView(this);
        behindImageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        behindframeLayout.addView(behindImageView, 0);

        //containerLayout
        LinearLayout containerLayout = new LinearLayout(this);
        containerLayout.setOrientation(LinearLayout.HORIZONTAL);
        containerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        containerLayout.setLayoutParams(new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth() + shadowWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        //you view container
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        //add shadow
        shadowImageView = new ImageView(this);
        shadowImageView.setBackgroundResource(R.drawable.parallax_swipe_back_shadow);
        shadowImageView.setLayoutParams(new LinearLayout.LayoutParams(shadowWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        containerLayout.addView(shadowImageView);
        containerLayout.addView(frameLayout);
        containerLayout.setTranslationX(-shadowWidth);
        //添加两个view
        slidingPaneLayout.addView(behindframeLayout, 0);
        slidingPaneLayout.addView(containerLayout, 1);
    }

    @Override
    public void setContentView(int id) {
        setContentView(getLayoutInflater().inflate(id, null));
    }

    @Override
    public void setContentView(View v) {
        setContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        try {
            behindImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            behindImageView.setImageBitmap(getBitmap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        super.setContentView(slidingPaneLayout, params);
        frameLayout.removeAllViews();
        frameLayout.addView(v, params);
    }


    @Override
    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
        this.overridePendingTransition(0, 0);
    }

    @Override
    public void onPanelSlide(View view, float v) {
        Log.e(TAG, "onPanelSlide ：" + v);
        //duang duang duang 你可以在这里加入很多特效
        behindImageView.setTranslationX(v * defaultTranslationX - defaultTranslationX);
        shadowImageView.setAlpha(v<0.8?1:(1.5f-v));
    }

    /**
     * 取得视觉差背景图
     *
     * @return
     */
    public Bitmap getBitmap() {
        return BitmapFactory.decodeFile(mFileTemp.getAbsolutePath());
    }

    /**
     * 启动视觉差返回Activity
     *
     * @param activity
     * @param intent
     */
    public void startParallaxSwipeBackActivty(Activity activity, Intent intent) {
        startParallaxSwipeBackActivty(activity, intent, false);
    }

    /**
     * startParallaxSwipeBackActivty
     *
     * @param activity
     * @param intent
     * @param isFullScreen
     */
    public void startParallaxSwipeBackActivty(Activity activity, Intent intent, boolean isFullScreen) {
        screenshots(activity, isFullScreen);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    /**
     * this screeshots form
     *
     * @param activity
     * @param isFullScreen
     */
    public void screenshots(Activity activity, boolean isFullScreen) {
        try {
            //View是你需要截图的View
            View decorView = activity.getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap b1 = decorView.getDrawingCache();
            // 获取状态栏高度 /
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            Log.e(TAG, "statusBarHeight:" + statusBarHeight);
            // 获取屏幕长和高 Get screen width and height
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            int height = activity.getWindowManager().getDefaultDisplay().getHeight();
            // 去掉标题栏 Remove the statusBar Height
            Bitmap bitmap;
            if (isFullScreen) {
                bitmap = Bitmap.createBitmap(b1, 0, 0, width, height);
            } else {
                bitmap = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
            }
            decorView.destroyDrawingCache();
            FileOutputStream out = new FileOutputStream(mFileTemp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
