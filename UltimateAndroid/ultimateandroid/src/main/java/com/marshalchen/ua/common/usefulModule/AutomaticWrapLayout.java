package com.marshalchen.ua.common.usefulModule;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ua.common.commonUtils.uiUtils.UiUtils;


/**
 *
 * User: cym
 * Date: 13-10-28
 * Time: 下午4:45
 *
 */
//AutomaticWrapLayout
public class AutomaticWrapLayout extends ViewGroup {
    private  static int VIEW_MARGIN = 12;
    public static int countsArray[] = new int[2];
    public static int rowsHeight=0;
    Context context=null;

    public AutomaticWrapLayout(Context context) {
        super(context);
        AutomaticWrapLayout.countsArray[0]=0;
        AutomaticWrapLayout.countsArray[1]=0;
        VIEW_MARGIN= UiUtils.dip2px(context, 6.0f);
        this.context=context;

    }

    public AutomaticWrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        VIEW_MARGIN= UiUtils.dip2px(context,6.0f);
        this.context=context;
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AutomaticWrapLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Logs.d("on touchevent");
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Logs.d(getParent().getClass().getName() +
//                    "  ssss  " + getParent().getParent().getClass().getName());
//            getParent().requestDisallowInterceptTouchEvent(true);
//            getParent().getParent().requestDisallowInterceptTouchEvent(true);
//            getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            //   getParent().requestDisallowInterceptTouchEvent(false);
//        }
//        getParent().requestDisallowInterceptTouchEvent(true);
//        getParent().getParent().requestDisallowInterceptTouchEvent(true);
//        getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//        return true;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        return  true;
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

                int width = child.getMeasuredWidth()+VIEW_MARGIN;
                int height = child.getMeasuredHeight();
                x += width;
                y = row * height + height+row*(VIEW_MARGIN);

                if (x > maxWidth) {
                    x = width+VIEW_MARGIN;
                    row++;
                    if (row > 1){
                        // Logs.d("historicalFav.size--"+index);
                        if (context!=null){

                        }
                        break;
                    }
                    x+=VIEW_MARGIN;
                    y = row * height + height+row*(VIEW_MARGIN);

                }
            }
        }
        setMeasuredDimension(maxWidth, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        int arrayx = 0;
        int arrayy = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width+VIEW_MARGIN;
                y = row * height + height+row*(VIEW_MARGIN);
                arrayy++;
                countsArray[arrayx] = arrayy;

                if (x > maxWidth) {
                    x = width+VIEW_MARGIN;
                    row++;
                    y = row * height + height+row*(VIEW_MARGIN);
                    countsArray[arrayx] = arrayy - 1;
                    arrayx++;
                    arrayy = 0;
                }

                if (row > 1) break;
                child.layout(x - width, y - height, x, y);
                rowsHeight=y-height;
            }
        }
        if (row > 0) countsArray[1]++;

    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;

        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }


    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);    //To change body of overridden methods use File | Settings | File Templates.
    }
//
//    /**
//     * Add margin
//     */
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        int width = getWidth();
//        int height = getHeight();
//        Paint mPaint = new Paint();
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(10);
//        mPaint.setAntiAlias(true);
//        Rect mRect = new Rect(0, 0, width, height);
//        canvas.drawRect(mRect, mPaint);
//        super.dispatchDraw(canvas);
//    }

}


