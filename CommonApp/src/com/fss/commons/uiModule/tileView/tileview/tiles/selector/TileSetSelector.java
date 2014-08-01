package com.fss.commons.uiModule.tileView.tileview.tiles.selector;

import com.fss.commons.uiModule.tileView.tileview.detail.DetailLevel;
import com.fss.commons.uiModule.tileView.tileview.detail.DetailLevelSet;

public interface TileSetSelector {
    public DetailLevel find(double scale, DetailLevelSet levels);
}
