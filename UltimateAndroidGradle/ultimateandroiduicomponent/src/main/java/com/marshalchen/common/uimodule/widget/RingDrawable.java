package com.marshalchen.common.uimodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.TypedValue;

/**
 * Created by baoyz on 14/11/2.
 */
class RingDrawable extends RefreshDrawable{

    private static final int MAX_LEVEL = 200;

    private boolean isRunning;
    private RectF mBounds;
    private int mWidth;
    private int mHeight;
    private int mTop;
    private int mOffsetTop;
    private Paint mPaint;
    private Path mPath;
    private float mAngle;
    private int[] mColorSchemeColors;
    private Handler mHandler = new Handler();
    private int mLevel;
    private float mDegress;

    RingDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(3));
        mPath = new Path();
    }

    @Override
    public void setPercent(float percent) {
        mPaint.setColor(evaluate(percent, mColorSchemeColors[0], mColorSchemeColors[1]));
    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {
        mColorSchemeColors = colorSchemeColors;
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        mOffsetTop += offset;
        float offsetTop = mOffsetTop - dp2px(20);
        if (offsetTop <= 0) {
            mAngle = 0;
        }else {
            int finalOffset = getRefreshLayout().getFinalOffset() - dp2px(20);
            if (offsetTop > finalOffset) {
                offsetTop = finalOffset;
            }
            mAngle = 340 * (offsetTop / finalOffset);
        }
        invalidateSelf();
    }

    @Override
    public void start() {
        mLevel = 50;
        isRunning = true;
        mHandler.post(mAnimationTask);
    }

    private Runnable mAnimationTask = new Runnable(){
        @Override
        public void run() {
            if (isRunning()){
                mLevel++;
                if (mLevel > MAX_LEVEL)
                    mLevel = 0;
                updateLevel(mLevel);
                invalidateSelf();
                mHandler.postDelayed(this, 20);
            }
        }
    };

    private void updateLevel(int level) {
        int animationLevel = level == MAX_LEVEL ? 0 : level;

        int stateForLevel = (animationLevel / 50);

        float percent = level % 50 / 50f;
        int startColor = mColorSchemeColors[stateForLevel];
        int endColor = mColorSchemeColors[(stateForLevel + 1) % mColorSchemeColors.length];
        mPaint.setColor(evaluate(percent, startColor, endColor));

        mDegress = 360 * percent;
    }

    @Override
    public void stop() {
        isRunning = false;
        mHandler.removeCallbacks(mAnimationTask);
        mDegress = 0;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mWidth = getRefreshLayout().getFinalOffset();
        mHeight = mWidth;
        mBounds = new RectF(bounds.width() / 2 - mWidth / 2, bounds.top, bounds.width() / 2 + mWidth / 2, bounds.top + mHeight);
        mBounds.inset(dp2px(15), dp2px(15));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
//        canvas.translate(0, mTop);
        canvas.rotate(mDegress, mBounds.centerX(), mBounds.centerY());
        drawRing(canvas);
        canvas.restore();
    }

    private void drawRing(Canvas canvas){
        mPath.reset();
        mPath.arcTo(mBounds, 270, mAngle, true);
        canvas.drawPath(mPath, mPaint);
//        canvas.drawArc(mBounds, 270, mAngle, true, mPaint);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    private int evaluate(float fraction, int startValue, int endValue) {
        int startInt = startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

}
