package com.marshalchen.common.uimodule.tileView.tileview.tiles.selector;

import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevel;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelSet;

public class TileSetSelectorMinimalUpScale implements TileSetSelector {

	@Override
	public DetailLevel find( double scale, DetailLevelSet levels ) {
		// fast-fail
		if ( levels.size() == 0 ) {
			return null;
		}
		// if there's just one level, that's our best-case
		if( levels.size() == 1 ) {
			return levels.get( 0 );
		}
		// set to null initially, but should never fail to populate
		DetailLevel match = null;
		// start at the last index
		int index = levels.size() - 1;
		// loop from largest to smallest
		for ( int i = index; i >= 0; i-- ) {
			// store the iteration level in the return product for now
			match = levels.get( i );
			// if the iteration scale is less than the desired scale...
			if ( match.getScale() < scale ) {
				// and there's a level registered with a larger scale
				if ( i < index ) {
					// ... try to get the next largest
					match = levels.get( i + 1 );
					// if we're at the largest level and can't go up one, then we've got our best-case
				}
				// we've got a match, all done
				break;
			}
		}
		return match;
	}

}
