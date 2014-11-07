package com.marshalchen.common.uimodule.tileView.tileview;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import android.view.ViewGroup;
import com.marshalchen.common.uimodule.tileView.layouts.AnchorLayout;
import com.marshalchen.common.uimodule.tileView.layouts.ZoomPanLayout;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelPatternParser;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailManager;
import com.marshalchen.common.uimodule.tileView.tileview.geom.PositionManager;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoder;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoderHttp;
import com.marshalchen.common.uimodule.tileView.tileview.hotspots.HotSpot;
import com.marshalchen.common.uimodule.tileView.tileview.hotspots.HotSpotEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.hotspots.HotSpotManager;
import com.marshalchen.common.uimodule.tileView.tileview.markers.CalloutManager;
import com.marshalchen.common.uimodule.tileView.tileview.markers.MarkerEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.markers.MarkerManager;
import com.marshalchen.common.uimodule.tileView.tileview.paths.DrawablePath;
import com.marshalchen.common.uimodule.tileView.tileview.paths.PathHelper;
import com.marshalchen.common.uimodule.tileView.tileview.paths.PathManager;
import com.marshalchen.common.uimodule.tileView.tileview.samples.SampleManager;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.TileManager;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.TileRenderListener;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.selector.TileSetSelector;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.selector.TileSetSelectorMinimalUpScale;

import java.util.HashSet;
import java.util.List;

/**
 * The TileView widget is a subclass of ViewGroup that provides a mechanism to asynchronously display tile-based images,
 * with additional functionality for 2D dragging, flinging, pinch or double-tap to zoom, adding overlaying Views (markers),
 * built-in Hot Spot support, dynamic path drawing, multiple levels of detail, and support for any relative positioning or 
 * coordinate system.
 * 
 * <p>A minimal implementation:</p>
 *  
 * <pre>{@code
 * TileView tileView = new TileView(this);
 * tileView.setSize(3000,5000);
 * tileView.addDetailLevel(1.0f, "path/to/tiles/%col%-%row%.jpg");
 * }</pre>
 * 
 * A more advanced implementation might look like:
 * <pre>{@code
 * TileView tileView = new TileView(this);
 * tileView.setSize(3000,5000);
 * tileView.addTileViewEventListener(someMapEventListener);
 * tileView.defineRelativeBounds(42.379676, -71.094919, 42.346550, -71.040280);
 * tileView.addDetailLevel(1.000f, "tiles/boston-1000-%col%_%row%.jpg", 256, 256);
 * tileView.addDetailLevel(0.500f, "tiles/boston-500-%col%_%row%.jpg", 256, 256);
 * tileView.addDetailLevel(0.250f, "tiles/boston-250-%col%_%row%.jpg", 256, 256);
 * tileView.addDetailLevel(0.125f, "tiles/boston-125-%col%_%row%.jpg", 128, 128);
 * tileView.addMarker(someView, 42.35848, -71.063736);
 * tileView.addMarker(anotherView, 42.3665, -71.05224);
 * tileView.addMarkerEventListener(someMarkerEventListener);
 * }</pre>
 * 
 */
public class TileView extends ZoomPanLayout {

	private HashSet<TileViewEventListener> tileViewEventListeners = new HashSet<TileViewEventListener>();
	
	private DetailManager detailManager = new DetailManager();
	private PositionManager positionManager = new PositionManager();
	
	private HotSpotManager hotSpotManager = new HotSpotManager( detailManager );
	
	private SampleManager sampleManager;
	private TileManager tileManager;
	private PathManager pathManager;
	private MarkerManager markerManager;
	private CalloutManager calloutManager;
	
	
	/**
	 * Constructor to use when creating a TileView from code.  Inflating from XML is not currently supported.
	 * @param context (Context) The Context the TileView is running in, through which it can access the current theme, resources, etc.
	 */
	public TileView( Context context ) {
		
		super( context );
		
		sampleManager = new SampleManager( context, detailManager );
		addView( sampleManager );
		
		tileManager = new TileManager( context, detailManager );
		addView( tileManager );		
		
		pathManager = new PathManager( context, detailManager );
		addView( pathManager );
		
		markerManager = new MarkerManager( context, detailManager );
		addView( markerManager );		
		
		calloutManager = new CalloutManager( context, detailManager );
		addView( calloutManager );
		
		detailManager.addDetailLevelEventListener( detailLevelEventListener );
		tileManager.setTileRenderListener( renderListener );
		
		addZoomPanListener( zoomPanListener );
		addGestureListener( gestureListener );
		
		requestRender();
		
	}
   
	//------------------------------------------------------------------------------------
	// PUBLIC API
	//------------------------------------------------------------------------------------

	//------------------------------------------------------------------------------------
	// Event Management API
	//------------------------------------------------------------------------------------
	
	/**
	 * Register an event listener callback object for this TileView.
	 * Note this is method adds a listener to an array of listeners, and does not set
	 * a single listener member a single listener.
	 * @param listener (TileViewEventListener) an implementation of the TileViewEventListener interface
	 */
	public void addTileViewEventListener( TileViewEventListener listener ) {
		tileViewEventListeners.add( listener );
	}
	
	/**
	 * Removes a TileViewEventListener object from those listening to this TileView.
	 * @param listener (TileViewEventListener) an implementation of the TileViewEventListener interface
	 */
	public void removeTileViewEventListener( TileViewEventListener listener ) {
		tileViewEventListeners.remove( listener );
	}
	
