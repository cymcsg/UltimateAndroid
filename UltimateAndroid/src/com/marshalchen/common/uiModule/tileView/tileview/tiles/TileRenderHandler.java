package com.marshalchen.common.uiModule.tileView.tileview.tiles;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

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
