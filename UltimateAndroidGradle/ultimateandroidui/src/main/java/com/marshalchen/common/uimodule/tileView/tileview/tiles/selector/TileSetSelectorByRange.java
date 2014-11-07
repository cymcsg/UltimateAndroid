package com.marshalchen.common.uimodule.tileView.tileview.tiles.selector;

import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevel;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelSet;

import java.util.ArrayList;
import java.util.List;

public class TileSetSelectorByRange implements TileSetSelector {

	private List<Double> switchPoint = new ArrayList<Double>();

	@Override
	public DetailLevel find( double scale, DetailLevelSet levels ) {
		int totalLevels = levels.size();
		int totalSwitches = switchPoint.size();

		// fast-fail
		if ( totalLevels == 0 ) {
			return null;
		}

		// sanity check the switchPoints with the levels
		// switchPoints should be 1 less then the total levels
		if ( totalLevels != ( totalSwitches + 1 ) ) {
			return null;
		}

		// loop through and find a set where this scale fits
		for ( int index = 0; index < totalSwitches; index++ ) {
			double thisSwitchPoint = this.switchPoint.get( index );

			// when we exceed the scale we take the previous
			if ( scale < thisSwitchPoint ) {
				return levels.get( index );
			}
		}

		// take the last
		return levels.get( totalLevels - 1 );
	}

	public void add( final double value ) {
		switchPoint.add( value );
	}
}