	//------------------------------------------------------------------------------------
	// Rendering API
	//------------------------------------------------------------------------------------
	
	/**
	 * Request that the current tile set is re-examined and re-drawn.
	 * The request is added to a queue and is not guaranteed to be processed at any particular
	 * time, and will never be handled immediately.
	 */
	public void requestRender(){
		tileManager.requestRender();
	}
	
	/**
	 * Notify the TileView that it may stop rendering tiles.  The rendering thread will be
	 * sent an interrupt request, but no guarantee is provided when the request will be responded to.
	 */
	public void cancelRender() {
		tileManager.cancelRender();
	}
	
	/**
	 * Enables or disables tile image caching (in-memory and on-disk)
	 * @param shouldCache (boolean) true to enable caching, false to disable it (default)
	 */
	public void setCacheEnabled( boolean shouldCache ) {
		tileManager.setCacheEnabled( shouldCache );
	}
	
	/**
	 * Sets a custom class to perform the decode operation when tile bitmaps are requested.
	 * By default, a BitmapDecoder implementation is provided that renders bitmaps from the context's Assets,
	 * but alternative implementations could be used that fetch images via HTTP, or from the SD card, or resources, SVG, etc.
	 * This signature is identical to calling setTileDecoder and setDownsampleDecoder with the same decoder instance as the parameter.
	 * {@link BitmapDecoderHttp} is an example of such an implementation.
	 * @param decoder (BitmapDecoder) A class instance that implements BitmapDecoder, and must define a decode method, which accepts a String file name and a Context object, and returns a Bitmap
	 */
	public void setDecoder( BitmapDecoder decoder ) {
		setTileDecoder( decoder );
		setDownsampleDecoder( decoder );
	}
	
	/**
	 * Sets a custom class to perform the decode operation when tile bitmaps are requested for tile images only.
	 * By default, a BitmapDecoder implementation is provided that renders bitmaps from the context's Assets,
	 * but alternative implementations could be used that fetch images via HTTP, or from the SD card, or resources, SVG, etc.
	 * {@link BitmapDecoderHttp} is an example of such an implementation.
	 * @param decoder (BitmapDecoder) A class instance that implements BitmapDecoder, and must define a decode method, which accepts a String file name and a Context object, and returns a Bitmap
	 */
	public void setTileDecoder( BitmapDecoder decoder ) {
		tileManager.setDecoder( decoder );
	}
	
	/**
	 * Sets a custom class to perform the decode operation when tile bitmaps are requested for downsample images only.
	 * By default, a BitmapDecoder implementation is provided that renders bitmaps from the context's Assets,
	 * but alternative implementations could be used that fetch images via HTTP, or from the SD card, or resources, SVG, etc.
	 * {@link BitmapDecoderHttp} is an example of such an implementation.
	 * @param decoder (BitmapDecoder) A class instance that implements BitmapDecoder, and must define a decode method, which accepts a String file name and a Context object, and returns a Bitmap
	 */
	public void setDownsampleDecoder( BitmapDecoder decoder ) {
		sampleManager.setDecoder( decoder );
	}
	
	/**
	 * Get the {@link TileSetSelector} implementation currently used to select tile sets.
	 * @return TileSetSelector implementation currently in use.
	 */
	public TileSetSelector getTileSetSelector() {
	    return detailManager.getTileSetSelector();
	}

	/**
	 * Set the tile selection method, defaults to {@link TileSetSelectorMinimalUpScale}
	 * Implement the {@link TileSetSelector} interface to customize how tile sets are selected.
	 * @param selector (TileSetSelector) implementation that handles tile set selection as scale is changed.
	 */
	public void setTileSetSelector(TileSetSelector selector) {
	    detailManager.setTileSetSelector(selector);
	}
	
	/**
	 * Defines whether tile bitmaps should be rendered using an AlphaAnimation
	 * @param enabled (boolean) true if the TileView should render tiles with fade transitions
	 */
	public void setTransitionsEnabled( boolean enabled ) {
		tileManager.setTransitionsEnabled( enabled );
	}
	
	/**
	 * Define the duration (in milliseconds) for each tile transition.
	 * @param duration (int) the duration of the transition in milliseconds.
	 */
	public void setTransitionDuration( int duration ) {
		tileManager.setTransitionDuration( duration );
	}
	
	//------------------------------------------------------------------------------------
	// Detail Level Management API
	//------------------------------------------------------------------------------------
	
	/**
	 * Defines the total size, in pixels, of the tile set at 100% scale.
	 * The TileView wills pan within it's layout dimensions, with the content (scrollable)
	 * size defined by this method.
	 * @param width (int) total width of the tiled set
	 * @param height (int) total height of the tiled set
	 */
	@Override
	public void setSize( int w, int h ) {
		// super (define clip area)
		super.setSize( w, h );
		// coordinate with other components
		detailManager.setSize( w, h );
		// notify manager for relative positioning
		positionManager.setSize( w, h );
	}
	
	/**
	 * Register a tile set to be used for a particular detail level.
	 * Each tile set to be used must be registered using this method,
	 * and at least one tile set must be registered for the TileView to render any tiles.
	 * @param detailScale (float) scale at which the TileView should use the tiles in this set.
	 * @param pattern (String) string path to the location of the tile image files, to be parsed by a DetailLevelPatternParser
	 */
	public void addDetailLevel( float detailScale, String pattern ) {
		detailManager.addDetailLevel( detailScale, pattern, null );
	}
	
