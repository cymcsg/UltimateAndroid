package com.marshalchen.common.uimodule.tileView.layouts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class StaticLayout extends ViewGroup {
	
	public StaticLayout(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int w = 0;
		int h = 0;		

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				w = Math.max(w, child.getMeasuredWidth());
				h = Math.max(h, child.getMeasuredHeight());
			}
		}

		h = Math.max(h, getSuggestedMinimumHeight());
		w = Math.max(w, getSuggestedMinimumWidth());
		
		w = resolveSize(w, widthMeasureSpec);
		h = resolveSize(h, heightMeasureSpec);
		
		setMeasuredDimension(w, h);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
			}
		}
	}
	
}
