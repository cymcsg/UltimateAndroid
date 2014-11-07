package com.marshalchen.common.uimodule.customPullRefreshLayout.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;

import java.security.InvalidParameterException;

/**
 * Created by baoyz on 14/10/31.
 */
class WaterDropDrawable extends RefreshDrawable implements Runnable {

    private static final float MAX_LEVEL = 10000;
    private static final float CIRCLE_COUNT = ProgressStates.values().length;
    private static final float MAX_LEVEL_PER_CIRCLE = MAX_LEVEL / CIRCLE_COUNT;

    private int mLevel;
    private Point p1, p2, p3, p4;
    private Paint mPaint;
    private Path mPath;
    private int mHeight;
    private int mWidth;
    private int mTop;
    private int[] mColorSchemeColors;
    private ProgressStates mCurrentState;
    private Handler mHandler = new Handler();
    private boolean isRunning;

    private enum ProgressStates {
        ONE,
        TWO,
        TREE,
        FOUR
    }

    public WaterDropDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPath = new Path();
        p1 = new Point();
        p2 = new Point();
        p3 = new Point();
        p4 = new Point();
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.translate(0, mTop > 0 ? mTop : 0);

        mPath.reset();
        mPath.moveTo(p1.x, p1.y);
        mPath.cubicTo(p3.x, p3.y, p4.x, p4.y, p2.x, p2.y);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mWidth = bounds.width();
        updateBounds();
        super.onBoundsChange(bounds);
    }

    private void updateBounds() {

        int height = mHeight;
        int width = mWidth;

        if (height > getRefreshLayout().getFinalOffset()) {
            height = getRefreshLayout().getFinalOffset();
        }

        final float percent = height / (float) getRefreshLayout().getFinalOffset();
        int offsetX = (int) (width / 2 * percent);
        int offsetY = 0;
        p1.set(offsetX, offsetY);
        p2.set(width - offsetX, offsetY);
        p3.set(width / 2 - height, height);
        p4.set(width / 2 + height, height);
    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {
        if (colorSchemeColors == null || colorSchemeColors.length < 4)
            throw new InvalidParameterException("The color scheme length must be 4");
        mPaint.setColor(colorSchemeColors[0]);
        mColorSchemeColors = colorSchemeColors;
    }

    @Override
    public void setPercent(float percent) {
        mPaint.setColor(evaluate(percent, mColorSchemeColors[0], mColorSchemeColors[1]));
    }

    private void updateLevel(int level) {
        int animationLevel = level == MAX_LEVEL ? 0 : level;

        int stateForLevel = (int) (animationLevel / MAX_LEVEL_PER_CIRCLE);
        mCurrentState = ProgressStates.values()[stateForLevel];

        float percent = level % 2500 / 2500f;
        int startColor = mColorSchemeColors[stateForLevel];
        int endColor = mColorSchemeColors[(stateForLevel + 1) % ProgressStates.values().length];
        mPaint.setColor(evaluate(percent, startColor, endColor));
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mHeight += offset;
        mTop = mHeight - getRefreshLayout().getFinalOffset();
        updateBounds();
        invalidateSelf();
    }

    @Override
    public void start() {
        mLevel = 2500;
        isRunning = true;
        mHandler.postDelayed(this, 20);
    }

    @Override
    public void stop() {
        mHandler.removeCallbacks(this);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        mLevel += 60;
        if (mLevel > MAX_LEVEL)
            mLevel = 0;
        if (isRunning) {
            mHandler.postDelayed(this, 20);
            updateLevel(mLevel);
            invalidateSelf();
        }
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