	/**
	 * Register a tile set to be used for a particular detail level.
	 * Each tile set to be used must be registered using this method,
	 * and at least one tile set must be registered for the TileView to render any tiles.
	 * @param detailScale (float) scale at which the TileView should use the tiles in this set.
	 * @param pattern (String) string path to the location of the tile image files, to be parsed by a DetailLevelPatternParser
	 * @param downsample (String) string path to the location of an optional non-tiled single image file that will fill the tile view, on a z-layer below tiles
	 */
	public void addDetailLevel( float detailScale, String pattern, String downsample ){
		detailManager.addDetailLevel( detailScale, pattern, downsample );
	}
	
	/**
	 * Register a tile set to be used for a particular detail level.
	 * Each tile set to be used must be registered using this method,
	 * and at least one tile set must be registered for the TileView to render any tiles.
	 * @param detailScale (float) scale at which the TileView should use the tiles in this set.
	 * @param pattern (String) string path to the location of the tile image files, to be parsed by a DetailLevelPatternParser
	 * @param downsample (String) string path to the location of an optional non-tiled single image file that will fill the tile view, on a z-layer below tiles
	 * @param tileWidth (int) size of each tiled column
	 * @param tileHeight (int) size of each tiled row
	 */
	public void addDetailLevel( float detailScale, String pattern, String downsample, int tileWidth, int tileHeight ){
		detailManager.addDetailLevel( detailScale, pattern, downsample, tileWidth, tileHeight );
	}
	
	/**
	 * Clear all previously registered zoom levels.  This method is experimental.
	 */
	public void resetDetailLevels(){
		detailManager.resetDetailLevels();
		refresh();
	}
	
	/**
	 * While the detail level is locked (after this method is invoked, and before unlockDetailLevel is invoked),
	 * the DetailLevel will not change, and the current DetailLevel will be scaled beyond the normal
	 * bounds.  Normally, during any scale change the details manager searches for the DetailLevel with
	 * a registered scale closest to the defined scale.  While locked, this does not occur.
	 */
	public void lockDetailLevel(){
		detailManager.lockDetailLevel();
	}
	
	/**
	 * Unlocks a DetailLevel locked with lockDetailLevel
	 */
	public void unlockDetailLevel(){
		detailManager.unlockDetailLevel();
	}
	
	/**
	 * pads the viewport by the number of pixels passed.  e.g., setViewportPadding( 100 ) instructs the
	 * TileView to interpret it's actual viewport offset by 100 pixels in each direction (top, left,
	 * right, bottom), so more tiles will qualify for "visible" status when intersections are calculated.
	 * @param padding (int) the number of pixels to pad the viewport by
	 */
	public void setViewportPadding( int padding ) {
		detailManager.setPadding( padding );
	}
	
	/**
	 * Define a custom parser to manage String file names representing image tiles
	 * @param parser (DetailLevelPatternParser) parser that returns String objects from passed pattern, column and row.
	 */
	public void setTileSetPatternParser( DetailLevelPatternParser parser ) {
		detailManager.setDetailLevelPatternParser( parser );
	}
	
	//------------------------------------------------------------------------------------
	// Positioning API
	//------------------------------------------------------------------------------------
	
	/**
	 * Register a set of offset points to use when calculating position within the TileView.
	 * Any type of coordinate system can be used (any type of lat/lng, percentile-based, etc),
	 * and all positioned are calculated relatively.  If relative bounds are defined, position parameters
	 * received by TileView methods will be translated to the the appropriate pixel value.
	 * To remove this process, use undefineRelativeBounds
	 * @param left (double) the left edge of the rectangle used when calculating position
	 * @param top (double) the top edge of the rectangle used when calculating position
	 * @param right (double) the right edge of the rectangle used when calculating position
	 * @param bottom (double) the bottom edge of the rectangle used when calculating position
	 */
	public void defineRelativeBounds( double left, double top, double right, double bottom  ) {
		positionManager.setBounds( left, top, right, bottom );
	}
	
	/**
	 * Unregisters arbitrary bounds and coordinate system.  After invoking this method, TileView methods that
	 * receive position method parameters will use pixel values, relative to the TileView's registered size (at 1.0d scale)
	 */
	public void undefineRelativeBounds() {
		positionManager.unsetBounds();
	}
	
	/**
	 * Translate a relative x and y position into a Point object with x and y values populated as pixel values, relative to the size of the TileView.
	 * @param x (int) relative x position to be translated to absolute pixel value
	 * @param y (int) relative y position to be translated to absolute pixel value
	 * @return Point a Point object with x and y values calculated from the relative Position x and y values
	 */
	public Point translate( double x, double y ) {
		return positionManager.translate( x, y );
	}
	
	/**
	 * Translate a List of relative x and y positions (double array... { x, y }
	 * into Point objects with x and y values populated as pixel values, relative to the size of the TileView.
	 * @param positions (List<double[]>) List of 2-element double arrays to be translated to Points (pixel values).  The first double should represent the relative x value, the second is y
	 * @return List<Point> List of Point objects with x and y values calculated from the corresponding x and y values
	 */
	public List<Point> translate( List<double[]> positions ) {
		return positionManager.translate( positions );
	}
	
	/**
	 * Divides a number by the current scale value, effectively flipping scaled values.  This can be useful when
	 * determining a relative position or dimension from a real pixel value.
	 * @param value (double) The number to be inversely scaled.
	 * @return (double) The inversely scaled product.
	 */
	public double unscale( double value ) {
		return value / getScale();
	}
	
