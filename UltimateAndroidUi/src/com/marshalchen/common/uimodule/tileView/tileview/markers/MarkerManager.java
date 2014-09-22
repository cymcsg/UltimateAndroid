package com.marshalchen.common.uimodule.tileView.tileview.markers;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import com.marshalchen.common.uimodule.tileView.layouts.TranslationLayout;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/*
 * TODO: need to consolidate positioning logic - works as is, but does too many unnecessary and possibly messy calculations
 * should work with adding at runtime, in response to user event, sliding, etc. 
 */


public class MarkerManager extends TranslationLayout implements DetailLevelEventListener {

	private DetailManager detailManager;
	private HashMap<View, Rect> markerMap = new HashMap<View, Rect>();
	private ArrayList<MarkerEventListener> listeners = new ArrayList<MarkerEventListener>();
	
	public MarkerManager( Context context, DetailManager zm ) {
		super( context );
		detailManager = zm;
		detailManager.addDetailLevelEventListener( this );
	}	
	
	public View addMarker( View v, int x, int y ){
		LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, x, y );
		return addMarker( v, lp );
	}

	public View addMarker( View v, int x, int y, float aX, float aY ) {
		LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, x, y, aX, aY );
		return addMarker( v, lp );
	}
	
	public View addMarker( View v, LayoutParams params ) {
		addView( v, params );
		markerMap.put( v, new Rect() );
		requestLayout();
		return v;
	}
	
	public void removeMarker( View v ) {
		removeView( v );
		markerMap.remove( v );
	}
	
	public void addMarkerEventListener( MarkerEventListener listener ) {
		listeners.add( listener );
	}
	
	public void removeMarkerEventListener( MarkerEventListener listener ) {
		listeners.remove( listener );
	}
	
	private View getViewFromTap( int x, int y ) {
		Iterator<Entry<View, Rect>> iterator = markerMap.entrySet().iterator();
		while (iterator.hasNext()) {
	        Entry<View, Rect> pairs = iterator.next();
	        Rect rect = (Rect) pairs.getValue();
	        if(rect.contains( x, y )){
	        	View view = (View) pairs.getKey();
	        	return view;
	        }
		}
		return null;
	}
	
	public void processHit ( Point point ) {
		// fast-fail if no listeners
		if( listeners.isEmpty() ){
			return;
		}
		View view = getViewFromTap( point.x, point.y );
		if( view != null ) {
			for( MarkerEventListener listener : listeners ) {
				listener.onMarkerTap( view, point.x, point.y );
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout( changed, l, t, r, b );
	    for (int i = getChildCount() - 1; i >= 0; i--) {
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
	            // get and set the rect for the child
	            Rect rect = markerMap.get( child );
	            if( rect != null ) {
	            	rect.set( x, y, x + w, y + h );
	            }	            
	        }
	    }
	}
	
	@Override
	public void onDetailLevelChanged() {

	}

	@Override
	public void onDetailScaleChanged( double scale ) {
		setScale( scale );
	}

}
