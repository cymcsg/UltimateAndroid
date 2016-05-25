package com.marshalchen.common.uimodule.tileView.layouts;

import android.content.Context;
import android.view.View;

/**
 * The TranslationLayout extends {@link AnchorLayout}, but additionally supports
 * a scale value.  The views of this layout will not be scaled along width or height,
 * but their positions will be multiplied by the TranslationLayout's scale value.
 * This allows the contained views to maintain their visual appearance and distance
 * relative to each other, while the total area of the group can be managed by the
 * scale value.
 * 
 * This is useful for positioning groups of markers, tooltips, or indicator views
 * without scaling, while the reference element(s) are scaled.
 */

public class TranslationLayout extends AnchorLayout {
	
	protected double scale = 1;
		
	public TranslationLayout(Context context){
		super(context);
	}
	
	/**
	 * Sets the scale (0-1) of the ZoomPanLayout
	 * @param scale (double) The new value of the ZoomPanLayout scale
	 */
	public void setScale(double d){
		scale = d;
		requestLayout();
	}
	
	/**
	 * Retrieves the current scale of the ZoomPanLayout
	 * @return (double) the current scale of the ZoomPanLayout
	 */
	public double getScale() {
		return scale;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int width = 0;
		int height = 0;		

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				TranslationLayout.LayoutParams lp = (TranslationLayout.LayoutParams) child.getLayoutParams();
				// get anchor offsets
				float aX = (lp.anchorX == null) ? anchorX : lp.anchorX;
	            float aY = (lp.anchorY == null) ? anchorY : lp.anchorY;
	            // offset dimensions by anchor values
	            int computedWidth = (int) (child.getMeasuredWidth() * aX);
	            int computedHeight = (int) (child.getMeasuredHeight() * aY);
	            // get offset position
	            int scaledX = (int) (0.5 + (lp.x * scale));
	            int scaledY = (int) (0.5 + (lp.y * scale));
	            // add computed dimensions to actual position
	            int right = scaledX + computedWidth;
				int bottom = scaledY + computedHeight;
				// if it's larger, use that
				width = Math.max(width, right);
				height = Math.max(height, bottom);
			}
		}

		height = Math.max(height, getSuggestedMinimumHeight());
		width = Math.max(width, getSuggestedMinimumWidth());
		width = resolveSize(width, widthMeasureSpec);
		height = resolveSize(height, heightMeasureSpec);
		setMeasuredDimension(width, height);
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    int count = getChildCount();
	    for (int i = 0; i < count; i++) {
	        View child = getChildAt(i);
	        if (child.getVisibility() != GONE) {
	            LayoutParams lp = (LayoutParams) child.getLayoutParams();
	            // get sizes
	            int w = child.getMeasuredWidth();
	            int h = child.getMeasuredHeight();
	            // get offset position
	            int scaledX = (int) (0.5 + (lp.x * scale));
	            int scaledY = (int) (0.5 + (lp.y * scale));
	            // user child's layout params anchor position if set, otherwise default to anchor position of layout
	            float aX = (lp.anchorX == null) ? anchorX : lp.anchorX;
	            float aY = (lp.anchorY == null) ? anchorY : lp.anchorY;
	            // apply anchor offset to position
	            int x = scaledX + (int) (w * aX);
	            int y = scaledY + (int) (h * aY);
	            // set it
	            child.layout(x, y, x + w, y + h);
	        }
	    }
	}

}
