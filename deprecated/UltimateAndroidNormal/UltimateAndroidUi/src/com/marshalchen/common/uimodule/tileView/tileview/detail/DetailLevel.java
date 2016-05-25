package com.marshalchen.common.uimodule.tileView.tileview.detail;



import android.graphics.Rect;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.Tile;

import java.util.LinkedList;


public class DetailLevel implements Comparable<DetailLevel> {

	private static final int DEFAULT_TILE_SIZE = 256;

	private double scale;
	
	private int tileWidth = DEFAULT_TILE_SIZE;
	private int tileHeight = DEFAULT_TILE_SIZE;

	private String pattern;
	private String downsample;

	private DetailManager detailManager;
	private Rect viewport = new Rect();

	public DetailLevel( DetailManager zm, float s, String p, String d, int tw, int th ) {
		detailManager = zm;
		scale = s;
		pattern = p;
		downsample = d;
		tileWidth = tw;
		tileHeight = th;
	}
	
	public DetailLevel( DetailManager zm, float s, String p, String d ) {
		this( zm, s, p, d, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE );
	}

	public LinkedList<Tile> getIntersections() {
		
		double relativeScale = getRelativeScale();
		
		int drawableWidth = (int) ( detailManager.getWidth() * getScale() * relativeScale );
		int drawableHeight = (int) ( detailManager.getHeight() * getScale() * relativeScale );
		double offsetWidth = ( tileWidth * relativeScale );
		double offsetHeight = ( tileHeight * relativeScale );
		
		LinkedList<Tile> intersections = new LinkedList<Tile>();
		
		viewport.set( detailManager.getComputedViewport() );
		
		// TODO test if mins are right
		viewport.top = Math.max( viewport.top, 0 );
		viewport.left = Math.max( viewport.left, 0 );
		viewport.right = Math.min( viewport.right, drawableWidth );
		viewport.bottom = Math.min( viewport.bottom, drawableHeight );
		
		
		int startingRow = (int) Math.floor( viewport.top / offsetHeight );
		int endingRow = (int) Math.ceil( viewport.bottom / offsetHeight );
		int startingColumn = (int) Math.floor( viewport.left / offsetWidth );
		int endingColumn = (int) Math.ceil( viewport.right / offsetWidth );
		
		DetailLevelPatternParser parser = detailManager.getDetailLevelPatternParser();
		
		for ( int iterationRow = startingRow; iterationRow < endingRow; iterationRow++ ) {
			for ( int iterationColumn = startingColumn; iterationColumn < endingColumn; iterationColumn++ ) {
				String fileName = parser.parse( pattern, iterationRow, iterationColumn );
				int left = iterationColumn * tileWidth;
				int top = iterationRow * tileHeight;
				Tile tile = new Tile( left, top, tileWidth, tileHeight, fileName );
				intersections.add( tile );
			}
		}
		
		
		return intersections;
		
	}

	public double getScale(){
		return scale;
	}
	
	public double getRelativeScale(){
		return detailManager.getScale() / scale;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public String getPattern() {
		return pattern;
	}

	public String getDownsample() {
		return downsample;
	}

	@Override
	public int compareTo( DetailLevel o ) {
		return (int) Math.signum( getScale() - o.getScale() );
	}

	@Override
	public boolean equals( Object o ) {
		if ( o instanceof DetailLevel ) {
			DetailLevel zl = (DetailLevel) o;
			return ( zl.getScale() == getScale() );
		}
		return false;
	}

	@Override
	public int hashCode() {
		long bits = ( Double.doubleToLongBits( getScale() ) * 43 );
		return ( ( (int) bits ) ^ ( (int) ( bits >> 32 ) ) );
	}

	
}