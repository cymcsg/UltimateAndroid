package com.marshalchen.common.uimodule.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.marshalchen.common.uimodule.shapeimageview.shader.ShaderHelper;
import com.marshalchen.common.uimodule.R;
import com.marshalchen.common.uimodule.shapeimageview.shader.SvgShader;


public class HeartImageView extends ShaderImageView {

    public HeartImageView(Context context) {
        super(context);
    }

    public HeartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.shape_imageview_imgview_heart, SvgShader.BORDER_TYPE_FILL);
    }
}
