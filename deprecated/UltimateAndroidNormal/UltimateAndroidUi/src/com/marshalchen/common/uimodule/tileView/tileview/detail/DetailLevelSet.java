package com.marshalchen.common.uimodule.tileView.tileview.detail;

import com.marshalchen.common.uimodule.tileView.tileview.tiles.selector.TileSetSelector;
import com.marshalchen.common.uimodule.tileView.tileview.tiles.selector.TileSetSelectorMinimalUpScale;

import java.util.Collections;
import java.util.LinkedList;

/*
 * This is termed "Set" while it's actually a list.
 * We need a unique, sorted collection (Set), but must
 * support frequent use of get().  NavigableSet is not
 * an option for the legacy API's we're supporting.
 * For now, use a LinkedList with Set-like behavior
 * built in.
 */

public class DetailLevelSet extends LinkedList<DetailLevel> {

	private static final long serialVersionUID = -1742428277010988084L;

	private TileSetSelector tileSetSelector = new TileSetSelectorMinimalUpScale();

	public void addDetailLevel( DetailLevel detailLevel ) {
		// ensure uniqueness
		if ( contains( detailLevel ) ) {
			return;
		}
		// add to the collection
		add( detailLevel );
		// sort it
		Collections.sort( this );
	}

	public DetailLevel find( double scale ) {
		return tileSetSelector.find( scale, this );
	}

	public TileSetSelector getTileSetSelector() {
		return tileSetSelector;
	}

	/**
	 * Set the tile selection method, defaults to {@link TileSetSelectorMinimalUpScale}
	 * @param selector (TileSetSelector)
	 */
	public void setTileSetSelector( TileSetSelector selector ) {
		tileSetSelector = selector;
	}

}
