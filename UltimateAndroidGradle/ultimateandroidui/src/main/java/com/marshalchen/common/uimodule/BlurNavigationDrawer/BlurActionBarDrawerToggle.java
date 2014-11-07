/*
 * Copyright (C) 2014 Charalampakis Basilis - Blur ActionBarDrawerToggle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.uimodule.BlurNavigationDrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BlurActionBarDrawerToggle extends ActionBarDrawerToggle {

    private Context context;

    /**
     * the layout that we take snapshot
     */
    private DrawerLayout mDrawerLayout;

    /**
     * an imageview to display the blurred snapshot/bitmap
     */
    private ImageView mBlurredImageView;

    /**
     * Blur radius used for the background.
     */
    private int mBlurRadius = DEFAULT_BLUR_RADIUS;

    /**
     * Default Blur Radius
     */

    public static int DEFAULT_BLUR_RADIUS = 5;

    /**
     * Down scale factor to reduce blurring time and memory allocation.
     */
    private float mDownScaleFactor = DEFAULT_DOWNSCALEFACTOR;

    /**
     * Default Down Scale Factor
     */

    public static float DEFAULT_DOWNSCALEFACTOR = 5.0f;

    /**
     * Render flag
     * <p/>
     * If true we must render
     * if false, we have already blurred the background
     */
    private boolean prepareToRender = true;

    /**
     * flag for "fake" sliding detection
     */
    private boolean isOpening = false;

    public BlurActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout,
                                     int drawerImageRes, int openDrawerContentDescRes,
                                     int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.context = activity.getBaseContext();
        this.mDrawerLayout = drawerLayout;
        init();
    }

    /**
     * We make a fake ImageView with width and height MATCH_PARENT.
     * This ImageView will host the blurred snapshot/bitmap.
     */
    private void init() {
        mBlurredImageView = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mBlurredImageView.setLayoutParams(params);
        mBlurredImageView.setClickable(false);
        mBlurredImageView.setVisibility(View.GONE);
        mBlurredImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                // Add the ImageViewiew not in the last position.
                // Otherwise, it will be shown in NavigationDrawer
                mDrawerLayout.addView(mBlurredImageView, 1);
            }
        });
    }


    @Override
    public void onDrawerSlide(final View drawerView, final float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);

        //must check this for "fake" sliding..
        if (slideOffset == 0.f)
            isOpening = false;
        else
            isOpening = true;

        render();
        setAlpha(mBlurredImageView, slideOffset, 100);
    }


    @Override
    public void onDrawerClosed(View view) {
        prepareToRender = true;
        mBlurredImageView.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        super.onDrawerStateChanged(newState);

        // "fake" sliding detection
        if (newState == DrawerLayout.STATE_IDLE
                && !isOpening) {

            handleRecycle();
        }
    }

    /**
     * Snapshots the specified layout and scale it using scaleBitmap() function
     * then we blur the scaled bitmap with the preferred blur radius.
     * Finally, we post it to our fake {@link android.widget.ImageView}.
     */

    private void render() {

        if (prepareToRender) {
            prepareToRender = false;

            Bitmap bitmap = loadBitmapFromView(mDrawerLayout);
            bitmap = scaleBitmap(bitmap);
            bitmap = Blur.fastblur(context, bitmap, mBlurRadius, false);

            mBlurredImageView.setVisibility(View.VISIBLE);
            mBlurredImageView.setImageBitmap(bitmap);
        }

    }

    public void setRadius(int radius) {
        mBlurRadius = radius < 1 ? 1 : radius;
    }

    public void setDownScaleFactor(float downScaleFactor) {
        mDownScaleFactor = downScaleFactor < 1 ? 1 : downScaleFactor;
    }

    private void setAlpha(View view, float alpha, long durationMillis) {
        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(durationMillis);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        } else {
            view.setAlpha(alpha);
        }
    }


    private Bitmap loadBitmapFromView(View mView) {
        Bitmap b = Bitmap.createBitmap(
                mView.getWidth(),
                mView.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);

        // With the following, screen blinks
        //v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);

        mView.draw(c);

        return b;
    }


    private Bitmap scaleBitmap(Bitmap myBitmap) {

        int width = (int) (myBitmap.getWidth() / mDownScaleFactor);
        int height = (int) (myBitmap.getHeight() / mDownScaleFactor);

        return Bitmap.createScaledBitmap(myBitmap, width, height, false);
    }

    private void handleRecycle() {
        Drawable drawable = mBlurredImageView.getDrawable();

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
            Bitmap bitmap = bitmapDrawable.getBitmap();

            if (bitmap != null)
                bitmap.recycle();

            mBlurredImageView.setImageBitmap(null);
        }

        prepareToRender = true;
    }
}