package com.marshalchen.common.uimodule.foldablelayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.marshalchen.common.uimodule.foldablelayout.shading.FoldShading;


/**
 * Layout that provides basic funcitionality: splitting view into 2 parts, splitted parts syncronouse rotations and so on
 */
public class FoldableItemLayout extends FrameLayout {

    private static final int CAMERA_DISTANCE = 48;
    private static final float CAMERA_DISTANCE_MAGIC_FACTOR = 8f / CAMERA_DISTANCE;

    private boolean mIsAutoScaleEnabled;

    private BaseLayout mBaseLayout;
    private PartView mTopPart, mBottomPart;

    private int mWidth, mHeight;
    private Bitmap mCacheBitmap;

    private boolean mIsInTransformation;

    private float mFoldRotation;
    private float mScale;
    private float mRollingDistance;

    public FoldableItemLayout(Context context) {
        super(context);
        init(context);
    }

    public FoldableItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FoldableItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mBaseLayout = new BaseLayout(this);

        mTopPart = new PartView(this, Gravity.TOP);
        mBottomPart = new PartView(this, Gravity.BOTTOM);

        setInTransformation(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBaseLayout.moveInflatedChildren(this, 3); // skipping mBaseLayout & mTopPart & mBottomPart views
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        if (mCacheBitmap != null) {
            mCacheBitmap.recycle();
            mCacheBitmap = null;
        }

        mCacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        mBaseLayout.setCacheCanvas(new Canvas(mCacheBitmap));

        mTopPart.setCacheBitmap(mCacheBitmap);
        mBottomPart.setCacheBitmap(mCacheBitmap);
    }

    /**
     * Fold rotation value in degrees
     */
    public void setFoldRotation(float rotation) {
        mFoldRotation = rotation;

        mTopPart.applyFoldRotation(rotation);
        mBottomPart.applyFoldRotation(rotation);

        setInTransformation(rotation != 0);

        if (mIsAutoScaleEnabled) {
            float viewScale = 1.0f;
            if (mWidth > 0) {
                float dW = (float) (mHeight * Math.abs(Math.sin(Math.toRadians(rotation)))) * CAMERA_DISTANCE_MAGIC_FACTOR;
                viewScale = mWidth / (mWidth + dW);
            }

            setScale(viewScale);
        }
    }

    public float getFoldRotation() {
        return mFoldRotation;
    }

    public void setScale(float scale) {
        mScale = scale;
        mTopPart.applyScale(scale);
        mBottomPart.applyScale(scale);
    }

    public float getScale() {
        return mScale;
    }

    /**
     * Translation preserving middle line splitting
     */
    public void setRollingDistance(float distance) {
        mRollingDistance = distance;
        mTopPart.applyRollingDistance(distance, mScale);
        mBottomPart.applyRollingDistance(distance, mScale);
    }

    public float getRollingDistance() {
        return mRollingDistance;
    }

    private void setInTransformation(boolean isInTransformation) {
        if (mIsInTransformation == isInTransformation) return;
        mIsInTransformation = isInTransformation;

        mBaseLayout.setDrawToCache(isInTransformation);
        mTopPart.setVisibility(isInTransformation ? VISIBLE : INVISIBLE);
        mBottomPart.setVisibility(isInTransformation ? VISIBLE : INVISIBLE);
    }

    public void setAutoScaleEnabled(boolean isAutoScaleEnabled) {
        mIsAutoScaleEnabled = isAutoScaleEnabled;
    }

    public FrameLayout getBaseLayout() {
        return mBaseLayout;
    }

    public void setLayoutVisibleBounds(Rect visibleBounds) {
        mTopPart.setVisibleBounds(visibleBounds);
        mBottomPart.setVisibleBounds(visibleBounds);
    }

    public void setFoldShading(FoldShading shading) {
        mTopPart.setFoldShading(shading);
        mBottomPart.setFoldShading(shading);
    }


    /**
     * View holder layout that can draw itself into given canvas
     */
    private static class BaseLayout extends FrameLayout {

        private Canvas mCacheCanvas;
        private boolean mIsDrawToCache;

        @SuppressWarnings("deprecation")
        private BaseLayout(FoldableItemLayout layout) {
            super(layout.getContext());

            int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
            LayoutParams params = new LayoutParams(matchParent, matchParent);
            layout.addView(this, params);

            // Moving background
            this.setBackgroundDrawable(layout.getBackground());
            layout.setBackgroundDrawable(null);

            setWillNotDraw(false);
        }

        private void moveInflatedChildren(FoldableItemLayout layout, int firstSkippedItems) {
            while (layout.getChildCount() > firstSkippedItems) {
                View view = layout.getChildAt(firstSkippedItems);
                LayoutParams params = (LayoutParams) view.getLayoutParams();
                layout.removeViewAt(firstSkippedItems);
                addView(view, params);
            }
        }

        @Override
        public void draw(Canvas canvas) {
            if (mIsDrawToCache) {
                mCacheCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                super.draw(mCacheCanvas);
            } else {
                super.draw(canvas);
            }
        }

