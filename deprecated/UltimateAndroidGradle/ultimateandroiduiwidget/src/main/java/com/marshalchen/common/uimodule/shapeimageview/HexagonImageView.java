package com.marshalchen.common.uimodule.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.marshalchen.common.uimodule.widgets.R;
import com.marshalchen.common.uimodule.shapeimageview.shader.ShaderHelper;
import com.marshalchen.common.uimodule.shapeimageview.shader.SvgShader;

public class HexagonImageView extends ShaderImageView {

    public HexagonImageView(Context context) {
        super(context);
    }

    public HexagonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.shape_imageview_imgview_hexagon);
    }
}
