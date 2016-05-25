package com.marshalchen.common.uimodule.tileView.animation;

import android.os.Handler;
import android.os.Message;
import com.marshalchen.common.uimodule.tileView.animation.easing.EasingEquation;
import com.marshalchen.common.uimodule.tileView.animation.easing.Linear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Animator {

	private double ellapsed;
	private double startTime;
	private double duration = 500;

	private HashMap<String, Double> properties = new HashMap<String, Double>();
	private HashMap<String, Double> values = new HashMap<String, Double>();
	
	private ArrayList<AnimationListener> listeners = new ArrayList<AnimationListener>();
	private EasingEquation ease = Linear.EaseNone;

	public void setAnimationEase(EasingEquation e) {
		if(e == null || !( e instanceof EasingEquation)){
			e = Linear.EaseNone;
		}
		ease = e;
	}

	public void addAnimationListener(AnimationListener l) {
		listeners.add(l);
	}
	
	public void removeAnimationListener(AnimationListener l){
		listeners.remove(l);
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double time) {
		duration = time;
	}

	public HashMap<String, Double> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, Double> p) {
		properties = p;
	}
	
	public void addProperties(HashMap<String, Double> p){
		properties.putAll(p);
	}
	
	public void addProperty(String s, Double d){
		properties.put(s, d);
	}

	public void start() {
		values.putAll(properties);
		ellapsed = 0;
		startTime = System.currentTimeMillis();
		handler.sendEmptyMessage(0);
		for(AnimationListener l : listeners){
			l.onAnimationStart();
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(final Message message) {
			ellapsed = System.currentTimeMillis() - startTime;
			for(Map.Entry<String, Double> e : values.entrySet()) {
				String key = e.getKey();
				Double value = e.getValue();
				Double originalValue = properties.get(key);
				Double computedValue = ease.compute(ellapsed, originalValue, originalValue - value, duration);
				e.setValue(computedValue);
			}
			for(AnimationListener l : listeners){
				l.onAnimationProgress(values);
			}
			if (ellapsed >= duration) {
				if (hasMessages(0)) {
					removeMessages(0);
				}
				for(AnimationListener l : listeners){
					l.onAnimationComplete();
				}
			} else {
				sendEmptyMessage(0);
			}

		}
	};

}
