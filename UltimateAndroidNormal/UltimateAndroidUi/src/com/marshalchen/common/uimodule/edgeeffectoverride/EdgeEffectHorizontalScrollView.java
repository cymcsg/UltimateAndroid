package com.marshalchen.common.uimodule.edgeeffectoverride;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import com.marshalchen.common.uimodule.R;

public class EdgeEffectHorizontalScrollView extends HorizontalScrollView {

	public EdgeEffectHorizontalScrollView(Context context) {
		this(context, null);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public EdgeEffectHorizontalScrollView(Context context, AttributeSet attrs) {
		super(new ContextWrapperEdgeEffect(context), attrs);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			init(context, attrs, android.R.attr.horizontalScrollViewStyle);
		} else {
			init(context, attrs, 0);
		}
	}

	public EdgeEffectHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(new ContextWrapperEdgeEffect(context), attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle){
		int color = context.getResources().getColor(R.color.default_edgeeffect_color);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EdgeEffectView, defStyle, 0);

		setEdgeEffectColor(color = a.getColor(R.styleable.EdgeEffectView_edgeeffect_color, color));

		a.recycle();
	}

	public void setEdgeEffectColor(int edgeEffectColor){
		((ContextWrapperEdgeEffect) getContext()).setEdgeEffectColor(edgeEffectColor);
	}
}
