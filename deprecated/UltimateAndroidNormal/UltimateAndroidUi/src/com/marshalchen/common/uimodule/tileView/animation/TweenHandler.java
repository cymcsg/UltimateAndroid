package com.marshalchen.common.uimodule.tileView.animation;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

public class TweenHandler extends Handler {

	private static final int RENDER = 1;
	
	private static TweenHandler instance = null;
	public static TweenHandler getInstance(){
		if(instance == null){
			instance = new TweenHandler();
		}
		return instance;
	}
	
	private ArrayList<Tween> tweens = new ArrayList<Tween>();
	
	public void addTween( Tween tween ) {
		tweens.add( tween );
		sendEmptyMessage( RENDER );
	}
	
	public void removeTween( Tween tween ) {
		tweens.remove( tween );
		if( tweens.size() == 0 ) {
			if( hasMessages( RENDER ) ) {
				removeMessages( RENDER );
			}
		}
	}
	
	@Override
	public void handleMessage( final Message message ) {
		boolean tweensAreRunning = false;
		ArrayList<Tween> condemned = new ArrayList<Tween>();
		for( Tween tween : tweens ) {
			boolean tweenIsComplete = runTween( tween );
			if( !tweensAreRunning ) {
				if( !tweenIsComplete ) {
					tweensAreRunning = true;
				}
			}
			if( tweenIsComplete ){
				condemned.add( tween );
			}
		}
		if( tweensAreRunning ) {
			sendEmptyMessage( RENDER );
		} else {
			if( hasMessages( RENDER ) ) {
				removeMessages( RENDER );
			}
		}
		tweens.removeAll( condemned );
	}
	
	private boolean runTween( Tween tween ) {
		double ellapsed = System.currentTimeMillis() - tween.getStartTime();
		ellapsed = Math.min( ellapsed, tween.getDuration() );
		tween.setEllapsed( ellapsed );
		double progress = tween.getProgress();
		double eased = tween.getEasedProgress();
		for ( TweenListener l : tween.getTweenListeners() ) {
			l.onTweenProgress( progress, eased );
		}
		boolean complete = ellapsed >= tween.getDuration();
		if ( complete ) {			
			for ( TweenListener l : tween.getTweenListeners() ) {
				l.onTweenComplete();
			}
		}		
		return complete;		
	}
}
