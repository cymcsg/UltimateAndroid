package com.marshalchen.common.uimodule.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.marshalchen.common.uimodule.shapeimageview.shader.BubbleShader;
import com.marshalchen.common.uimodule.shapeimageview.shader.ShaderHelper;

public class BubbleImageView extends ShaderImageView {

    public BubbleImageView(Context context) {
        super(context);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new BubbleShader();
    }
}
