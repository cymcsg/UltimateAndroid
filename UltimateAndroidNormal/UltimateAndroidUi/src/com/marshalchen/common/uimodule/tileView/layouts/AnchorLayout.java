package com.marshalchen.common.uimodule.tileView.layouts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * The AnchorLayout positions it's children using absolute pixel values,
 * offset by anchors.  An anchor exists on each axis (x and y), and is
 * determined by multiplying the relevant dimension of the child (width for x,
 * height for y) by a float.  This float can be supplied to each child via
 * LayoutParams, or to the AnchorLayout ViewGroup directly.  If a child's
 * LayoutParams are not specified (null), then it will be positioned using
 * the Layout's anchor values.
 * 
 * For example, passing an -0.5f anchorX and -1.0f anchorY will position the
 * view entirely above, and centered horizontally, relative to the pixel
 * coordinates supplied.
 * 
 * This is useful for positioning elements as indicators for another view,
 * or graphical feature.  Tooltips, map markers, instructional elements, etc
 * could benefit from anchored layouts.
 */

public class AnchorLayout extends ViewGroup {

	protected float anchorX = 0f;
	protected float anchorY = 0f;

	public AnchorLayout( Context context ) {
		super( context );
	}

	/**
	 * Sets the anchor values used by this ViewGroup if it's children do not
	 * have anchor values supplied directly (via LayoutParams)
	 * @param aX (float) x-axis anchor value (offset computed by multiplying this value by the child's width
	 * @param aY (float) y-axis anchor value (offset computed by multiplying this value by the child's height
	 */
	public void setAnchors( float aX, float aY ) {
		anchorX = aX;
		anchorY = aY;
		requestLayout();
	}

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

		measureChildren( widthMeasureSpec, heightMeasureSpec );

		int width = 0;
		int height = 0;

		int count = getChildCount();
		for ( int i = 0; i < count; i++ ) {
			View child = getChildAt( i );
			if ( child.getVisibility() != GONE ) {
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				// get anchor offsets
				float aX = ( lp.anchorX == null ) ? anchorX : lp.anchorX;
				float aY = ( lp.anchorY == null ) ? anchorY : lp.anchorY;
				// offset dimensions by anchor values
				int computedWidth = (int) ( child.getMeasuredWidth() * aX );
				int computedHeight = (int) ( child.getMeasuredHeight() * aY );
				// add computed dimensions to actual position
				int right = lp.x + computedWidth;
				int bottom = lp.y + computedHeight;
				// if it's larger, use that
				width = Math.max( width, right );
				height = Math.max( height, bottom );
			}
		}

		height = Math.max( height, getSuggestedMinimumHeight() );
		width = Math.max( width, getSuggestedMinimumWidth() );
		width = resolveSize( width, widthMeasureSpec );
		height = resolveSize( height, heightMeasureSpec );
		setMeasuredDimension( width, height );

	}

	@Override
	protected void onLayout( boolean changed, int l, int t, int r, int b ) {
		int count = getChildCount();
		for ( int i = 0; i < count; i++ ) {
			View child = getChildAt( i );
			if ( child.getVisibility() != GONE ) {
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				// get sizes
				int w = child.getMeasuredWidth();
				int h = child.getMeasuredHeight();
				// user child's layout params anchor position if set, otherwise
				// default to anchor position of layout
				float aX = ( lp.anchorX == null ) ? anchorX : lp.anchorX;
				float aY = ( lp.anchorY == null ) ? anchorY : lp.anchorY;
				// apply anchor offset to position
				int x = lp.x + (int) ( w * aX );
				int y = lp.y + (int) ( h * aY );
				// set it
				child.layout( x, y, x + w, y + h );
			}
		}
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0 );
	}

	@Override
	protected boolean checkLayoutParams( ViewGroup.LayoutParams p ) {
		return p instanceof LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams( ViewGroup.LayoutParams p ) {
		return new LayoutParams( p );
	}

	/**
	 * Per-child layout information associated with AnchorLayout.
	 */
	public static class LayoutParams extends ViewGroup.LayoutParams {

		/**
		 * The absolute left position of the child in pixels
		 */
		public int x = 0;
		/**
		 * The absolute right position of the child in pixels
		 */
		public int y = 0;
		/**
		 * Float value to determine the child's horizontal offset. This float is multiplied by the child's width.  If null, the containing AnchorLayout's anchor values will be used.
		 */
		public Float anchorX = null;
		/**
		 * Float value to determine the child's vertical offset. This float is multiplied by the child's height.  If null, the containing AnchorLayout's anchor values will be used.
		 */
		public Float anchorY = null;

		/**
		 * Copy constructor
		 * @param source (LayoutParams) LayoutParams instance to copy properties from
		 */
		public LayoutParams( ViewGroup.LayoutParams source ) {
			super( source );
		}

		/**
		 * Creates a new set of layout parameters with the specified values.
		 * @param width (int) Information about how wide the view wants to be.  This should generally be WRAP_CONTENT or a fixed value.
		 * @param height (int) Information about how tall the view wants to be.  This should generally be WRAP_CONTENT or a fixed value.
		 */
		public LayoutParams( int width, int height ) {
			super( width, height );
		}

		/**
		 * Creates a new set of layout parameters with the specified values.
		 * @param width (int) Information about how wide the view wants to be.  This should generally be WRAP_CONTENT or a fixed value.
		 * @param height (int) Information about how tall the view wants to be.  This should generally be WRAP_CONTENT or a fixed value.
		 * @param left (int) Sets the absolute x value of the view's position in pixels
		 * @param top (int) Sets the absolute y value of the view's position in pixels
		 */
		public LayoutParams( int width, int height, int left, int top ) {
			super( width, height );
			x = left;
			y = top;
		}
		
		/**
		 * Creates a new set of layout parameters with the specified values.
		 * @param width (int) Information about how wide the view wants to be.  This should generally be WRAP_CONTENT or a fixed value.
		 * @param height (int) Information about how tall the view wants to be.  This should generally be WRAP_CONTENT or a fixed value.
		 * @param left (int) Sets the absolute x value of the view's position in pixels
		 * @param top (int) Sets the absolute y value of the view's position in pixels
		 * @param aX (float) Sets the relative horizontal offset of the view (multiplied by the view's width)
		 * @param aY (float) Sets the relative vertical offset of the view (multiplied by the view's height)
		 */
		public LayoutParams( int width, int height, int left, int top, float aX, float aY ) {
			super( width, height );
			x = left;
			y = top;
			anchorX = aX;
			anchorY = aY;
		}

	}
}