	/**
	 * Scrolls (instantly) the TileView to the x and y positions provided.
	 * @param x (double) the relative x position to move to
	 * @param y (double) the relative y position to move to
	 */
	public void moveTo( double x, double y ) {
		Point point = positionManager.translate( x, y, getScale() );
		scrollToPoint( point );
		requestRender();
	}
	
	/**
	 * Scrolls (instantly) the TileView to the x and y positions provided, then centers the viewport to the position.
	 * @param x (double) the relative x position to move to
	 * @param y (double) the relative y position to move to
	 */
	public void moveToAndCenter( double x, double y ) {
		Point point = positionManager.translate( x, y, getScale() );
		scrollToAndCenter( point );
		requestRender();
	}
	
	/**
	 * Scrolls (with animation) the TIelView to the relative x and y positions provided.
	 * @param x (double) the relative x position to move to
	 * @param y (double) the relative y position to move to
	 */
	public void slideTo( double x, double y ) {
		Point point = positionManager.translate( x, y, getScale() );
		slideToPoint( point );
	}
	
	/**
	 * Scrolls (with animation) the TileView to the x and y positions provided, then centers the viewport to the position.
	 * @param x (double) the relative x position to move to
	 * @param y (double) the relative y position to move to
	 */
	public void slideToAndCenter( double x, double y ) {
		Point point = positionManager.translate( x, y, getScale() );
		slideToAndCenter( point );
	}
	
	/**
	 * Scales and moves TileView so that each of the passed points is visible.
	 * @param points (List<double[]>) List of 2-element double arrays to be translated to Points (pixel values).  The first double should represent the relative x value, the second is y
	 */
	public void framePoints( List<double[]> points ) {

		    double topMost = -Integer.MAX_VALUE;
		    double bottomMost = Integer.MAX_VALUE;
		    double leftMost = Integer.MAX_VALUE;
		    double rightMost = -Integer.MAX_VALUE;

		    for( double[] coordinate : points ) {
	            double x = coordinate[0];
	            double y = coordinate[1];
	            if(positionManager.contains( x, y )){
	            	topMost = Math.max( topMost, x );
		            bottomMost = Math.min( bottomMost, x );
		            leftMost = Math.min( leftMost, y );
		            rightMost = Math.max( rightMost, y );	
	            }	            	        
		    }

		    Point topRight = translate( topMost, rightMost );
		    Point bottomLeft = translate( bottomMost, leftMost );

		    int width = bottomLeft.x - topRight.x;
		    int height = bottomLeft.y - topRight.y;

		    double scaleX = Math.abs( getWidth() / (double) width );
		    double scaleY = Math.abs( getHeight() / (double) height );

		    double destinationScale = Math.min( scaleX, scaleY );

		    double middleX = ( rightMost + leftMost ) * 0.5f;
		    double middleY = ( topMost + bottomMost ) * 0.5f;

		    moveToAndCenter( middleY, middleX );        
		    setScaleFromCenter( destinationScale );     
		
	}
	
	
	//------------------------------------------------------------------------------------
	// Marker, Callout and HotSpot API
	//------------------------------------------------------------------------------------
	
	/**
	 * Markers added to this TileView will have anchor logic applied on the values provided here.
	 * E.g., setMarkerAnchorPoints(-0.5f, -1.0f) will have markers centered horizontally, positioned
	 * vertically to a value equal to - 1 * height.
	 * Note that individual markers can be assigned specific anchors - this method applies a default
	 * value to all markers added without specifying anchor values.
	 * @param anchorX (float) the x-axis position of a marker will be offset by a number equal to the width of the marker multiplied by this value 
	 * @param anchorY (float) the y-axis position of a marker will be offset by a number equal to the height of the marker multiplied by this value
	 */
	public void setMarkerAnchorPoints( float anchorX, float anchorY ) {
		markerManager.setAnchors( anchorX, anchorY );
	}
	
	/**
	 * Add a marker to the the TileView.  The marker can be any View.
	 * No LayoutParams are required; the View will be laid out using WRAP_CONTENT for both width and height, and positioned based on the parameters
	 * @param view (View) View instance to be added to the TileView
	 * @param x (double) relative x position the View instance should be positioned at
	 * @param y (double) relative y position the View instance should be positioned at
	 * @return (View) the View instance added to the TileView
	 */
	public View addMarker( View view, double x, double y ) {
		Point point = positionManager.translate( x, y );
		return markerManager.addMarker( view, point.x, point.y );
	}
	
	/**
	 * Add a marker to the the TileView.  The marker can be any View.
	 * No LayoutParams are required; the View will be laid out using WRAP_CONTENT for both width and height, and positioned based on the parameters
	 * @param view (View) View instance to be added to the TileView
	 * @param x (double) relative x position the View instance should be positioned at
	 * @param y (double) relative y position the View instance should be positioned at
	 * @param aX (float) the x-axis position of a marker will be offset by a number equal to the width of the marker multiplied by this value 
	 * @param aY (float) the y-axis position of a marker will be offset by a number equal to the height of the marker multiplied by this value
	 * @return (View) the View instance added to the TileView
	 */
	public View addMarker( View view, double x, double y, float anchorX, float anchorY ) {
		Point point = positionManager.translate( x, y );
		return markerManager.addMarker( view, point.x, point.y, anchorX, anchorY );
	}

	/**
	 * Removes a marker View from the TileView's view tree.
	 * @param view (View) The marker View to be removed.
	 */
	public void removeMarker( View view ) {
		markerManager.removeMarker( view );
	}
	
