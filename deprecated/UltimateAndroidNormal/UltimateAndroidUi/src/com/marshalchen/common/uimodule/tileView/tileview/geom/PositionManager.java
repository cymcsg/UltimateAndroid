package com.marshalchen.common.uimodule.tileView.tileview.geom;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class PositionManager {
	
	private double left;
	private double top;
	private double right;
	private double bottom;
	
	private double offsetWidth;
	private double offsetHeight;
	
	private int pixelWidth;
	private int pixelHeight;
	
	private boolean definedBounds;
	
	private void calculateOffsets() {
		offsetWidth = right - left;
		offsetHeight = bottom - top;
	}
	
	public void setSize( int w, int h ) {
		pixelWidth = w;
		pixelHeight = h;
		if(!definedBounds){
			right = pixelWidth;
			bottom = pixelHeight;
			calculateOffsets();
		}
	}
	
	public void setBounds( double l, double t, double r, double b ) {
		definedBounds = true;
		left = l;
		top = t;
		right = r;
		bottom = b;
		calculateOffsets();
	}
	
	public void unsetBounds() {
		definedBounds = false;
		left = 0;
		top = 0;
		right = pixelWidth;
		bottom = pixelHeight;
		calculateOffsets();
	}
	
	public Point translate( double x, double y, double scale ) {
		Point point = translate( x, y );
		point.x = (int) ( (0.5 + point.x ) * scale );
		point.y = (int) ( (0.5 + point.y ) * scale );
		return point;
	}
	
	// get back the point coordinates *not* scaled (for markers, callouts, etc, that handle scale internally)
	public Point translate( double x, double y ) {		
		
		Point point = new Point();
		
		double factorX = ( x - left ) / offsetWidth;
		point.x = (int) ( 0.5 + ( pixelWidth * factorX ) );
		
		double factorY = ( y - top ) / offsetHeight;
		point.y = (int) ( 0.5 + ( pixelHeight * factorY ) );

		return point;
		
	}
	
	public ArrayList<Point> translate( List<double[]> positions ){
		ArrayList<Point> points = new ArrayList<Point>();
		for( double[] position : positions ) {
			Point point = translate( position[0], position[1] );
			points.add( point );
		}
		return points;
	}
	
	public boolean contains( double x, double y ) {
		double minX = Math.min( left, right );
		double maxX = Math.max( left, right );
		double minY = Math.min( top, bottom );
		double maxY = Math.max( top, bottom );
		return y >= minY
			&& y <= maxY
			&& x >= minX
			&& x <= maxX;
	}
	
}

/*

	public double getLeft() {
		return left;
	}
	
	public void setLeft( double left ) {
		this.left = left;
	}

	public double getTop() {
		return top;
	}
	
	public void setTop( double top ) {
		this.top = top;
	}
	
	public double getRight() {
		return right;
	}

	public void setRight( double right ) {
		this.right = right;
	}
	
	public double getBottom() {
		return bottom;
	}
	
	public void setBottom( double bottom ) {
		this.bottom = bottom;
	}

	public int getWidth() {
		return pixelWidth;
	}

	public void setWidth( int width ) {
		pixelWidth = width;
	}

	public int getHeight() {
		return pixelHeight;
	}

	public void setHeight( int height ) {
		pixelHeight = height;
	}

 */

/*
package com.qozix.geom;

import java.util.ArrayList;

import android.graphics.Point;

public class Geolocator {

	private Coordinate topLeft = new Coordinate();
	private Coordinate bottomRight = new Coordinate();

	private int pixelWidth = 0;
	private int pixelHeight = 0;

	public void setCoordinates( Coordinate tl, Coordinate br ) {
		topLeft = tl;
		bottomRight = br;
	}
	
	public void setCoordinates( double left, double top, double right, double bottom ){
		topLeft = new Coordinate( left, top );
		bottomRight = new Coordinate( right, bottom );
	}

	public void setSize( int w, int h ) {
		pixelWidth = w;
		pixelHeight = h;
	}

	public Point translate( Coordinate c ) {

		Point p = new Point();

		double longitudanalDelta = bottomRight.longitude - topLeft.longitude;
		double longitudanalDifference = c.longitude - topLeft.longitude;
		double longitudanalFactor = longitudanalDifference / longitudanalDelta;
		p.x = (int) ( longitudanalFactor * pixelWidth );

		double latitudanalDelta = bottomRight.latitude - topLeft.latitude;
		double latitudanalDifference = c.latitude - topLeft.latitude;
		double latitudanalFactor = latitudanalDifference / latitudanalDelta;
		p.y = (int) ( latitudanalFactor * pixelHeight );
		
		return p;

	}

	public Coordinate translate( Point p ) {

		Coordinate c = new Coordinate();

		double relativeX = p.x / (double) pixelWidth;
		double deltaX = bottomRight.longitude - topLeft.longitude;
		c.longitude = topLeft.longitude + deltaX * relativeX;

		double relativeY = p.y / (double) pixelHeight;
		double deltaY = bottomRight.latitude - topLeft.latitude;
		c.latitude = topLeft.latitude + deltaY * relativeY;

		return c;
	}
	
	public int[] coordinatesToPixels( double lat, double lng ) {
		
		int[] positions = new int[2];

		double longitudanalDelta = bottomRight.longitude - topLeft.longitude;
		double longitudanalDifference = lng - topLeft.longitude;
		double longitudanalFactor = longitudanalDifference / longitudanalDelta;
		positions[0] = (int) ( longitudanalFactor * pixelWidth );
		
		double latitudanalDelta = bottomRight.latitude - topLeft.latitude;
		double latitudanalDifference = lat - topLeft.latitude;
		double latitudanalFactor = latitudanalDifference / latitudanalDelta;
		positions[1] = (int) ( latitudanalFactor * pixelHeight );
		
		return positions;
		
	}
	
	public ArrayList<Point> getPointsFromCoordinates( ArrayList<Coordinate> coordinates ) {
		ArrayList<Point> points = new ArrayList<Point>();
		for ( Coordinate coordinate : coordinates ) {
			Point point = translate( coordinate );
			points.add( point );
		}
		return points;
	}

	public ArrayList<Coordinate> getCoordinatesFromPoints( ArrayList<Point> points ) {
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		for ( Point point : points ) {
			Coordinate coordinate = translate( point );
			coordinates.add( coordinate );
		}
		return coordinates;
	}

	public boolean contains( Coordinate coordinate ) {
		double minLat = Math.min( topLeft.latitude, bottomRight.latitude );
		double maxLat = Math.max( topLeft.latitude, bottomRight.latitude );
		double minLng = Math.min( topLeft.longitude, bottomRight.longitude );
		double maxLng = Math.max( topLeft.longitude, bottomRight.longitude );
		return coordinate.latitude >= minLat
			&& coordinate.latitude <= maxLat
			&& coordinate.longitude >= minLng
			&& coordinate.longitude <= maxLng;
	}
}
*/
