package com.marshalchen.common.uimodule.tileView.tileview.paths;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawablePath {

	/**
	 * The path that this drawable will follow.
	 */
	public Path path;

	/**
	 * The paint to be used for this path.
	 */
	public Paint paint;

	/**
	 * Draw the supplied path onto the supplied canvas.
	 * 
	 * @param canvas
	 * @param drawingPath
	 */
	@SuppressLint("NewApi")
	public void draw( Canvas canvas, Path drawingPath ) {
		canvas.drawPath( drawingPath, paint );
	}

}
