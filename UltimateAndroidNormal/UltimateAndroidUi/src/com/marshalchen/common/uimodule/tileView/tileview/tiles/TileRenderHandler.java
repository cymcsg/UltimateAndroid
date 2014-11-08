package com.marshalchen.common.uimodule.tileView.tileview.tiles;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class TileRenderHandler extends Handler {

	private final WeakReference<TileManager> reference;
	public TileRenderHandler( TileManager tm ) {
		super();
		reference = new WeakReference<TileManager>( tm );
	}
	@Override
	public final void handleMessage( Message message ) {
		final TileManager tileManager = reference.get();
		if ( tileManager != null ) {
			tileManager.renderTiles();
		}
	}
}
