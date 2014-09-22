package com.marshalchen.common.uimodule.tileView.layouts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class FixedLayout extends ViewGroup {
	
	public FixedLayout(Context context) {
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
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				int right = lp.x + child.getMeasuredWidth();
				int bottom = lp.y + child.getMeasuredHeight();
				w = Math.max(w, right);
				h = Math.max(h, bottom);
			}
		}

		h = Math.max(h, getSuggestedMinimumHeight());
		w = Math.max(w, getSuggestedMinimumWidth());
		
		w = resolveSize(w, widthMeasureSpec);
		h = resolveSize(h, heightMeasureSpec);
		
		setMeasuredDimension(w, h);
		
	}
	
	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
			}
		}
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}

	public static class LayoutParams extends ViewGroup.LayoutParams {

		public int x = 0;
		public int y = 0;

		public LayoutParams(int width, int height, int left, int top) {
			super(width, height);
			x = left;
			y = top;
		}
		
		public LayoutParams(int width, int height){
			super(width, height);
		}

		public LayoutParams(ViewGroup.LayoutParams source) {
			super(source);
		}
	}
}
