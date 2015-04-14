package com.marshalchen.common.uimodule.parallaxscroll;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class ParallaxedView {
	static public boolean isAPI11 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	protected WeakReference<View> view;
	protected int lastOffset;

	abstract protected void translatePreICS(View view, float offset);
	
	public ParallaxedView(View view) {
		this.lastOffset = 0;
		this.view = new WeakReference<View>(view);
	}

	public boolean is(View v) {
		return (v != null && view != null && view.get() != null && view.get().equals(v));
	}

	@SuppressLint("NewApi")
	public void setOffset(float offset) {
		View view = this.view.get();
		if (view != null)
			if (isAPI11) {
				view.setTranslationY(offset);
			} else {
				translatePreICS(view, offset);
			}
	}
	
	public void setView(View view) {
		this.view = new WeakReference<View>(view);
	}
}