        private void setCacheCanvas(Canvas cacheCanvas) {
            mCacheCanvas = cacheCanvas;
        }

        private void setDrawToCache(boolean drawToCache) {
            if (mIsDrawToCache == drawToCache) return;
            mIsDrawToCache = drawToCache;
            invalidate();
        }

    }

    /**
     * Splitted part view. It will draw top or bottom part of cached bitmap and overlay shadows.
     * Also it contains main logic for all transformations (fold rotation, scale, "rolling distance").
     */
    private static class PartView extends View {

        private final int mGravity;

        private Bitmap mBitmap;
        private final Rect mBitmapBounds = new Rect();

        private float mClippingFactor = 0.5f;

        private final Paint mBitmapPaint;

        private Rect mVisibleBounds;

        private int mInternalVisibility;
        private int mExtrenalVisibility;

        private float mLocalFoldRotation;
        private FoldShading mShading;

        public PartView(FoldableItemLayout parent, int gravity) {
            super(parent.getContext());
            mGravity = gravity;

            final int matchParent = LayoutParams.MATCH_PARENT;
            parent.addView(this, new LayoutParams(matchParent, matchParent));
            setCameraDistance(CAMERA_DISTANCE * getResources().getDisplayMetrics().densityDpi);

            mBitmapPaint = new Paint();
            mBitmapPaint.setDither(true);
            mBitmapPaint.setFilterBitmap(true);

            setWillNotDraw(false);
        }

        private void setCacheBitmap(Bitmap bitmap) {
            mBitmap = bitmap;
            calculateBitmapBounds();
        }

        private void setVisibleBounds(Rect visibleBounds) {
            mVisibleBounds = visibleBounds;
            calculateBitmapBounds();
        }

        private void setFoldShading(FoldShading shading) {
            mShading = shading;
        }

        private void calculateBitmapBounds() {
            if (mBitmap == null) {
                mBitmapBounds.set(0, 0, 0, 0);
            } else {
                int h = mBitmap.getHeight();
                int w = mBitmap.getWidth();

                int top = mGravity == Gravity.TOP ? 0 : (int) (h * (1 - mClippingFactor) - 0.5f);
                int bottom = mGravity == Gravity.TOP ? (int) (h * mClippingFactor + 0.5f) : h;

                mBitmapBounds.set(0, top, w, bottom);
                if (mVisibleBounds != null) {
                    if (!mBitmapBounds.intersect(mVisibleBounds)) {
                        mBitmapBounds.set(0, 0, 0, 0); // no intersection
                    }
                }
            }

            invalidate();
        }

        private void applyFoldRotation(float rotation) {
            float position = rotation;
            while (position < 0) position += 360;
            position %= 360;
            if (position > 180) position -= 360; // now poistion within (-180; 180]

            float rotationX = 0;
            boolean isVisible = true;

            if (mGravity == Gravity.TOP) {
                if (position <= -90 || position == 180) { // (-180; -90] || {180} - will not show
                    isVisible = false;
                } else if (position < 0) { // (-90; 0) - applying rotation
                    rotationX = position;
                }
                // [0; 180) - holding still
            } else {
                if (position >= 90) { // [90; 180] - will not show
                    isVisible = false;
                } else if (position > 0) { // (0; 90) - applying rotation
                    rotationX = position;
                }
                // else: (-180; 0] - holding still
            }

            setRotationX(rotationX);

            mInternalVisibility = isVisible ? VISIBLE : INVISIBLE;
            applyVisibility();

            mLocalFoldRotation = position;

            invalidate(); // needed to draw shadow overlay
        }

        private void applyScale(float scale) {
            setScaleX(scale);
            setScaleY(scale);
        }

        private void applyRollingDistance(float distance, float scale) {
            // applying translation
            setTranslationY((int) (distance * scale + 0.5f));

            // computing clipping for top view (bottom clipping will be 1 - topClipping)
            final int h = getHeight() / 2;
            final float topClipping = h == 0 ? 0.5f : (h - distance) / h / 2;

            mClippingFactor = mGravity == Gravity.TOP ? topClipping : 1f - topClipping;

            calculateBitmapBounds();
        }

        @Override
        public void setVisibility(int visibility) {
            mExtrenalVisibility = visibility;
            applyVisibility();
        }

        private void applyVisibility() {
            super.setVisibility(mExtrenalVisibility == VISIBLE ? mInternalVisibility : mExtrenalVisibility);
        }

        @Override
        public void draw(Canvas canvas) {
            if (mShading != null) mShading.onPreDraw(canvas, mBitmapBounds, mLocalFoldRotation, mGravity);
            if (mBitmap != null) canvas.drawBitmap(mBitmap, mBitmapBounds, mBitmapBounds, mBitmapPaint);
            if (mShading != null) mShading.onPostDraw(canvas, mBitmapBounds, mLocalFoldRotation, mGravity);
        }

    }

}
