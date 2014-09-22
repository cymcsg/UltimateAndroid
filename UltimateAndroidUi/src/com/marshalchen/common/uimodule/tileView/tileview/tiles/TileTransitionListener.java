package com.marshalchen.common.uimodule.tileView.tileview.tiles;

import android.view.animation.Animation;

import java.lang.ref.WeakReference;


public class TileTransitionListener implements Animation.AnimationListener {

	private WeakReference<TileManager> reference;
	
	public TileTransitionListener( TileManager tm ) {
		reference = new WeakReference<TileManager>( tm );
	}
	
	@Override
	public void onAnimationStart( Animation animation ) {
		
	}
	
	@Override
	public void onAnimationRepeat( Animation animation ) {
		
	}
	
	@Override
	public void onAnimationEnd( Animation animation ) {
		TileManager tileManager = reference.get();
		if( tileManager != null) {
			// why both?  no clue...  omit either one and abuse it,  you'll get incomplete draws...
			tileManager.invalidate();
			tileManager.postInvalidate();		
		}		
	}
	
}
