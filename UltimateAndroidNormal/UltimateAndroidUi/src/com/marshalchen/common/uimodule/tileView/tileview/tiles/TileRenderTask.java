package com.marshalchen.common.uimodule.tileView.tileview.tiles;

import com.marshalchen.common.uimodule.tileView.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.LinkedList;


class TileRenderTask extends AsyncTask<Void, Tile, Void> {

	private final WeakReference<TileManager> reference;
	
	// package level access
	TileRenderTask( TileManager tm ) {
		super();
		reference = new WeakReference<TileManager>( tm );
	}
	
	@Override
	protected void onPreExecute() {
		final TileManager tileManager = reference.get();
		if ( tileManager != null ) {
			tileManager.onRenderTaskPreExecute();
		}		
	}

	@Override
	protected Void doInBackground( Void... params ) {
		// have we been stopped or dereffed?
		TileManager tileManager = reference.get();
		// if not go ahead, but check again in each iteration
		if ( tileManager != null ) {
			// avoid concurrent modification exceptions by duplicating
			LinkedList<Tile> renderList = tileManager.getRenderList();
			// start rendering, checking each iteration if we need to break out
			for ( Tile m : renderList ) {
				// check again if we've been stopped or gc'ed
				tileManager = reference.get();
				if ( tileManager == null ) {
					return null;
				}
				// quit if we've been forcibly stopped
				if ( tileManager.getRenderIsCancelled() ) {
					return null;
				}
				// quit if task has been cancelled or replaced
				if ( isCancelled() ) {
					return null;
				}
				// once the bitmap is decoded, the heavy lift is done
				tileManager.decodeIndividualTile( m );
				// pass it to the UI thread for insertion into the view tree
				publishProgress( m );
			}
			
		}		
		return null;
	}

	@Override
	protected void onProgressUpdate( Tile... params ) {
		// have we been stopped or dereffed?
		TileManager tileManager = reference.get();
		// if not go ahead but check other cancel states
		if ( tileManager != null ) {
			// quit if it's been force-stopped
			if ( tileManager.getRenderIsCancelled() ) {
				return;
			}
			// quit if it's been stopped or replaced by a new task
			if ( isCancelled() ) {
				return;
			}
			// tile should already have bitmap decoded
			Tile m = params[0];
			// add the bitmap to it's view, add the view to the current detail level layout
			tileManager.renderIndividualTile( m );
		}
		
	}

	@Override
	protected void onPostExecute( Void param ) {
		// have we been stopped or dereffed?
		TileManager tileManager = reference.get();
		// if not go ahead but check other cancel states
		if ( tileManager != null ) {
			tileManager.onRenderTaskPostExecute();
		}
	}

	@Override
	protected void onCancelled() {
		// have we been stopped or dereffed?
		TileManager tileManager = reference.get();
		// if not go ahead but check other cancel states
		if ( tileManager != null ) {
			tileManager.onRenderTaskCancelled();
		}
	}

}