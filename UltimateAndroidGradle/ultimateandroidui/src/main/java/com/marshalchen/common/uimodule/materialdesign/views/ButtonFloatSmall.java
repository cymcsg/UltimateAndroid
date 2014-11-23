package com.marshalchen.common.uimodule.materialdesign.views;

import com.marshalchen.common.uimodule.R;
import com.marshalchen.common.uimodule.materialdesign.utils.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ButtonFloatSmall extends ButtonFloat {
	
	public ButtonFloatSmall(Context context, AttributeSet attrs) {
		super(context, attrs);
		sizeRadius = 20;
		sizeIcon = 20;
		setDefaultProperties();
		LayoutParams params = new LayoutParams(Utils.dpToPx(sizeIcon, getResources()),Utils.dpToPx(sizeIcon, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(params);
	}
	
	protected void setDefaultProperties(){
		rippleSpeed = Utils.dpToPx(2, getResources());
		rippleSize = 10;		
		// Min size
		setMinimumHeight(Utils.dpToPx(sizeRadius*2, getResources()));
		setMinimumWidth(Utils.dpToPx(sizeRadius*2, getResources()));
		// Background shape
		setBackgroundResource(R.drawable.material_design_background_button_float);
		setBackgroundColor(backgroundColor);
	}

}
