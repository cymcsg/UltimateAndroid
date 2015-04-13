package com.marshalchen.common.uimodule.listbuddies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * ListView that notify the through a listener about the Y distance that was scrolled.
 */
public class ObservableListView extends ListView {

    private static final String TAG = ObservableListView.class.getSimpleName();
    /**
     * Delegate for the callback to the fragment/activity that the ListView is in
     */
    private ListViewObserverDelegate mObserver;

    private View mTrackedChild;
    private int mTrackedChildPrevPosition;
    private int mTrackedChildPrevTop;

    public ObservableListView(Context context) {
        super(context);
    }

    public ObservableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static interface ListViewObserverDelegate {
        public void onListScroll(View view, float deltaY);
    }


    private View getChildInTheMiddle() {
        return getChildAt(getChildCount() / 2);
    }

    public void setObserver(ListViewObserverDelegate observer) {
        mObserver = observer;
    }

    /**
     * Calculate the scroll distance comparing the distance with the top of the list of the current
     * child and the last one tracked
     *
     * @param l - Current horizontal scroll origin.
     * @param t - Current vertical scroll origin.
     * @param oldl - Previous horizontal scroll origin.
     * @param oldt - Previous vertical scroll origin.
     */
    private float OldDeltaY;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);


        if (mTrackedChild == null) {

            //We want to continue scrolling the list when we don't find a valid child
            // so we use the last value of deltaY
            mObserver.onListScroll(this, OldDeltaY);

            if (getChildCount() > 0) {
                mTrackedChild = getChildInTheMiddle();
                mTrackedChildPrevTop = mTrackedChild.getTop();
                mTrackedChildPrevPosition = getPositionForView(mTrackedChild);
            }
        } else {

            boolean childIsSafeToTrack = mTrackedChild.getParent() == this && getPositionForView(mTrackedChild) == mTrackedChildPrevPosition;
            if (childIsSafeToTrack) {
                int top = mTrackedChild.getTop();
                if (mObserver != null) {
                    float deltaY = top - mTrackedChildPrevTop;

                    if (deltaY == 0) {
                        //When we scroll so fast the list this value becomes 0 all the time
                        // so we don't want the other list stop, and we give it the last
                        //no 0 value we have
                        deltaY = OldDeltaY;
                    } else {
                        OldDeltaY = deltaY;
                    }

                    mObserver.onListScroll(this, deltaY);

                }
                mTrackedChildPrevTop = top;
            } else {
                mTrackedChild = null;
            }
        }


    }
}
