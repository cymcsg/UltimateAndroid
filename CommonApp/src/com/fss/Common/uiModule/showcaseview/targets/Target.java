package com.fss.common.uiModule.showcaseview.targets;

import android.graphics.Point;

public interface Target {
    Target NONE = new Target() {
        @Override
        public Point getPoint() {
            return new Point(1000000, 1000000);
        }
    };

    public Point getPoint();
}
