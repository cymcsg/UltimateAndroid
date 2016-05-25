/*
 * Copyright (C) 2013 Priboi Tiberiu
 * Copyright (C) 2013 The Android Open Source Project
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

package com.marshalchen.common.demoofui.foldingDrawer;

import java.io.IOException;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.TextureView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.marshalchen.common.uimodule.foldingLayout.BaseFoldingLayout;
import com.marshalchen.common.uimodule.foldingLayout.FoldingLayout;
import com.marshalchen.common.uimodule.foldingLayout.listener.OnFoldListener;
import com.marshalchen.common.demoofui.R;


/**
 * This application creates a paper like folding effect of some view. The number
 * of folds, orientation (vertical or horizontal) of the fold, and the anchor
 * point about which the view will fold can be set to achieve different folding
 * effects.
 * <p/>
 * Using bitmap and canvas scaling techniques, the foldingLayout can be scaled
 * so as to depict a paper-like folding effect. The addition of shadows on the
 * separate folds adds a sense of realism to the visual effect.
 * <p/>
 * This application shows folding of a TextureView containing a live camera
 * feed, as well as the folding of an ImageView with a static image. The
 * TextureView experiences jagged edges as a result of scaling operations on
 * rectangles. The ImageView however contains a 1 pixel transparent border
 * around its contents which can be used to avoid this unwanted artifact.
 */
public class FoldingLayoutActivity extends ActionBarActivity {

    private final int ANTIALIAS_PADDING = 1;

    private final int FOLD_ANIMATION_DURATION = 1000;

    /*
     * A bug was introduced in Android 4.3 that ignores changes to the Canvas
     * state between multiple calls to super.dispatchDraw() when running with
     * hardware acceleration. To account for this bug, a slightly different
     * approach was taken to fold a static image whereby a bitmap of the
     * original contents is captured and drawn in segments onto the canvas.
     * However, this method does not permit the folding of a TextureView hosting
     * a live camera feed which continuously updates. Furthermore, the sepia
     * effect was removed from the bitmap variation of the demo to simplify the
     * logic when running with this workaround."
     */
    static final boolean IS_JBMR2 = Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;
    static final boolean IS_ISC = Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB;

    private FoldingLayout mFoldLayout;
    private SeekBar mAnchorSeekBar;
    private BaseFoldingLayout.Orientation mOrientation = BaseFoldingLayout.Orientation.HORIZONTAL;

    private int mNumberOfFolds = 5;

    private float mAnchorFactor = 0;

    private boolean mDidLoadSpinner = true;

    private boolean mIsCameraFeed = false;
    private boolean mIsSepiaOn = false;

    private ItemSelectedListener mItemSelectedListener;

    private Camera mCamera;
    //private TextureView mTextureView;
    private ImageView mImageView;