	/**
	 * Moves an existing marker to another position.
	 * @param view The marker View to be repositioned.
	 * @param x (double) relative x position the View instance should be positioned at
	 * @param y (double) relative y position the View instance should be positioned at
	 */
	public void moveMarker( View view, double x, double y ){
		if( markerManager.indexOfChild( view ) > -1 ){
			Point point = positionManager.translate( x, y );
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if( params instanceof AnchorLayout.LayoutParams ) {
				AnchorLayout.LayoutParams anchorLayoutParams = (AnchorLayout.LayoutParams) params;
				anchorLayoutParams.x = point.x;
				anchorLayoutParams.y = point.y;
				view.setLayoutParams( anchorLayoutParams );
				markerManager.requestLayout();
			}	
		}			
	}
	
	/**
	 * Scroll the TileView so that the View passed is centered in the viewport
	 * @param view (View) the View marker that the TileView should center on.
	 * @params animate (boolean) should the movement use a transition effectg
	 */
	public void moveToMarker( View view, boolean animate ) {
		if( markerManager.indexOfChild( view ) > -1 ){
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if( params instanceof AnchorLayout.LayoutParams ) {
				AnchorLayout.LayoutParams anchorLayoutParams = (AnchorLayout.LayoutParams) params;
				int scaledX = (int) ( anchorLayoutParams.x * getScale() );
				int scaledY = (int) ( anchorLayoutParams.y * getScale() );
				Point point = new Point( scaledX, scaledY );
				if( animate ) {
					slideToAndCenter( point );
				} else {
					scrollToAndCenter( point );
				}
			}	
		}
	}
	
	/**
	 * Scroll the TileView so that the View passed is centered in the viewport
	 * @param view (View) the View marker that the TileView should center on.
	 */
	public void moveToMarker( View view ) {
		moveToMarker( view, false );
	}
	
	/**
	 * Register a MarkerEventListener.  Unlike standard touch events attached to marker View's (e.g., View.OnClickListener),
	 * MarkerEventListeners do not consume the touch event, so will not interfere with scrolling.  While the event is
	 * dispatched from a Tap event, it's routed though a hit detection API to trigger the listener.
	 * @param listener (MarkerEventListener) listener to be added to the TileView's list of MarkerEventListeners
	 */
	public void addMarkerEventListener( MarkerEventListener listener ) {
		markerManager.addMarkerEventListener( listener );
	}
	
	/**
	 * Removes a MarkerEventListener from the TileView's registry.
	 * @param listener (MarkerEventListener) listener to be removed From the TileView's list of MarkerEventListeners
	 */
	public void removeMarkerEventListener( MarkerEventListener listener ) {
		markerManager.removeMarkerEventListener( listener );
	}

	/**
	 * Add a callout to the the TileView.  The callout can be any View.
	 * No LayoutParams are required; the View will be laid out using WRAP_CONTENT for both width and height, and positioned based on the parameters
	 * Callout views will always be positioned at the top of the view tree (at the highest z-index), and will always be removed during any touch event
	 * that is not consumed by the callout View.
	 * @param view (View) View instance to be added to the TileView's
	 * @param x (double) relative x position the View instance should be positioned at
	 * @param y (double) relative y position the View instance should be positioned at
	 * @return (View) the View instance added to the TileView's
	 */
	public View addCallout( View view, double x, double y ) {
		Point point = positionManager.translate( x, y );
		return calloutManager.addMarker( view, point.x, point.y );
	}
	
	/**
	 * Add a callout to the the TileView.  The callout can be any View.
	 * No LayoutParams are required; the View will be laid out using WRAP_CONTENT for both width and height, and positioned based on the parameters
	 * Callout views will always be positioned at the top of the view tree (at the highest z-index), and will always be removed during any touch event
	 * that is not consumed by the callout View.
	 * @param view (View) View instance to be added to the TileView's
	 * @param x (double) relative x position the View instance should be positioned at
	 * @param y (double) relative y position the View instance should be positioned at
	 * @param aX (float) the x-axis position of a callout view will be offset by a number equal to the width of the callout view multiplied by this value 
	 * @param aY (float) the y-axis position of a callout view will be offset by a number equal to the height of the callout view multiplied by this value
	 * @return (View) the View instance added to the TileView's
	 */
	public View addCallout( View view, double x, double y, float anchorX, float anchorY ) {
		Point point = positionManager.translate( x, y );
		return calloutManager.addMarker( view, point.x, point.y, anchorX, anchorY );
	}

	/**
	 * Removes a callout View from the TileView's view tree.
	 * @param view The callout View to be removed.
	 * @return (boolean) true if the view was in the view tree and was removed, false if it was not in the view tree
	 */
	public boolean removeCallout( View view ) {
		if( calloutManager.indexOfChild( view ) > -1 ) {
			calloutManager.removeView( view );
			return true;
		}
		return false;
	}
	
	/**
	 * Register a HotSpot that should fire an listener when a touch event occurs that intersects that rectangle.
	 * The HotSpot moves and scales with the TileView.
	 * @param hotSpot (HotSpot) the hotspot that is tested against touch events that occur on the TileView
	 * @return HotSpot the hotspot created with this method
	 */
	public HotSpot addHotSpot( HotSpot hotSpot ){
		hotSpotManager.addHotSpot( hotSpot );
		return hotSpot;
	}
	
