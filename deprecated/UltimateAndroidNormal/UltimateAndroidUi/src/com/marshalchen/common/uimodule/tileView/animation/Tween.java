package com.marshalchen.common.uimodule.tileView.animation;

import com.marshalchen.common.uimodule.tileView.animation.easing.EasingEquation;
import com.marshalchen.common.uimodule.tileView.animation.easing.Linear;

import java.util.ArrayList;


// TODO: the singleton Handler might leak...
public class Tween {

	private double ellapsed;
	private double startTime;
	private double duration = 500;

	private ArrayList<TweenListener> listeners = new ArrayList<TweenListener>();
	private EasingEquation ease = Linear.EaseNone;

	public double getEllapsed(){
		return ellapsed;
	}
	
	public void setEllapsed( double time ){
		ellapsed = time;
	}
	
	public double getProgress() {
		return ellapsed / duration;
	}

	public double getEasedProgress() {
		return ease.compute( ellapsed, 0, 1, duration );
	}

	public void setAnimationEase( EasingEquation e ) {
		if ( e == null ) {
			e = Linear.EaseNone;
		}
		ease = e;
	}

	public ArrayList<TweenListener> getTweenListeners() {
		return listeners;
	}
	
	public void addTweenListener( TweenListener l ) {
		listeners.add( l );
	}

	public void removeTweenListener( TweenListener l ) {
		listeners.remove( l );
	}

	public double getStartTime() {
		return startTime;
	}
	
	public double getDuration() {
		return duration;
	}

	public void setDuration( double time ) {
		duration = time;
	}

	public void start() {
		stop();
		ellapsed = 0;
		startTime = System.currentTimeMillis();
		for ( TweenListener l : listeners ) {
			l.onTweenStart();
		}
		TweenHandler handler = TweenHandler.getInstance();
		handler.addTween( this );
	}

	public void stop() {
		TweenHandler handler = TweenHandler.getInstance();
		handler.removeTween( this );
	}


}
