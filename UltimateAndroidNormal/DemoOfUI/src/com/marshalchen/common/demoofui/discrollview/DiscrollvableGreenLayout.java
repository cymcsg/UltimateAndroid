package com.marshalchen.common.demoofui.discrollview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.marshalchen.common.uimodule.discrollview.Discrollvable;
import com.marshalchen.common.demoofui.R;


/**
 *
 */
public class DiscrollvableGreenLayout extends LinearLayout implements Discrollvable {

    private static final String TAG = "DiscrollvableGreenLayout";

    private TextView mGreenView1;
    private float mGreenView1TranslationY;
    private int mGreenColor;
    private int mBlackColor = Color.BLACK;
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    public DiscrollvableGreenLayout(Context context) {
        super(context);
    }

    public DiscrollvableGreenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollvableGreenLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mGreenView1 = (TextView) findViewById(R.id.greenView1);
        mGreenView1TranslationY = mGreenView1.getTranslationY();
        mGreenColor = getResources().getColor(android.R.color.holo_green_light);
    }

    @Override
    public void onResetDiscrollve() {
        mGreenView1.setTranslationY(mGreenView1TranslationY);
        mGreenView1.setTextColor(mGreenColor);
        setBackgroundColor(mBlackColor);
    }

    @Override
    public void onDiscrollve(float ratio) {
        mGreenView1.setTranslationY(mGreenView1TranslationY * (1 - ratio));
        if(ratio >= 0.5f) {
            ratio = (ratio - 0.5f) / 0.5f;
            mGreenView1.setTextColor((Integer) mArgbEvaluator.evaluate(ratio, mBlackColor, mGreenColor));
            setBackgroundColor((Integer) mArgbEvaluator.evaluate(ratio, mGreenColor, mBlackColor));
        }
    }
}
