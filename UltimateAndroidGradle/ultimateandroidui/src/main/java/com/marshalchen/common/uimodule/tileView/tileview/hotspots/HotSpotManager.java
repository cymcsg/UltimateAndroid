package com.marshalchen.common.uimodule.tileView.tileview.hotspots;

import android.graphics.Point;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailManager;

import java.util.ArrayList;

public class HotSpotManager implements DetailLevelEventListener {

	private double scale = 1;
	
	private ArrayList<HotSpotEventListener> listeners = new ArrayList<HotSpotEventListener>();
	private ArrayList<HotSpot> spots = new ArrayList<HotSpot>();
	
	public HotSpotManager( DetailManager detailManager ) {
		detailManager.addDetailLevelEventListener( this );
	}
	
	public void addHotSpot( HotSpot hotSpot ){
		spots.add( hotSpot );
	}
	
	public void removeHotSpot( HotSpot hotSpot ){
		spots.remove( hotSpot );
	}
	
	public void addHotSpotEventListener( HotSpotEventListener listener ) {
		listeners.add( listener );
	}
	
	public void removeHotSpotEventListener( HotSpotEventListener listener ) {
		listeners.remove( listener );
	}
	
	public void clear(){
		spots.clear();
	}
	
	// work from end of list - match the last one added (equivalant to z-index)
	private HotSpot getMatch( Point point ){
		Point scaledPoint = new Point();
		scaledPoint.x = (int) ( point.x / scale );
		scaledPoint.y = (int) ( point.y / scale );
		for(int i = spots.size() - 1; i >= 0; i--){
			HotSpot hotSpot = spots.get( i );
			if(hotSpot.contains( scaledPoint.x, scaledPoint.y )){
				return hotSpot;
			}
		}
		return null;
	}
	
	public void processHit( Point point ){
		// fast-fail if no listeners
		if(listeners.isEmpty()){
			return;
		}
		// is there a match?
		HotSpot hotSpot = getMatch( point );
		if( hotSpot != null){
			HotSpotEventListener spotListener = hotSpot.getHotSpotEventListener();
			if( spotListener != null ) {
				spotListener.onHotSpotTap( hotSpot, point.x, point.y );
			}
			for( HotSpotEventListener listener : listeners ) {
				listener.onHotSpotTap( hotSpot, point.x, point.y );
			}
		}
	}

	@Override
	public void onDetailLevelChanged() {
		
	}

	@Override
	public void onDetailScaleChanged( double s ) {
		scale = s;
	}
}