    private Paint mSepiaPaint;
    private Paint mDefaultPaint;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.folding_drawer_activity_fold);

        mImageView = (ImageView) findViewById(R.id.image_view);
        mImageView.setPadding(ANTIALIAS_PADDING, ANTIALIAS_PADDING,
                ANTIALIAS_PADDING, ANTIALIAS_PADDING);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageDrawable(getResources()
                .getDrawable(R.drawable.test));

        if (IS_ISC) {
            //mTextureView = new TextureView(this);
            //mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }

        mAnchorSeekBar = (SeekBar) findViewById(R.id.anchor_seek_bar);
        mFoldLayout = (FoldingLayout) findViewById(R.id.fold_view);

        mFoldLayout.setFoldListener(mOnFoldListener);

        mAnchorSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

        mItemSelectedListener = new ItemSelectedListener();

        mDefaultPaint = new Paint();
        mSepiaPaint = new Paint();

        ColorMatrix m1 = new ColorMatrix();
        ColorMatrix m2 = new ColorMatrix();
        m1.setSaturation(0);
        m2.setScale(1f, .95f, .82f, 1.0f);
        m1.setConcat(m2, m1);
        mSepiaPaint.setColorFilter(new ColorMatrixColorFilter(m1));
        mFoldLayout.setNumberOfFolds(mNumberOfFolds);
    }

    /**
     * This listener, along with the setSepiaLayer method below, show a possible
     * use case of the OnFoldListener provided with the FoldingLayout. This is a
     * fun extra addition to the demo showing what kind of visual effects can be
     * applied to the child of the FoldingLayout by setting the layer type to
     * hardware. With a hardware layer type applied to the child, a paint object
     * can also be applied to the same layer. Using the concatenation of two
     * different color matrices (above), a color filter was created which
     * simulates a sepia effect on the layer.
     */
    private OnFoldListener mOnFoldListener = new OnFoldListener() {
        @Override
        public void onStartFold() {
            if (mIsSepiaOn) {
                setSepiaLayer(mFoldLayout.getChildAt(0), true);
            }
        }

        @Override
        public void onEndFold() {
            setSepiaLayer(mFoldLayout.getChildAt(0), false);
        }
    };

    @SuppressLint("NewApi")
    private void setSepiaLayer(View view, boolean isSepiaLayerOn) {
        if (!IS_JBMR2) {
            if (isSepiaLayerOn) {
                if (Build.VERSION.SDK_INT >= 17) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                    view.setLayerPaint(mSepiaPaint);
                }

            } else {
                if (Build.VERSION.SDK_INT >= 17) {
                    view.setLayerPaint(mDefaultPaint);
                }

            }
        }
    }

    /**
     * Creates a SurfaceTextureListener in order to prepare a TextureView which
     * displays a live, and continuously updated, feed from the Camera.
     */
    @SuppressLint("NewApi")
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                              int i, int i2) {
            mCamera = Camera.open();

            if (mCamera == null && Camera.getNumberOfCameras() > 1) {
                mCamera = mCamera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }

            if (mCamera == null) {
                return;
            }

            try {
                mCamera.setPreviewTexture(surfaceTexture);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                int i, int i2) {
            // Ignored, Camera does all the work for us
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
            }
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            // Invoked every time there's a new Camera preview frame
        }
    };

    /**
     * A listener for scrolling changes in the seekbar. The anchor point of the
     * folding view is updated every time the seekbar stops tracking touch
     * events. Every time the anchor point is updated, the folding view is
     * restored to a default unfolded state.
     */
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // mTranslation = 0;
            mAnchorFactor = ((float) mAnchorSeekBar.getProgress()) / 100.0f;
            mFoldLayout.setAnchorFactor(mAnchorFactor);
        }
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (IS_JBMR2) {
//            getMenuInflater().inflate(R.menu.fold_with_bug, menu);
//        } else {
//            getMenuInflater().inflate(R.menu.fold, menu);
//        }
//        MenuItem spinerItem = menu.findItem(R.id.num_of_folds);
//        Spinner s = (Spinner) MenuItemCompat.getActionView(spinerItem);
//
//        s.setOnItemSelectedListener(mItemSelectedListener);
//
//        return true;
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // int[] loc = new int[2];
        // mFoldLayout.getLocationOnScreen(loc);
        // mParentPositionY = loc[1];
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.animate_fold:
//                animateFold();
//                break;
//            case R.id.toggle_orientation:
//                mOrientation = (mOrientation == Orientation.HORIZONTAL) ? Orientation.VERTICAL
//                        : Orientation.HORIZONTAL;
//                item.setTitle((mOrientation == Orientation.HORIZONTAL) ? R.string.vertical
//                        : R.string.horizontal);
//                // mTranslation = 0;
//                mFoldLayout.setOrientation(mOrientation);
//                break;
//            case R.id.camera_feed:
//                if (IS_ISC) {
//                    mIsCameraFeed = !mIsCameraFeed;
//                    item.setTitle(mIsCameraFeed ? R.string.static_image
//                            : R.string.camera_feed);
//                    item.setChecked(mIsCameraFeed);
//                    if (mIsCameraFeed) {
//                        //	mFoldLayout.removeView(mImageView);
//                        //	mFoldLayout.addView(mTextureView,
//                        //		new ViewGroup.LayoutParams(mFoldLayout.getWidth(),
//                        //				mFoldLayout.getHeight()));
//                    } else {
//                        //mFoldLayout.removeView(mTextureView);
//                        //mFoldLayout.addView(mImageView, new ViewGroup.LayoutParams(
//                        //		mFoldLayout.getWidth(), mFoldLayout.getHeight()));
//                    }
//                    // mTranslation = 0;
//                }
//                break;
//            case R.id.sepia:
//                mIsSepiaOn = !mIsSepiaOn;
//                item.setChecked(mIsSepiaOn);
//                if (mIsSepiaOn && mFoldLayout.getFoldFactor() != 0) {
//                    setSepiaLayer(mFoldLayout.getChildAt(0), true);
//                } else {
//                    setSepiaLayer(mFoldLayout.getChildAt(0), false);
//                }
//                break;
//            default:
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * Animates the folding view inwards (to a completely folded state) from its
     * current state and then back out to its original state.
     */
    public void animateFold() {
        float foldFactor = mFoldLayout.getFoldFactor();

        ObjectAnimator animator = ObjectAnimator.ofFloat(mFoldLayout,
                "foldFactor", foldFactor, 1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        animator.setDuration(FOLD_ANIMATION_DURATION);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    /**
     * Listens for selection events of the spinner located on the action bar.
     * Every time a new value is selected, the number of folds in the folding
     * view is updated and is also restored to a default unfolded state.
     */
    private class ItemSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {
            mNumberOfFolds = Integer.parseInt(parent.getItemAtPosition(pos)
                    .toString());
            if (mDidLoadSpinner) {
                mDidLoadSpinner = false;
            } else {
                // mTranslation = 0;
                mFoldLayout.setNumberOfFolds(mNumberOfFolds);

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

}