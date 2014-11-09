package com.marshalchen.common.uimodule.tileView.animation.easing;

public abstract class Strong extends EasingEquation {
	public static final Strong EaseNone = new Strong(){
		
	};
	public static final Strong EaseIn = new Strong(){
		@Override
		public double compute(double t, double b, double c, double d){
			return c * Math.pow(t / d, 5) + b;
		}
	};
	public static final Strong EaseOut = new Strong(){
		@Override
		public double compute(double t, double b, double c, double d){
			return c * ( 1 - Math.pow( 1 - ( t / d), 5) ) + b;
		}
	};
}

