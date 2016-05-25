package com.marshalchen.common.uimodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.TypedValue;

/**
 * Created by baoyz on 14/11/2.
 */
class ArcDrawable extends RefreshDrawable{

    private static final int MAX_LEVEL = 200;

    private boolean isRunning;
    private RectF mBounds;
    private int mWidth;
    private int mHeight;
    private int mTop;
    private int mOffsetTop;
    private Paint mPaint;
    private float mAngle;
    private int[] mColorSchemeColors;
    private Handler mHandler = new Handler();
    private int mLevel;

    ArcDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void setPercent(float percent) {
        mPaint.setColor(evaluate(percent, mColorSchemeColors[3], mColorSchemeColors[0]));
    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {
        mColorSchemeColors = colorSchemeColors;
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        mOffsetTop += offset;
        float offsetTop = mOffsetTop;
        if (mOffsetTop > getRefreshLayout().getFinalOffset()){
            offsetTop = getRefreshLayout().getFinalOffset();
        }
        mAngle = 360 * (offsetTop / getRefreshLayout().getFinalOffset());
        invalidateSelf();
    }

    @Override
    public void start() {
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
    }

    @Override
    public void stop() {
        isRunning = false;
        mHandler.removeCallbacks(mAnimationTask);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mWidth = dp2px(40);
        mHeight = mWidth;
        mBounds = new RectF(bounds.width() / 2 - mWidth / 2, bounds.top, bounds.width() / 2 + mWidth / 2, bounds.top + mHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
//        canvas.translate(0, mTop);
        drawRing(canvas);
        canvas.restore();
    }

    private void drawRing(Canvas canvas){
        canvas.drawArc(mBounds, 270, mAngle, true, mPaint);
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
