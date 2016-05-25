package com.marshalchen.common.uimodule.tileView.tileview.hotspots;

import android.graphics.Rect;
import android.graphics.Region;


public class HotSpot extends Region {
	
	private Object tag;
	private HotSpotEventListener listener;
	
	public HotSpot() {
		super();
	}

	public HotSpot( int left, int top, int right, int bottom ) {
		super( left, top, right, bottom );
	}

	public HotSpot( Rect r ) {
		super( r );
	}

	public HotSpot( Region region ) {
		super( region );
	}
	
	public Object getTag(){
		return tag;
	}
	
	public void setTag( Object o ) {
		tag = o;
	}
	
	public void setHotSpotEventListener( HotSpotEventListener l ) {
		listener = l;
	}
	
	public HotSpotEventListener getHotSpotEventListener() { 
		return listener;
	}
	
}
