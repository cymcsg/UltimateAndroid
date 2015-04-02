package com.marshalchen.common.uimodule.activityanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AnimatedDoorLayout extends ViewGroup {

    private static final String TAG = "AnimatedDoorLayout";

    static final boolean IS_JBMR2 = Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;

    public static final int HORIZONTAL_DOOR = 1;
    public static final int VERTICAL_DOOR = 2;

    private Rect mRect = new Rect();

    private int mOriginalWidth;
    private int mOriginalHeight;

    private int mDoorType;

    private float mProgress;

    private Bitmap mFullBitmap;

    public AnimatedDoorLayout(Context context) {
        super(context);
    }

    public AnimatedDoorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedDoorLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDoorType(int doorType) {
        mDoorType = doorType;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
        throwCustomException(getChildCount());
        boolean returnValue = super.addViewInLayout(child, index, params, preventRequestLayout);
        return returnValue;
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        throwCustomException(getChildCount());
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        updateDoor();
    }

    private void throwCustomException (int numOfChildViews) {
        if (numOfChildViews == 1) {
            throw new IllegalArgumentException("only one child please");
        }
    }

    private void updateDoor() {
        prepareDoor();
        invalidate();
    }

    private void prepareDoor() {
        if(isInEditMode()) {
            return;
        }

        mOriginalWidth = getMeasuredWidth();
        mOriginalHeight = getMeasuredHeight();

        if (IS_JBMR2) {
            mFullBitmap = Bitmap.createBitmap(mOriginalWidth, mOriginalHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mFullBitmap);
            getChildAt(0).draw(canvas);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(isInEditMode() || mProgress >= 1f) {
            super.dispatchDraw(canvas);
            return;
        }

        int delta;
        if(mDoorType == VERTICAL_DOOR) {
            delta = (int) ((mOriginalHeight/2)*mProgress);
        } else {
            delta = (int) ((mOriginalWidth/2)*mProgress);
        }

        //1st door
        canvas.save();
        if(mDoorType == VERTICAL_DOOR) {
            mRect.set(0, 0, mOriginalWidth, delta);
        } else {
            mRect.set(0, 0, delta, mOriginalHeight);
        }
        if (IS_JBMR2) {
            canvas.drawBitmap(mFullBitmap, mRect, mRect, null);
        } else {
            canvas.clipRect(mRect);
            super.dispatchDraw(canvas);
        }
        canvas.restore();

        //2nd door
        canvas.save();
        if(mDoorType == VERTICAL_DOOR) {
            mRect.set(0, mOriginalHeight - delta, mOriginalWidth, mOriginalHeight);
        } else {
            mRect.set(mOriginalWidth - delta, 0, mOriginalWidth, mOriginalHeight);
        }
        if (IS_JBMR2) {
            canvas.drawBitmap(mFullBitmap, mRect, mRect, null);
        } else {
            canvas.clipRect(mRect);
            super.dispatchDraw(canvas);
        }
        canvas.restore();
    }
}
