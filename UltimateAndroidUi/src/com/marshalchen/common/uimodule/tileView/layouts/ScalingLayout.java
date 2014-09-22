package com.marshalchen.common.uimodule.tileView.layouts;

import android.content.Context;
import android.graphics.Canvas;

public class ScalingLayout extends FixedLayout {

	private double scale = 1;

	public ScalingLayout( Context context ) {
		super( context );
		setWillNotDraw( false );
	}

	public void setScale( double factor ) {
		scale = factor;
		postInvalidate();
	}

	public double getScale() {
		return scale;
	}

	@Override
	public void onDraw( Canvas canvas ) {
		canvas.scale( (float) scale, (float) scale );
		super.onDraw( canvas );
	}

}