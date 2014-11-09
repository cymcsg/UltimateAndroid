package com.marshalchen.common.uimodule.tileView.tileview.tiles;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import com.marshalchen.common.uimodule.tileView.layouts.FixedLayout;
import com.marshalchen.common.uimodule.tileView.layouts.ScalingLayout;
import com.marshalchen.common.uimodule.tileView.os.AsyncTask;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevel;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailManager;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoder;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoderAssets;

import java.util.HashMap;
import java.util.LinkedList;

public class TileManager extends ScalingLayout implements DetailLevelEventListener {

	private static final int RENDER_FLAG = 1;
	private static final int RENDER_BUFFER = 250;
	
	private static final int TRANSITION_DURATION = 200;

	private LinkedList<Tile> scheduledToRender = new LinkedList<Tile>();
	private LinkedList<Tile> alreadyRendered = new LinkedList<Tile>();

	private BitmapDecoder decoder = new BitmapDecoderAssets();
	private HashMap<Double, ScalingLayout> tileGroups = new HashMap<Double, ScalingLayout>();

	private TileCache cache;
	private DetailLevel detailLevelToRender;
	private DetailLevel lastRenderedDetailLevel;
	private TileRenderTask lastRunRenderTask;
	private ScalingLayout currentTileGroup;
	private DetailManager detailManager;

	private boolean renderIsCancelled = false;
	private boolean renderIsSuppressed = false;
	private boolean isRendering = false;
	
	private boolean transitionsEnabled = true;
	private int transitionDuration = TRANSITION_DURATION;
	
	private TileRenderHandler handler;
	private TileRenderListener renderListener;
	private TileTransitionListener transitionListener;

	public TileManager( Context context, DetailManager zm ) {
		super( context );
		detailManager = zm;
		detailManager.addDetailLevelEventListener( this );		
		handler = new TileRenderHandler( this );
		transitionListener = new TileTransitionListener( this );
	}
	
	public void setTransitionsEnabled( boolean enabled ) {
		transitionsEnabled = enabled;
	}
	
	public void setTransitionDuration( int duration ) {
		transitionDuration = duration;
	}
	
	public void setDecoder( BitmapDecoder d ){
		decoder = d;
	}
	
	public void setCacheEnabled( boolean shouldCache ) {
		if ( shouldCache ){
			if ( cache == null ){
				cache = new TileCache( getContext() );
			}
		} else {
			if ( cache != null ) {
				cache.destroy();
			}
			cache = null;
		}
	}
	
	public void setTileRenderListener( TileRenderListener listener ){
		renderListener = listener;
	}

	public void requestRender() {
		// if we're requesting it, we must really want one
		renderIsCancelled = false;
		renderIsSuppressed = false;
		// if there's no data about the current detail level, don't bother
		if ( detailLevelToRender == null ) {
			return;
		}
		// throttle requests
		if ( handler.hasMessages( RENDER_FLAG ) ) {
			handler.removeMessages( RENDER_FLAG );
		}
		// give it enough buffer that (generally) successive calls will be captured
		handler.sendEmptyMessageDelayed( RENDER_FLAG, RENDER_BUFFER );
	}

	public void cancelRender() {
		// hard cancel - further render tasks won't start, and we'll attempt to interrupt the currently executing task
		renderIsCancelled = true;
		// if the currently executing task isn't null...
		if ( lastRunRenderTask != null ) {
			// ... and it's in a cancellable state
			if ( lastRunRenderTask.getStatus() != AsyncTask.Status.FINISHED ) {
				// ... then squash it
				lastRunRenderTask.cancel( true );
			}
		}
		// give it to gc
		lastRunRenderTask = null;
	}

	public void suppressRender() {
		// this will prevent new tasks from starting, but won't actually cancel the currently executing task
		renderIsSuppressed = true;
	}
	
	public void updateTileSet() {
		// grab reference to this detail level, so we can get it's tile set for comparison to viewport
		detailLevelToRender = detailManager.getCurrentDetailLevel();
		// fast-fail if it's null
		if(detailLevelToRender == null){
			return;
		}
		// fast-fail if there's no change (same tile set)
		if( detailLevelToRender.equals( lastRenderedDetailLevel ) ) {
			return;
		}
		// we made it this far, cache the new level to test for changes on next invocation
		lastRenderedDetailLevel = detailLevelToRender;
		// fetch appropriate child
		currentTileGroup = getCurrentTileGroup();
		// show it
		currentTileGroup.setVisibility( View.VISIBLE );
		// bring it to top of stack
		currentTileGroup.bringToFront();
	}

	public boolean getIsRendering() {
		return isRendering;
	}
	
	public void clear() {
		// suppress and cancel renders
		suppressRender();
		cancelRender();		
		// destroy all tiles
		for ( Tile m : scheduledToRender ) {
			m.destroy();
		}
		scheduledToRender.clear();
		for ( Tile m : alreadyRendered ) {
			m.destroy();
		}
		alreadyRendered.clear();
		// the above should clear everything, but let's be redundant
		for ( ScalingLayout tileGroup : tileGroups.values() ) {
			int totalChildren = tileGroup.getChildCount();
			for ( int i = 0; i < totalChildren; i++ ) {
				View child = tileGroup.getChildAt( i );
				if ( child instanceof ImageView ) {
					ImageView imageView = (ImageView) child;
					imageView.setImageBitmap( null );
				}
			}
			tileGroup.removeAllViews();
		}
		// clear the cache
		if ( cache != null ) {
			cache.clear();
		}
	}

