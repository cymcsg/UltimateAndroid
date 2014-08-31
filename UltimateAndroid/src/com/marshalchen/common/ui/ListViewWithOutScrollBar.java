package com.marshalchen.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by cym on 14-5-6.
 */
public class ListViewWithOutScrollBar extends ListView {
    public ListViewWithOutScrollBar(Context context) {
        super(context);
    }

    public ListViewWithOutScrollBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewWithOutScrollBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
