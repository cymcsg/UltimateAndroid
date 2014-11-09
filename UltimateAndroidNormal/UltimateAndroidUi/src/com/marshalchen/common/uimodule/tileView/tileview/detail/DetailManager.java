package com.marshalchen.common.uimodule.tileView.tileview.detail;

import android.graphics.Rect;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.selector.TileSetSelector;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.selector.TileSetSelectorMinimalUpScale;

import java.util.HashSet;

public class DetailManager {

	private static final double PRECISION = 6;
	private static final double DECIMAL = Math.pow( 10, PRECISION );
	
	private DetailLevelSet detailLevels = new DetailLevelSet();
	private HashSet<DetailLevelEventListener> detailLevelEventListeners = new HashSet<DetailLevelEventListener>();
	private HashSet<DetailLevelSetupListener> detailLevelSetupListeners = new HashSet<DetailLevelSetupListener>();

	private double scale = 1;
	private double historicalScale;
	
	private DetailLevel currentDetailLevel;
	
	private int width;
	private int height;
	private int scaledWidth;
	private int scaledHeight;
	
	private boolean detailLevelLocked = false;
	
	private int padding = 0;
	private Rect viewport = new Rect();
	private Rect computedViewport = new Rect();
	
	private DetailLevelPatternParser detailLevelPatternParser = new DetailLevelPatternParserDefault();

	private static double getAtPrecision( double s ) {
		return Math.round( s * DECIMAL ) / DECIMAL;
	}

	public DetailManager(){
		update( true );
	}

	public double getScale() {
		return scale;
	}

	public void setScale( double s ) {
		// round to PRECISION decimal places
		// DEBUG: why are we rounding still?
		s = getAtPrecision( s );
		// is it changed?
		boolean changed = ( scale != s );
		// set it
		scale = s;
		// update computed values
		update( changed );		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	// DEBUG: needed?  maybe use ZPL's width and height...?
	public int getScaledWidth(){
		return scaledWidth;
	}
	
	public int getScaledHeight(){
		return scaledHeight;
	}
	
	public void setSize( int w, int h ) {
		width = w;
		height = h;
		update( true );
	}
	
	/**
	 *  "pads" the viewport by the number of pixels passed.  e.g., setPadding( 100 ) instructs the
	 *  DetailManager to interpret it's actual viewport offset by 100 pixels in each direction (top, left,
	 *  right, bottom), so more tiles will qualify for "visible" status when intersections are calculated.
	 * @param pixels (int) the number of pixels to pad the viewport by
	 */
	public void setPadding( int pixels ) {
		padding = pixels;
		updateComputedViewport();
	}
	
	public void updateViewport( int left, int top, int right, int bottom ) {
		viewport.set( left, top, right, bottom );
		updateComputedViewport();
	}
	
	private void updateComputedViewport() {
		computedViewport.set( viewport );
		computedViewport.top -= padding;
		computedViewport.left -= padding;
		computedViewport.bottom += padding;
		computedViewport.right += padding;
	}
	
	public Rect getViewport() {
		return viewport;
	}
	
	public Rect getComputedViewport() {		
		return computedViewport;
	}
	
	public DetailLevelPatternParser getDetailLevelPatternParser() {
		return detailLevelPatternParser;
	}
	
	public void setDetailLevelPatternParser( DetailLevelPatternParser parser ) {
		detailLevelPatternParser = parser;
	}
	
	private void update( boolean changed ){
		// has there been a change in tile sets?
		boolean detailLevelChanged = false;		
		// if detail level is locked, do not change tile sets
		if(!detailLevelLocked){			
			// get the most appropriate detail level for the current scale
			DetailLevel matchingLevel = detailLevels.find( getScale() );
			// if one is found (if any tile sets are registered)
			if(matchingLevel != null){
				// is it the same as the one being used?
				detailLevelChanged = !matchingLevel.equals( currentDetailLevel );				
				// update current detail level
				currentDetailLevel = matchingLevel; 
			}			
		}		
		// update scaled values
		scaledWidth = (int) ( getWidth() * getScale() );
		scaledHeight = (int) ( getHeight() * getScale() );
		// broadcast scale change
		if( changed ) {
			for ( DetailLevelEventListener listener : detailLevelEventListeners ) {
				listener.onDetailScaleChanged( getScale() );
			}			
		}
		// if there's a change in detail, update appropriate values
		if ( detailLevelChanged ) {			
			// notify all interested parties
			for ( DetailLevelEventListener listener : detailLevelEventListeners ) {
				listener.onDetailLevelChanged();
			}
		}
	}

	public void lockDetailLevel(){
		detailLevelLocked = true;
	}
	
	public void unlockDetailLevel(){
		detailLevelLocked = false;
	}

	public void addDetailLevelEventListener( DetailLevelEventListener l ) {
		detailLevelEventListeners.add( l );
	}

	public void removeDetailLevelEventListener( DetailLevelEventListener l ) {
		detailLevelEventListeners.remove( l );
	}
	
	public void addDetailLevelSetupListener( DetailLevelSetupListener l ) {
		detailLevelSetupListeners.add( l );
	}

	public void removeDetailLevelSetupListener( DetailLevelSetupListener l ) {
		detailLevelSetupListeners.remove( l );
	}
	
	private void addDetailLevel( DetailLevel detailLevel ) {
		detailLevels.addDetailLevel( detailLevel );
		update( false );
		for ( DetailLevelSetupListener listener : detailLevelSetupListeners ) {
			listener.onDetailLevelAdded();
		}
	}
	
	public void addDetailLevel( float scale, String pattern, String downsample ) {
		DetailLevel detailLevel = new DetailLevel( this, scale, pattern, downsample );
		addDetailLevel( detailLevel );
	}
	
	public void addDetailLevel( float scale, String pattern, String downsample, int tileWidth, int tileHeight ) {
		DetailLevel detailLevel = new DetailLevel( this, scale, pattern, downsample, tileWidth, tileHeight );
		addDetailLevel( detailLevel );
	}
	
	public void resetDetailLevels(){
		detailLevels.clear();
		update( false );
	}

	public DetailLevel getCurrentDetailLevel() {
		return currentDetailLevel;
	}
	
	public double getCurrentDetailLevelScale(){
		if(currentDetailLevel != null ) {
			return currentDetailLevel.getScale();
		}
		return 1;
	}
	
	public double getHistoricalScale(){
		return historicalScale;
	}
	
	public void saveHistoricalScale(){
		historicalScale = scale;
	}

	public TileSetSelector getTileSetSelector() {
	    return this.detailLevels.getTileSetSelector();
	}

	/**
	 * Set the tile selection method, defaults to {@link TileSetSelectorMinimalUpScale}
	 * 
	 * @param selector
	 */
	public void setTileSetSelector(TileSetSelector selector) {
	    this.detailLevels.setTileSetSelector(selector);
	}

}