	private ScalingLayout getCurrentTileGroup() {
		// get the registered scale for the active detail level
		double levelScale = detailManager.getCurrentDetailLevelScale();
		// if a tile group has already been created and registered...
		if ( tileGroups.containsKey( levelScale ) ) {
			// ... we're done.  return cached level.
			return tileGroups.get( levelScale );
		}
		// otherwise create one
		ScalingLayout tileGroup = new ScalingLayout( getContext() );
		// scale it to the inverse of the levels scale (so 0.25 levels are shown at 400%)
		tileGroup.setScale( 1 / levelScale );
		// register it scale (key) for re-use
		tileGroups.put( levelScale, tileGroup );
		// add it to the view tree
		// MATCH_PARENT should work here but doesn't, roll back if reverting to FrameLayout
		addView( tileGroup, new LayoutParams( detailManager.getWidth(), detailManager.getHeight() ) );
		// send it off
		return tileGroup;
	}

	

	// access omitted deliberately - need package level access for the TileRenderHandler
	void renderTiles() {
		// has it been canceled since it was requested?
		if ( renderIsCancelled ) {
			return;
		}
		// can we keep rending existing tasks, but not start new ones?
		if ( renderIsSuppressed ) {
			return;
		}
		// fast-fail if there's no available data
		if ( detailLevelToRender == null ) {
			return;
		}
		// decode and render the bitmaps asynchronously
		beginRenderTask();
	}

	private void beginRenderTask() {
		// find all matching tiles
		LinkedList<Tile> intersections = detailLevelToRender.getIntersections();
		// if it's the same list, don't bother
		if ( scheduledToRender.equals( intersections ) ) {
			return;
		}
		// if we made it here, then replace the old list with the new list
		scheduledToRender = intersections;
		// cancel task if it's already running
		if ( lastRunRenderTask != null ) {
			if ( lastRunRenderTask.getStatus() != AsyncTask.Status.FINISHED ) {
				lastRunRenderTask.cancel( true );
			}
		}
		// start a new one
		lastRunRenderTask = new TileRenderTask( this );
		lastRunRenderTask.execute();
	}

	private FixedLayout.LayoutParams getLayoutFromTile( Tile m ) {
		int w = m.getWidth();
		int h = m.getHeight();
		int x = m.getLeft();
		int y = m.getTop();
		return new FixedLayout.LayoutParams( w, h, x, y );
	}

	private void cleanup() {
		// start with all rendered tiles...
		LinkedList<Tile> condemned = new LinkedList<Tile>( alreadyRendered );
		// now remove all those that were just qualified
		condemned.removeAll( scheduledToRender );
		// for whatever's left, destroy and remove from list
		for ( Tile m : condemned ) {
			m.destroy();
			alreadyRendered.remove( m );
		}
		// hide all other groups
		for ( ScalingLayout tileGroup : tileGroups.values() ) {
			if ( currentTileGroup == tileGroup ) {
				continue;
			}
			tileGroup.setVisibility( View.GONE );
		}
	}

	/*
	 *  render tasks (invoked in asynctask's thread)
	 */
	
	void onRenderTaskPreExecute(){
		// set a flag that we're working
		isRendering = true;
		// notify anybody interested
		if ( renderListener != null ) {
			renderListener.onRenderStart();
		}
	}
	
	void onRenderTaskCancelled() {
		if ( renderListener != null ) {
			renderListener.onRenderCancelled();
		}
		isRendering = false;
	}
	
	void onRenderTaskPostExecute() {
		// set flag that we're done
		isRendering = false;
		// everything's been rendered, so get rid of the old tiles
		cleanup();
		// recurse - request another round of render - if the same intersections are discovered, recursion will end anyways
		requestRender();
		// notify anybody interested
		if ( renderListener != null ) {
			renderListener.onRenderComplete();
		}
	}
	
	LinkedList<Tile> getRenderList(){
		return new LinkedList<Tile>( scheduledToRender );
	}
	
	// package level access so it can be invoked by the render task
	void decodeIndividualTile( Tile m ) {
		m.decode( getContext(), cache, decoder );
	}

	// package level access so it can be invoked by the render task
	void renderIndividualTile( Tile tile ) {
		// if it's already rendered, quit now
		if ( alreadyRendered.contains( tile ) ) {
			return;
		}
		// create the image view if needed, with default settings
		tile.render( getContext() );
		// add it to the list of those rendered
		alreadyRendered.add( tile );
		// get reference to the actual image view
		ImageView imageView = tile.getImageView();
		// get layout params from the tile's predefined dimensions
		LayoutParams layoutParams = getLayoutFromTile( tile );
		// add it to the appropriate set (which is already scaled)
		currentTileGroup.addView( imageView, layoutParams );
		// shouldn't be necessary, but is
		postInvalidate();
		// do we want to animate in tiles?
		if( transitionsEnabled){
			// do we have an appropriate duration?
			if( transitionDuration > 0 ) {
				// create the animation (will be cleared by tile.destroy).  do this here for the postInvalidate listener
				AlphaAnimation fadeIn = new AlphaAnimation( 0f, 1f );
				// set duration
				fadeIn.setDuration( transitionDuration );
				// this listener posts invalidate on complete, again should not be necessary but is
				fadeIn.setAnimationListener( transitionListener );
				// start it up
				imageView.startAnimation( fadeIn );
			}
		}
	}
	
	boolean getRenderIsCancelled() {
		return renderIsCancelled;
	}
	
	// TODO: instead of implements, use a member?
	@Override
	public void onDetailLevelChanged() {
		updateTileSet();
	}

	@Override
	public void onDetailScaleChanged( double scale ) {
		setScale( scale );
	}

}
