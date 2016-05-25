package com.marshalchen.common.uimodule.kugouLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class UnClickableFrameLayout extends BuildLayerFrameLayout{
	public UnClickableFrameLayout(Context context){
		super(context);
	}
	public UnClickableFrameLayout(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	public UnClickableFrameLayout(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
}
