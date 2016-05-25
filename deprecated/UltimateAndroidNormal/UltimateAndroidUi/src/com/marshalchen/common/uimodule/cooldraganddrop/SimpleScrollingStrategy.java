package com.marshalchen.common.uimodule.cooldraganddrop;

import android.widget.ScrollView;

public class SimpleScrollingStrategy implements ScrollingStrategy {

	ScrollView mScrollViewContainer;

	public SimpleScrollingStrategy(ScrollView scrollViewContainer) {

		mScrollViewContainer = scrollViewContainer;

	}

	@Override
	public boolean performScrolling(final int x, final int y, final CoolDragAndDropGridView view) {

		if (mScrollViewContainer != null) {

			int scrollY = mScrollViewContainer.getScrollY();
			int delta = scrollY - view.getTop();
			int maxDelta = Math.max(delta, 0);

			int dy = y - delta;

			int height = view.getHeight();
			int containerHeight = mScrollViewContainer.getHeight();

			int topThresshold = containerHeight / 10;
			int bottomThresshold = 9 * containerHeight / 10;

			if ((dy < topThresshold) && (maxDelta > 0)) {

				mScrollViewContainer.scrollBy(0, -topThresshold / 8);
				return true;

			} else if ((dy > bottomThresshold) && ((delta + containerHeight) < height)) {

				mScrollViewContainer.scrollBy(0, topThresshold / 8);
				return true;
			}
		}

		return false;
	}

}
