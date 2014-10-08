package com.marshalchen.common.uimodule.slider.Transformers;

import android.view.View;

import com.marshalchen.common.uimodule.nineoldandroids.view.ViewHelper;

public class StackTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		ViewHelper.setTranslationX(view,position < 0 ? 0f : -view.getWidth() * position);
	}

}
