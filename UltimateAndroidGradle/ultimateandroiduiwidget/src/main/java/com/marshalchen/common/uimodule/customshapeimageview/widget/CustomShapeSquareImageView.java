package com.marshalchen.common.uimodule.customshapeimageview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomShapeSquareImageView extends CustomShapeImageView {

    public CustomShapeSquareImageView(Context context, int resourceId, int shape, int svgRawResourceId) {
        super(context, resourceId, shape, svgRawResourceId);
    }
    public CustomShapeSquareImageView(Context context) {
        super(context);
    }

    public CustomShapeSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomShapeSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}