	/**
	 * Register a HotSpot that should fire an listener when a touch event occurs that intersects that rectangle.
	 * The HotSpot moves and scales with the TileView.
	 * @param positions (List<double[]>) List of paired doubles { x, y } that represents the points that make up the region.
	 * @return HotSpot the hotspot created with this method
	 */
	public HotSpot addHotSpot( List<double[]> positions ) {
		List<Point> points = positionManager.translate( positions );
		Path path = PathHelper.pathFromPoints( points );
		path.close();
		RectF bounds = new RectF();
		path.computeBounds( bounds, true );
		Rect rect = new Rect();
		bounds.round( rect );
		Region clip = new Region( rect );
		HotSpot hotSpot = new HotSpot();
		hotSpot.setPath( path, clip );
		return addHotSpot( hotSpot );
	}
	
	/**
	 * Register a HotSpot that should fire an listener when a touch event occurs that intersects that rectangle.
	 * The HotSpot moves and scales with the TileView.
	 * @param positions (List<double[]>) List of paired doubles { x, y } that represents the points that make up the region.
	 * @param listener (HotSpotEventListener) listener to attach to this hotspot, which will be invoked if a Tap event is fired that intersects the hotspot's Region
	 * @return HotSpot the hotspot created with this method
	 */
	public HotSpot addHotSpot( List<double[]> positions, HotSpotEventListener listener ) {
		HotSpot hotSpot = addHotSpot( positions );
		hotSpot.setHotSpotEventListener( listener );
		return hotSpot;
	}
	
	/**
	 * Remove a HotSpot registered with addHotSpot
	 * @param hotSpot (HotSpot) the hotspot to remove
	 * @return (boolean) true if a hotspot was removed, false if not
	 */
	public void removeHotSpot( HotSpot hotSpot ) {
		hotSpotManager.removeHotSpot( hotSpot );
	}
	
	/**
	 * Register a HotSpotEventListener with the TileView.  This listener will fire if any hotspot's region intersects a Tap event.
	 * @param listener (HotSpotEventListener) the listener to be added.
	 */
	public void addHotSpotEventListener( HotSpotEventListener listener ) {
		hotSpotManager.addHotSpotEventListener( listener );
	}
	
	/**
	 * Remove a HotSpotEventListener from the TileView's registry.
	 * @param listener (HotSpotEventListener) the listener to be removed
	 */
	public void removeHotSpotEventListener( HotSpotEventListener listener ) {
		hotSpotManager.removeHotSpotEventListener( listener );
	}
	
	//------------------------------------------------------------------------------------
	// Path Drawing API
	//------------------------------------------------------------------------------------
	
	/**
	 * Register a Path and Paint that will be drawn on a layer above the tiles, but below markers.
	 * This Path's will be scaled with the TileView, but will always be as wide as the stroke set for the Paint.
	 * @param drawablePath (DrawablePath) a DrawablePath instance to be drawn by the TileView
	 * @return DrawablePath the DrawablePath instance passed to the TileView
	 */
	public DrawablePath drawPath( DrawablePath drawablePath ) {
		return pathManager.addPath( drawablePath );
	}
	
	/**
	 * Register a Path and Paint that will be drawn on a layer above the tiles, but below markers.
	 * This Path's will be scaled with the TileView, but will always be as wide as the stroke set for the Paint.
	 * @param positions (List<double[]>) List of doubles { x, y } that represent the points of the Path.
	 * @return DrawablePath the DrawablePath instance passed to the TileView
	 */
	public DrawablePath drawPath( List<double[]> positions ) {
		List<Point> points = positionManager.translate( positions );
		return pathManager.addPath( points );
	}
	
	/**
	 * Register a Path and Paint that will be drawn on a layer above the tiles, but below markers.
	 * This Path's will be scaled with the TileView, but will always be as wide as the stroke set for the Paint.
	 * @param positions (List<double[]>) List of doubles { x, y } that represent the points of the Path.
	 * @param paint (Paint) the Paint instance that defines the style of the drawn path.
	 * @return DrawablePath the DrawablePath instance passed to the TileView
	 */
	public DrawablePath drawPath( List<double[]> positions, Paint paint ) {
		List<Point> points = positionManager.translate( positions );
		return pathManager.addPath( points, paint );
	}
	
	/**
	 * Removes a DrawablePath from the TileView's registry.  This path will no longer be drawn by the TileView.
	 * @param drawablePath (DrawablePath) the DrawablePath instance to be removed.
	 */
	public void removePath( DrawablePath drawablePath ) {
		pathManager.removePath( drawablePath );
	}
	
	/**
	 * Returns the Paint instance used by default.  This can be modified for future Path paint operations.
	 * @return Paint the Paint instance used by default.
	 */
	public Paint getPathPaint(){
		return pathManager.getPaint();
	}
	
	//------------------------------------------------------------------------------------
	// Memory Management API
	//------------------------------------------------------------------------------------
	
	/**
	 * Clear bitmap image files, appropriate for Activity.onPause
	 */
	public void clear() {
		tileManager.clear();
		sampleManager.clear();
		pathManager.setShouldDraw( false );
	}
	
	/**
	 * Clear bitmap image files, appropriate for Activity.onPause (mirror for .clear)
	 */
	public void pause() {
		clear();
	}
	
	/**
	 * Clear tile image files and remove all views, appropriate for Activity.onDestroy
	 * References to TileView should be set to null following invocations of this method.
	 */
	public void destroy() {
		tileManager.clear();
		sampleManager.clear();
		pathManager.clear();
	}
	
