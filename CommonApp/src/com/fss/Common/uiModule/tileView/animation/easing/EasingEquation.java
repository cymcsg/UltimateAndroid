package com.fss.common.uiModule.tileView.animation.easing;

public abstract class EasingEquation {
	public double compute(double t, double b, double c, double d){
		return c * ( t / d ) + b;
	}
}
