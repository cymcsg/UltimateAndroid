package com.marshalchen.common.uimodule.tileView.tileview.tiles.selector;

import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevel;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelSet;

public class TileSetSelectorClosest implements TileSetSelector {

	@Override
	public DetailLevel find( double scale, DetailLevelSet levels ) {
		// fast-fail
		if ( levels.size() == 0 ) {
			return null;
		}

		// default to first item
		DetailLevel match = null;
		double diffToMatch = 0d;

		// loop through and find the "closest"
		for ( final DetailLevel thisLevel : levels ) {
			if ( match == null ) {
				// default to the first value found
				match = thisLevel;
				diffToMatch = Math.abs( scale - match.getScale() );
				continue;
			}

			// calculate the diff from current level to requested scale
			final double diffToCurrent = Math.abs( scale - thisLevel.getScale() );
			if ( diffToCurrent < diffToMatch ) {
				match = thisLevel;
				diffToMatch = diffToCurrent;
			}
		}

		return match;
	}

}
