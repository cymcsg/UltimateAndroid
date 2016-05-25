package com.marshalchen.common.uimodule.customPullRefreshLayout.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.TypedValue;

import java.security.InvalidParameterException;

/**
 * Created by baoyz on 14/10/31.
 */
class CirclesDrawable extends RefreshDrawable implements Runnable {

    private static final float MAX_LEVEL = 10000;
    private static final float CIRCLE_COUNT = ProgressStates.values().length;
    private static final float MAX_LEVEL_PER_CIRCLE = MAX_LEVEL / CIRCLE_COUNT;
    private static final int ALPHA_OPAQUE = 255;

    private Paint mFstHalfPaint;
    private Paint mScndHalfPaint;
    private Paint mAbovePaint;
    private RectF mOval = new RectF();
    private int mDiameter;
    private Path mPath;
    private int mHalf;
    private ProgressStates mCurrentState;
    private int mControlPointMinimum;
    private int mControlPointMaximum;
    private int mAxisValue;
    private ColorFilter mColorFilter;
    private static int mColor1;
    private static int mColor2;
    private static int mColor3;
    private static int mColor4;
    private int fstColor, scndColor;
    private boolean goesBackward;
    private Handler mHandler = new Handler();
    private int mLevel;
    private boolean isRunning;
    private int mTop;
    private int mDrawWidth;
    private int mDrawHeight;
    private Rect mBounds;

    public CirclesDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);
    }

    @Override
    public void start() {
        mLevel = 2500;
        isRunning = true;
        mHandler.postDelayed(this, 10);
    }

    @Override
    public void stop() {
        isRunning = false;
        mHandler.removeCallbacks(this);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {
        initCirclesProgress(colorSchemeColors);
    }

    @Override
    public void setPercent(float percent) {
        int level = (int) (2500 * percent);

        updateLevel(level);
    }

    private void updateLevel(int level){
        int animationLevel = level == MAX_LEVEL ? 0 : level;

        int stateForLevel = (int) (animationLevel / MAX_LEVEL_PER_CIRCLE);
        mCurrentState = ProgressStates.values()[stateForLevel];

        resetColor(mCurrentState);
        int levelForCircle = (int) (animationLevel % MAX_LEVEL_PER_CIRCLE);

        boolean halfPassed;
        if (!goesBackward) {
            halfPassed = levelForCircle != (int) (animationLevel % (MAX_LEVEL_PER_CIRCLE / 2));
        } else {
            halfPassed = levelForCircle == (int) (animationLevel % (MAX_LEVEL_PER_CIRCLE / 2));
            levelForCircle = (int) (MAX_LEVEL_PER_CIRCLE - levelForCircle);
        }

        mFstHalfPaint.setColor(fstColor);
        mScndHalfPaint.setColor(scndColor);

        if (!halfPassed) {
            mAbovePaint.setColor(mScndHalfPaint.getColor());
        } else {
            mAbovePaint.setColor(mFstHalfPaint.getColor());
        }

        mAbovePaint.setAlpha(200 + (int) (55 * (levelForCircle / MAX_LEVEL_PER_CIRCLE)));

        mAxisValue = (int) (mControlPointMinimum + (mControlPointMaximum - mControlPointMinimum) * (levelForCircle / MAX_LEVEL_PER_CIRCLE));

    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        invalidateSelf();
    }

    @Override
    public void run() {
        mLevel += 80;
        if (mLevel > MAX_LEVEL)
            mLevel = 0;
        if (isRunning) {
            mHandler.postDelayed(this, 20);
            updateLevel(mLevel);
            invalidateSelf();
        }
    }

    private enum ProgressStates {
        FOLDING_DOWN,
        FOLDING_LEFT,
        FOLDING_UP,
        FOLDING_RIGHT
    }

    private void initCirclesProgress(int[] colors) {
        initColors(colors);
        mPath = new Path();

        Paint basePaint = new Paint();
        basePaint.setAntiAlias(true);

        mFstHalfPaint = new Paint(basePaint);
        mScndHalfPaint = new Paint(basePaint);
        mAbovePaint = new Paint(basePaint);

        setColorFilter(mColorFilter);
    }

    private void initColors(int[] colors) {
        if (colors == null || colors.length < 4)
            throw new InvalidParameterException("The color scheme length must be 4");
        mColor1 = colors[0];
        mColor2 = colors[1];
        mColor3 = colors[2];
        mColor4 = colors[3];
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mDrawWidth = dp2px(40);
        mDrawHeight = mDrawWidth;
        mTop = -mDrawHeight - (getRefreshLayout().getFinalOffset() - mDrawHeight) / 2;
        mBounds = bounds;
        measureCircleProgress(mDrawWidth, mDrawHeight);
    }

    private void resetColor(ProgressStates currentState) {
        switch (currentState) {
            case FOLDING_DOWN:
                fstColor = mColor1;
                scndColor = mColor2;
                goesBackward = false;
                break;
            case FOLDING_LEFT:
                fstColor = mColor1;
                scndColor = mColor3;
                goesBackward = true;
                break;
            case FOLDING_UP:
                fstColor = mColor3;
                scndColor = mColor4;
                goesBackward = true;
                break;
            case FOLDING_RIGHT:
                fstColor = mColor2;
                scndColor = mColor4;
                goesBackward = false;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mCurrentState != null) {
            canvas.save();
            canvas.translate(mBounds.width() / 2 - mDrawWidth / 2, mTop);
            makeCirclesProgress(canvas);
            canvas.restore();
        }
    }

    private void measureCircleProgress(int width, int height) {
        mDiameter = Math.min(width, height);
        mHalf = mDiameter / 2;
        mOval.set(0, 0, mDiameter, mDiameter);
        mControlPointMinimum = -mDiameter / 6;
        mControlPointMaximum = mDiameter + mDiameter / 6;
    }

    private void makeCirclesProgress(Canvas canvas) {

        switch (mCurrentState) {
            case FOLDING_DOWN:
            case FOLDING_UP:
                drawYMotion(canvas);
                break;
            case FOLDING_RIGHT:
            case FOLDING_LEFT:
                drawXMotion(canvas);
                break;
        }

        canvas.drawPath(mPath, mAbovePaint);
    }

    private void drawXMotion(Canvas canvas) {
        canvas.drawArc(mOval, 90, 180, true, mFstHalfPaint);
        canvas.drawArc(mOval, -270, -180, true, mScndHalfPaint);
        mPath.reset();
        mPath.moveTo(mHalf, 0);
        mPath.cubicTo(mAxisValue, 0, mAxisValue, mDiameter, mHalf, mDiameter);
    }

    private void drawYMotion(Canvas canvas) {
        canvas.drawArc(mOval, 0, -180, true, mFstHalfPaint);
        canvas.drawArc(mOval, -180, -180, true, mScndHalfPaint);
        mPath.reset();
        mPath.moveTo(0, mHalf);
        mPath.cubicTo(0, mAxisValue, mDiameter, mAxisValue, mDiameter, mHalf);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        this.mColorFilter = cf;
        mFstHalfPaint.setColorFilter(cf);
        mScndHalfPaint.setColorFilter(cf);
        mAbovePaint.setColorFilter(cf);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
