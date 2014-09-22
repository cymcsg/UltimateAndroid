package com.marshalchen.common.uimodule.discrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.marshalchen.common.uimodule.R;

/**
 *
 */
public class DiscrollViewContent extends LinearLayout {

    public DiscrollViewContent(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public DiscrollViewContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public DiscrollViewContent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(asDiscrollvable(child, (LayoutParams) params), index, params);
    }

    private View asDiscrollvable(View child, LayoutParams lp) {
        if(! isDiscrollvable(lp)) {
            return child;
        }
        DiscrollvableView discrollvableChild = new DiscrollvableView(getContext());
        discrollvableChild.setDiscrollveAlpha(lp.mDiscrollveAlpha);
        discrollvableChild.setDiscrollveTranslation(lp.mDiscrollveTranslation);
        discrollvableChild.setDiscrollveScaleX(lp.mDiscrollveScaleX);
        discrollvableChild.setDiscrollveScaleY(lp.mDiscrollveScaleY);
        discrollvableChild.setDiscrollveThreshold(lp.mDiscrollveThreshold);
        discrollvableChild.setDiscrollveFromBgColor(lp.mDiscrollveFromBgColor);
        discrollvableChild.setDiscrollveToBgColor(lp.mDiscrollveToBgColor);
        discrollvableChild.addView(child);
        return discrollvableChild;
    }

    private boolean isDiscrollvable(LayoutParams lp) {
        return lp.mDiscrollveAlpha ||
               lp.mDiscrollveTranslation != -1 ||
               lp.mDiscrollveScaleX ||
               lp.mDiscrollveScaleY ||
               (lp.mDiscrollveFromBgColor != -1 && lp.mDiscrollveToBgColor != -1);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {

        private int mDiscrollveFromBgColor;
        private int mDiscrollveToBgColor;
        private float mDiscrollveThreshold;
        public boolean mDiscrollveAlpha;
        public boolean mDiscrollveScaleX;
        public boolean mDiscrollveScaleY;
        private int mDiscrollveTranslation;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams);
            try {
                mDiscrollveAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_alpha, false);
                mDiscrollveScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleX, false);
                mDiscrollveScaleY = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleY, false);
                mDiscrollveTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation, -1);
                mDiscrollveThreshold = a.getFloat(R.styleable.DiscrollView_LayoutParams_discrollve_threshold, 0.0f);
                mDiscrollveFromBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor, -1);
                mDiscrollveToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor, -1);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }
    }
}
