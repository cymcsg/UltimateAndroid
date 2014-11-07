package com.marshalchen.common.demoofui.discrollview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.marshalchen.common.uimodule.discrollview.Discrollvable;
import com.marshalchen.common.demoofui.R;


/**
 *
 */
public class DiscrollvablePathLayout extends LinearLayout implements Discrollvable {

    private float mRatio;
    private Paint mPaint;
    private View mPathView;

    public DiscrollvablePathLayout(Context context) {
        super(context);
        initPaint();
    }

    public DiscrollvablePathLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DiscrollvablePathLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPathView = findViewById(R.id.pathView);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5.0f);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.argb(180, 255, 255, 255));
    }

    @Override
    public void onDiscrollve(float ratio) {
        mRatio = ratio;
        mPathView.setAlpha(ratio);
        mPathView.setTranslationY(-(mPathView.getHeight()/2) * ((ratio - 0.5f) / 0.5f));
        invalidate();
    }

    @Override
    public void onResetDiscrollve() {
        mRatio = 0.0f;
        mPathView.setAlpha(0);
        mPathView.setTranslationY(mPathView.getHeight()/2);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        makeAndMeasurePath();

        if(!isInEditMode()) {
            // Apply the dash effect
            float length = mPathMeasure.getLength();
            PathEffect effect = new DashPathEffect(new float[] {length, length }, length * (1 - mRatio));
            mPaint.setPathEffect(effect);
        }

        canvas.drawPath(mPath, mPaint);
    }

    private PathMeasure mPathMeasure = new PathMeasure();
    private Path mPath = new Path();

    private void makeAndMeasurePath() {
        mPath.reset();
        float translationY = mPathView.getTranslationY();
        mPath.moveTo(mPathView.getLeft(), mPathView.getTop() + translationY);
        mPath.lineTo(mPathView.getLeft() + mPathView.getWidth(), mPathView.getTop() + translationY);
        mPath.lineTo(mPathView.getLeft() + mPathView.getWidth(), mPathView.getTop() + mPathView.getHeight() + translationY);
        mPath.lineTo(mPathView.getLeft(), mPathView.getTop() + mPathView.getHeight() + translationY);
        mPath.close();
        mPathMeasure.setPath(mPath, false);
    }
}