	/**
	 * Restore visible state (generally after a call to .clear()
	 * Appropriate for Activity.onResume
	 */
	public void resume(){
		updateViewport();
		tileManager.requestRender();
		sampleManager.update();
		pathManager.setShouldDraw( true );
	}
	
	/**
	 * Request the TileView reevaluate tile sets, rendered tiles, samples, invalidates, etc
	 */
	public void refresh() {
		updateViewport();
		tileManager.updateTileSet();
		tileManager.requestRender();
		sampleManager.update();
		redraw();
	}
	
	//------------------------------------------------------------------------------------
	// PRIVATE API
	//------------------------------------------------------------------------------------
	

	// make sure we keep the viewport UTD, and if layout changes we'll need to recompute what tiles to show
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout( changed, l, t, r, b );
		updateViewport();
		requestRender();
	}	
	
	// let the zoom manager know what tiles to show based on our position and dimensions
	private void updateViewport(){
		int left = getScrollX();
		int top = getScrollY();
		int right = left + getWidth();
		int bottom = top + getHeight();
		detailManager.updateViewport( left, top, right, bottom );
	}   
	
	// tell the tile renderer to not start any more tasks, but it can continue with any that are already running
	private void suppressRender() {
		tileManager.suppressRender();
	}

	
	//------------------------------------------------------------------------------------
	// Private Listeners
	//------------------------------------------------------------------------------------

	private ZoomPanListener zoomPanListener = new ZoomPanListener() {
		@Override
		public void onZoomPanEvent(){
			
		}						
		@Override
		public void onScrollChanged( int x, int y ) {
			updateViewport();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onScrollChanged( x, y );
			}
		}			
		@Override
		public void onScaleChanged( double scale ) { 
			detailManager.setScale( scale );
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onScaleChanged( scale );
			}
		}
		@Override
		public void onZoomStart( double scale ) {
			detailManager.lockDetailLevel();
			detailManager.setScale( scale );
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onZoomStart( scale );
			}
		}
		@Override
		public void onZoomComplete( double scale ) {
			detailManager.unlockDetailLevel();
			detailManager.setScale( scale );
			requestRender();  // put this here instead of gesture listener so we catch animations and pinches
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onZoomComplete( scale );
			}
		}
	};
	
	private DetailLevelEventListener detailLevelEventListener = new DetailLevelEventListener(){
		@Override
		public void onDetailLevelChanged() {
			requestRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onDetailLevelChanged();
			}
		}
		/*
		 * do *not* update scale in response to changes in the zoom manager
		 * transactions are one-way - set scale on TileView (ZoomPanLayout)
		 * and pass those to DetailManager, which then distributes, manages
		 * and notifies all other interested parties.
		 */
		@Override
		public void onDetailScaleChanged( double scale ) {
			
		}
	};
	
	private GestureListener gestureListener = new GestureListener(){

		@Override
		public void onDoubleTap( Point point ) {
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onDoubleTap( point.x, point.y );
			}		
		}
		@Override
		public void onDrag( Point point ) {
			suppressRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onDrag( point.x, point.y );
			}
		}
		@Override
		public void onFingerDown( Point point ) {
			suppressRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onFingerDown( point.x, point.y );
			}
		}
		@Override
		public void onFingerUp( Point point ) {
			if ( !isFlinging() ) {
				requestRender();
			}
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onFingerUp( point.x, point.y );
			}
		}
		@Override
		public void onFling( Point startPoint, Point finalPoint ) {
			suppressRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onFling( startPoint.x, startPoint.y, finalPoint.x, finalPoint.y );
			}
		}
		@Override
		public void onFlingComplete( Point point ) {
			requestRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onFlingComplete( point.x, point.y );
			}
		}
		@Override
		public void onPinch( Point point ) {
			suppressRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onPinch( point.x, point.y );
			}
		}
		@Override
		public void onPinchComplete( Point point ) {
			requestRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onPinchComplete( point.x, point.y );
			}
		}
		@Override
		public void onPinchStart( Point point ) {
			suppressRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onPinchStart( point.x, point.y );
			}
		}
		@Override
		public void onTap( Point point ) {
			markerManager.processHit( point );
			hotSpotManager.processHit( point );
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onTap( point.x, point.y );
			}
		}
		@Override
		public void onScrollComplete( Point point ) {
			requestRender();
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onScrollChanged( point.x, point.y );
			}
		}
	};
	
	private TileRenderListener renderListener = new TileRenderListener(){
		@Override
		public void onRenderCancelled() {
			
		}
		@Override
		public void onRenderComplete() {
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onRenderComplete();
			}
		}
		@Override
		public void onRenderStart() {
			for ( TileViewEventListener listener : tileViewEventListeners ) {
				listener.onRenderStart();
			}
		}		
	};
	
	//------------------------------------------------------------------------------------
	// Public static interfaces and classes
	//------------------------------------------------------------------------------------
	
	/**
	 * Interface for implementations to receive TileView events.  This interface consolidates several disparate
	 * listeners (Gestures, ZoomPan Events, TileView events) into a single unit for ease of use.
	 */
	public static interface TileViewEventListener {
		/**
		 * Fires when a ACTION_DOWN event is raised from the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onFingerDown(int x, int y);
		/**
		 * Fires when a ACTION_UP event is raised from the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onFingerUp(int x, int y);
		/**
		 * Fires while the TileView is being dragged
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onDrag(int x, int y);
		/**
		 * Fires when a user double-taps the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onDoubleTap(int x, int y);
		/**
		 * Fires when a user taps the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onTap(int x, int y);
		/**
		 * Fires while a user is pinching the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onPinch(int x, int y);
		/**
		 * Fires when a user starts a pinch action
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onPinchStart(int x, int y);
		/**
		 * Fires when a user completes a pinch action
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onPinchComplete(int x, int y);
		/**
		 * Fires when a user initiates a fling action
		 * @param sx (int) the x position of the start of the fling
		 * @param sy (int) the y position of the start of the fling
		 * @param dx (int) the x position of the end of the fling
		 * @param dy (int) the y position of the end of the fling
		 */
		public void onFling(int sx, int sy, int dx, int dy);
		/**
		 * Fires when a fling action has completed
		 * @param x (int) the final x scroll position of the TileView after the fling
		 * @param y (int) the final y scroll position of the TileView after the fling
		 */
		public void onFlingComplete(int x, int y);
		/**
		 * Fires when the TileView's scale has updated
		 * @param scale (double) the new scale of the TileView (0-1)
		 */
		public void onScaleChanged(double scale);
		/**
		 * Fires when the TileView's scroll position has updated
		 * @param x (int) the new x scroll position of the TileView
		 * @param y (int) the new y scroll position of the TileView
		 */
		public void onScrollChanged(int x, int y);
		/**
		 * Fires when a zoom action starts (typically through a pinch of double-tap action,
		 * or by programmatic animated zoom methods.
		 * @param scale (double) the new scale of the TileView (0-1)
		 */
		public void onZoomStart(double scale);
		/**
		 * Fires when a zoom action ends (typically through a pinch of double-tap action,
		 * or by programmatic animated zoom methods.
		 * @param scale (double) the new scale of the TileView (0-1)
		 */
		public void onZoomComplete(double scale);
		/**
		 * Fires when the TileView should start using a new DetailLevel
		 */
		public void onDetailLevelChanged();
		/**
		 * Fires when the rendering thread has started to update the visible tiles.
		 */
		public void onRenderStart();
		/**
		 * Fires when the rendering thread has completed updating the visible tiles, but before cleanup
		 */
		public void onRenderComplete();
	}
	
	/**
	 * Convenience class that implements {@TileViewEventListener}
	 */
	public static class TileViewEventListenerImplementation implements TileViewEventListener {

		/**
		 * Fires when a ACTION_DOWN event is raised from the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onFingerDown( int x, int y ) {
			
		}
		/**
		 * Fires when a ACTION_UP event is raised from the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onFingerUp( int x, int y ) {
			
		}
		/**
		 * Fires while the TileView is being dragged
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onDrag( int x, int y ) {
			
		}
		/**
		 * Fires when a user double-taps the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onDoubleTap( int x, int y ) {
			
		}
		/**
		 * Fires when a user taps the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onTap( int x, int y ) {
			
		}
		/**
		 * Fires while a user is pinching the TileView
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onPinch( int x, int y ) {
			
		}
		/**
		 * Fires when a user starts a pinch action
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onPinchStart( int x, int y ) {
			
		}
		/**
		 * Fires when a user completes a pinch action
		 * @param x (int) the x position of the event
		 * @param y (int) the y position of the event
		 */
		public void onPinchComplete( int x, int y ) {
			
		}
		/**
		 * Fires when a user initiates a fling action
		 * @param sx (int) the x position of the start of the fling
		 * @param sy (int) the y position of the start of the fling
		 * @param dx (int) the x position of the end of the fling
		 * @param dy (int) the y position of the end of the fling
		 */
		public void onFling( int sx, int sy, int dx, int dy ) {
			
		}
		/**
		 * Fires when a fling action has completed
		 * @param x (int) the final x scroll position of the TileView after the fling
		 * @param y (int) the final y scroll position of the TileView after the fling
		 */
		public void onFlingComplete( int x, int y ) {
			
		}
		/**
		 * Fires when the TileView's scale has updated
		 * @param scale (double) the new scale of the TileView (0-1)
		 */
		public void onScaleChanged( double scale ) {
			
		}
		/**
		 * Fires when the TileView's scroll position has updated
		 * @param x (int) the new x scroll position of the TileView
		 * @param y (int) the new y scroll position of the TileView
		 */
		public void onScrollChanged( int x, int y ) {
			
		}
		/**
		 * Fires when a zoom action starts (typically through a pinch of double-tap action,
		 * or by programmatic animated zoom methods.
		 * @param scale (double) the new scale of the TileView (0-1)
		 */
		public void onZoomStart( double scale ) {
			
		}
		/**
		 * Fires when a zoom action ends (typically through a pinch of double-tap action,
		 * or by programmatic animated zoom methods.
		 * @param scale (double) the new scale of the TileView (0-1)
		 */
		public void onZoomComplete( double scale ) {
			
		}
		/**
		 * Fires when the TileView should start using a new DetailLevel
		 * @param oldZoom (int) the zoom level the TileView was using before the change
		 * @param currentZoom (int) the zoom level the TileView has changed to
		 */
		public void onDetailLevelChanged() {
			
		}
		/**
		 * Fires when the rendering thread has started to update the visible tiles.
		 */
		public void onRenderStart() {
			
		}
		/**
		 * Fires when the rendering thread has completed updating the visible tiles, but before cleanup
		 */
		public void onRenderComplete() {
			
		}
		
	}

    public PositionManager getPositionManager() {
        return positionManager;
    }

    public PathManager getPathManager() {
        return pathManager;
    }
	
}