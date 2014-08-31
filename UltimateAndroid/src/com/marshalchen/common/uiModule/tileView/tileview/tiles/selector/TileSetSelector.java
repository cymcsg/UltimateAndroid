package com.marshalchen.common.uiModule.tileView.tileview.tiles.selector;

import com.marshalchen.common.uiModule.tileView.tileview.detail.DetailLevel;
import com.marshalchen.common.uiModule.tileView.tileview.detail.DetailLevelSet;

public interface TileSetSelector {
    public DetailLevel find(double scale, DetailLevelSet levels);
}
