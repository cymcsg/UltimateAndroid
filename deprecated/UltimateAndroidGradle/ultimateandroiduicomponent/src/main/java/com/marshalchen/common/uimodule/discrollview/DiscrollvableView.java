package com.marshalchen.common.uimodule.discrollview;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DiscrollvableView extends FrameLayout implements Discrollvable {

    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();

    private float mDiscrollveThreshold;
    private int mDiscrollveFromBgColor;
    private int mDiscrollveToBgColor;
    private boolean mDiscrollveAlpha;
    private int mDiscrollveTranslation;
    private boolean mDiscrollveScaleX;
    private boolean mDiscrollveScaleY;

    private int mWidth;
    private int mHeight;

    public DiscrollvableView(Context context) {
        super(context);
    }

    public DiscrollvableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollvableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onResetDiscrollve();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void setDiscrollveTranslation(int discrollveTranslation) {
        mDiscrollveTranslation = discrollveTranslation;
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM) && isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)) {
            throw new IllegalArgumentException("cannot translate from bottom and top");
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT) && isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            throw new IllegalArgumentException("cannot translate from left and right");
        }
    }

    public void setDiscrollveThreshold(float discrollveThreshold) {
        if(discrollveThreshold < 0.0f || discrollveThreshold > 1.0f) {
            throw new IllegalArgumentException("threshold must be >= 0.0f and <= 1.0f");
        }
        mDiscrollveThreshold = discrollveThreshold;
    }

    public void setDiscrollveFromBgColor(int discrollveFromBgColor) {
        mDiscrollveFromBgColor = discrollveFromBgColor;
    }

    public void setDiscrollveToBgColor(int discrollveToBgColor) {
        mDiscrollveToBgColor = discrollveToBgColor;
    }

    public void setDiscrollveAlpha(boolean discrollveAlpha) {
        mDiscrollveAlpha = discrollveAlpha;
    }

    public void setDiscrollveScaleX(boolean discrollveScaleX) {
        mDiscrollveScaleX = discrollveScaleX;
    }

    public void setDiscrollveScaleY(boolean discrollveScaleY) {
        mDiscrollveScaleY = discrollveScaleY;
    }

    @Override
    public void onDiscrollve(float ratio) {
        if(ratio >= mDiscrollveThreshold) {
            ratio = withThreshold(ratio);
            float ratioInverse = 1 - ratio;

            if(mDiscrollveAlpha) {
                setAlpha(ratio);
            }
            if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
                setTranslationY(mHeight * ratioInverse);
            }
            if(isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)) {
                setTranslationY(-mHeight * ratioInverse);
            }
            if(isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT)) {
                setTranslationX(-mWidth * ratioInverse);
            }
            if(isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)) {
                setTranslationX(mWidth * ratioInverse);
            }
            if(mDiscrollveScaleX) {
                setScaleX(ratio);
            }
            if(mDiscrollveScaleY) {
                setScaleY(ratio);
            }
            if(mDiscrollveFromBgColor != -1 && mDiscrollveToBgColor != -1) {
                setBackgroundColor((Integer) sArgbEvaluator.evaluate(ratio, mDiscrollveFromBgColor, mDiscrollveToBgColor));
            }
        }
    }

    private float withThreshold(float ratio) {
        return (ratio - mDiscrollveThreshold) / (1.0f - mDiscrollveThreshold);
    }

    @Override
    public void onResetDiscrollve() {
        if(mDiscrollveAlpha) {
            setAlpha(0.0f);
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight);
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight);
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth);
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth);
        }
        if(mDiscrollveScaleX) {
            setScaleX(0.0f);
        }
        if(mDiscrollveScaleY) {
            setScaleY(0.0f);
        }
        if(mDiscrollveFromBgColor != -1 && mDiscrollveToBgColor != -1) {
            setBackgroundColor(mDiscrollveFromBgColor);
        }
    }

    private boolean isDiscrollveTranslationFrom(int translationMask) {
        if(mDiscrollveTranslation == -1) {
            return false;
        }
        return (mDiscrollveTranslation & translationMask) == translationMask;
    }
}
