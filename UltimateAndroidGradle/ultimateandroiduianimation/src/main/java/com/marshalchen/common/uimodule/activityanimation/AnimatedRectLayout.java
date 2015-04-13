package com.marshalchen.common.uimodule.activityanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class AnimatedRectLayout extends ViewGroup {

    private static final String TAG = "AnimatedRectLayout";

    public static final int ANIMATION_RANDOM = 1;
    public static final int ANIMATION_WAVE_TL = 2;
    public static final int ANIMATION_WAVE_TR = 4;
    public static final int ANIMATION_WAVE_BR = 8;
    public static final int ANIMATION_WAVE_BL = 16;

    public static final int RECT_COUNT_IN_WIDTH = 10;

    static final boolean IS_JBMR2 = Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;

    private static SparseArray<IndexesBuilder> sIndexesBuilder = new SparseArray<IndexesBuilder>();

    private int mAnimationType;

    private int mRectCountInHeight;
    private int mRectCountInWidth;

    private Rect[][] mRects;
    private int[][] mRectIndexes;

    private float mProgress;

    private Bitmap mFullBitmap;

    public AnimatedRectLayout(Context context) {
        super(context);
    }

    public AnimatedRectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedRectLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAnimationType(int animationType) {
        mAnimationType = animationType;
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
        updateGrid();
    }

    private void throwCustomException (int numOfChildViews) {
        if (numOfChildViews == 1) {
            throw new IllegalArgumentException("only one child please");
        }
    }

    private void updateGrid() {
        prepareGrid();
        invalidate();
    }

    private void prepareGrid() {
        if(isInEditMode()) {
            return;
        }
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        int rectWidth = w/ RECT_COUNT_IN_WIDTH;
        int rectHeight = rectWidth;

        mRectCountInWidth = w/rectWidth;
        mRectCountInHeight = h/rectHeight;

        int delta = w%rectWidth;
        if(delta>0) {
            rectWidth += delta/mRectCountInWidth;
        }

        delta = h%rectWidth;
        if(delta>0) {
            rectHeight += delta/mRectCountInHeight;
        }

        mRects = new Rect[mRectCountInWidth][mRectCountInHeight];
        mRectIndexes = new int[mRectCountInWidth * mRectCountInHeight][2];

        for (int x = 0; x < mRectCountInWidth; x++) {
            for (int y = 0; y < mRectCountInHeight; y++) {
                int left = x * rectWidth;
                int top = y * rectHeight;
                int right = left + rectWidth;
                int bottom = top + rectHeight;

                if (x + 1 >= mRectCountInWidth) {
                    right = w;
                }
                if (y + 1 >= mRectCountInHeight) {
                    bottom = h;
                }

                mRects[x][y] = new Rect(left, top, right, bottom);
            }
        }

        sIndexesBuilder.get(mAnimationType).build(mRectIndexes, mRectCountInWidth, mRectCountInHeight);

        if (IS_JBMR2) {
            mFullBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
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

        int threshold = (int) (mRectCountInWidth * mRectCountInHeight * mProgress);

        for(int i=0 ; i<mRectIndexes.length ; i++) {
            if(i >= threshold) {
                return;
            }
            canvas.save();

            int[] index = mRectIndexes[i];
            Rect rect = mRects[index[0]][index[1]];

            if(IS_JBMR2) {
                canvas.drawBitmap(mFullBitmap, rect, rect, null);
            } else {
                canvas.clipRect(rect);
                super.dispatchDraw(canvas);
            }

            canvas.restore();
        }
    }

    private interface IndexesBuilder {
        public int[][] build(int[][] indexes, int rectCountInWidth, int rectCountInHeight);
    }

    private static final IndexesBuilder sRandomIndexesBuilder  = new IndexesBuilder() {
        @Override
        public int[][] build(int[][] indexes, int rectCountInWidth, int rectCountInHeight) {
            int index = 0;
            for (int x = 0; x < rectCountInWidth; x++) {
                for (int y = 0; y < rectCountInHeight; y++) {
                    indexes[index][0] = x;
                    indexes[index][1] = y;
                    index++;
                }
            }
            return shuffle(indexes);
        }
    };

    private static final IndexesBuilder sBottomLeftWaveIndexesBuilder  = new IndexesBuilder() {
        @Override
        public int[][] build(int[][] indexes, int rectCountInWidth, int rectCountInHeight) {
            int index = 0;
            for (int k = 0; k < rectCountInHeight + rectCountInWidth; ++k) {
                int z = k < rectCountInHeight ? 0 : k - rectCountInHeight + 1;
                for (int i = z; i <= k - z; ++i) {
                    int j = (rectCountInHeight -1)-(k-i);
                    if(i < rectCountInWidth && j < rectCountInHeight) {
                        indexes[index][0] = i;
                        indexes[index][1] = j;
                        index++;
                    }
                }
            }
            return indexes;
        }
    };

    private static final IndexesBuilder sTopRightWaveIndexesBuilder  = new IndexesBuilder() {
        @Override
        public int[][] build(int[][] indexes, int rectCountInWidth, int rectCountInHeight) {
            return reverse(sBottomLeftWaveIndexesBuilder.build(indexes, rectCountInWidth, rectCountInHeight));
        }
    };

    private static final IndexesBuilder sTopLeftWaveIndexesBuilder  = new IndexesBuilder() {
        @Override
        public int[][] build(int[][] indexes, int rectCountInWidth, int rectCountInHeight) {
            int index = 0;
            for(int k = 0, max = rectCountInHeight + rectCountInWidth; k < max ; k++) {
                for(int j = 0 ; j <= k ; j++ ) {
                    int i = k - j;
                    if(i < rectCountInWidth && j < rectCountInHeight) {
                        indexes[index][0] = i;
                        indexes[index][1] = j;
                        index++;
                    }
                }
            }
            return indexes;
        }
    };

    private static final IndexesBuilder sBottomRightWaveIndexesBuilder  = new IndexesBuilder() {
        @Override
        public int[][] build(int[][] indexes, int rectCountInWidth, int rectCountInHeight) {
            return reverse(sTopLeftWaveIndexesBuilder.build(indexes, rectCountInWidth, rectCountInHeight));
        }
    };

    static {
        sIndexesBuilder.put(ANIMATION_RANDOM, sRandomIndexesBuilder);
        sIndexesBuilder.put(ANIMATION_WAVE_TL, sTopLeftWaveIndexesBuilder);
        sIndexesBuilder.put(ANIMATION_WAVE_TR, sTopRightWaveIndexesBuilder);
        sIndexesBuilder.put(ANIMATION_WAVE_BR, sBottomRightWaveIndexesBuilder);
        sIndexesBuilder.put(ANIMATION_WAVE_BL, sBottomLeftWaveIndexesBuilder);
    }

    private static int[][] shuffle(int[][] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i));
            int[] tmp = array[i];
            array[i] = array[r];
            array[r] = tmp;
        }
        return array;
    }

    private static int[][] reverse(int[][] array) {
        int i = 0;
        int j = array.length - 1;
        int[] tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }
}
