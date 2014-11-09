package com.marshalchen.common.uimodule.foldablelayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

/**
 * View that provides ability to switch between 2 diffrent views (cover view & details view) with fold animation.
 * <p/>
 * It is implemented as subclass of FoldableListLayout with only 2 views to scroll between.
 */
public class UnfoldableView extends FoldableListLayout {

    private View mDefaultDetailsPlaceHolderView;
    private View mDefaultCoverPlaceHolderView;

    private View mDetailsView, mCoverView;
    private View mDetailsPlaceHolderView, mCoverPlaceHolderView;
    private CoverHolderLayout mCoverHolderLayout;

    private View mScheduledCoverView, mScheduledDetailsView;

    private ViewGroup.LayoutParams mDetailsViewParams, mCoverViewParams;
    private int mDetailsViewParamWidth, mDetailsViewParamHeight, mCoverViewParamWidth, mCoverViewParamHeight;
    private Rect mCoverViewPosition, mDetailsViewPosition;

    private Adapter mAdapter;

    private float mLastFoldRotation;
    private boolean mIsUnfolding;
    private boolean mIsFoldingBack;
    private boolean mIsUnfolded;

    private OnFoldingListener mListener;

    public UnfoldableView(Context context) {
        super(context);
        init(context);
    }

    public UnfoldableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UnfoldableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mCoverHolderLayout = new CoverHolderLayout(context);
        mDefaultDetailsPlaceHolderView = new View(context);
        mDefaultCoverPlaceHolderView = new View(context);
        mAdapter = new Adapter();
    }

    public void setOnFoldingListener(OnFoldingListener listener) {
        mListener = listener;
    }

    private void setDetailsViewInternal(View detailsView) {
        // saving details view data
        mDetailsView = detailsView;
        mDetailsViewParams = detailsView.getLayoutParams();
        mDetailsViewParamWidth = mDetailsViewParams.width;
        mDetailsViewParamHeight = mDetailsViewParams.height;

        // getting details view positions on screen
        mDetailsViewPosition = getViewGlobalPosition(detailsView);

        // creating placeholder to show in place of details view
        mDetailsPlaceHolderView = createDetailsPlaceHolderView();

        // setting precise width/height params and switching details view with it's placeholder
        mDetailsViewParams.width = mDetailsViewPosition.width();
        mDetailsViewParams.height = mDetailsViewPosition.height();
        switchViews(detailsView, mDetailsPlaceHolderView, mDetailsViewParams);
    }

    private void clearDetailsViewInternal() {
        if (mDetailsView == null) return; // nothing to do

        // restoring original width/height params and adding cover view back to it's place
        mDetailsViewParams.width = mDetailsViewParamWidth;
        mDetailsViewParams.height = mDetailsViewParamHeight;
        switchViews(mDetailsPlaceHolderView, mDetailsView, mDetailsViewParams);

        // clearing references
        mDetailsView = null;
        mDetailsViewParams = null;
        mDetailsViewPosition = null;
        mDetailsPlaceHolderView = null;
    }

    private void setCoverViewInternal(View coverView) {
        // saving cover view data
        mCoverView = coverView;
        mCoverViewParams = coverView.getLayoutParams();
        mCoverViewParamWidth = mCoverViewParams.width;
        mCoverViewParamHeight = mCoverViewParams.height;

        // getting cover view positions on screen
        mCoverViewPosition = getViewGlobalPosition(coverView);

        // creating placeholder to show in place of cover view
        mCoverPlaceHolderView = createCoverPlaceHolderView();

        // setting precise width/height params and switching cover view with it's placeholder
        mCoverViewParams.width = mCoverViewPosition.width();
        mCoverViewParams.height = mCoverViewPosition.height();
        switchViews(coverView, mCoverPlaceHolderView, mCoverViewParams);

        // moving cover view into special cover view holder (for unfold animation)
        mCoverHolderLayout.setView(coverView, mCoverViewPosition.width(), mCoverViewPosition.height());
    }

    private void clearCoverViewInternal() {
        if (mCoverView == null) return; // nothing to do

        // freeing coverView so we can add it back to it's palce
        mCoverHolderLayout.clearView();

        // restoring original width/height params and adding cover view back to it's place
        mCoverViewParams.width = mCoverViewParamWidth;
        mCoverViewParams.height = mCoverViewParamHeight;
        switchViews(mCoverPlaceHolderView, mCoverView, mCoverViewParams);

        // clearing references
        mCoverView = null;
        mCoverViewParams = null;
        mCoverViewPosition = null;
        mCoverPlaceHolderView = null;
    }

    public void changeCoverView(View coverView) {
        if (mCoverView == null || mCoverView == coverView) return; // nothing to do
        clearCoverViewInternal();
        setCoverViewInternal(coverView);
    }

    protected View createDetailsPlaceHolderView() {
        return mDefaultDetailsPlaceHolderView;
    }

    protected View createCoverPlaceHolderView() {
        return mDefaultCoverPlaceHolderView;
    }

    /**
     * Starting unfold animation for given views
     */
    public void unfold(View coverView, View detailsView) {
        if (mCoverView == coverView && mDetailsView == detailsView) return; // already in place

        if ((mCoverView != null && mCoverView != coverView) || (mDetailsView != null && mDetailsView != detailsView)) {
            // cover or details view is differ - closing details and schedule reopening
            mScheduledDetailsView = detailsView;
            mScheduledCoverView = coverView;
            foldBack();
            return;
        }

        setCoverViewInternal(coverView);
        setDetailsViewInternal(detailsView);

        // initializing foldable views
        setAdapter(mAdapter);

        // starting unfold animation
        scrollToPosition(1);
    }

    public void foldBack() {
        scrollToPosition(0);
    }

    private void onFoldedBack() {
        // clearing all foldable views
        setAdapter(null);

        clearCoverViewInternal();
        clearDetailsViewInternal();

        // clearing translations
        setTranslationX(0);
        setTranslationY(0);

        if (mScheduledCoverView != null && mScheduledDetailsView != null) {
            View scheduledDetails = mScheduledDetailsView;
            View scheduledCover = mScheduledCoverView;
            mScheduledDetailsView = mScheduledCoverView = null;
            unfold(scheduledCover, scheduledDetails);
        }
    }

    public boolean isUnfolding() {
        return mIsUnfolding;
    }

    public boolean isFoldingBack() {
        return mIsFoldingBack;
    }

    public boolean isUnfolded() {
        return mIsUnfolded;
    }

    @Override
    public void setFoldRotation(float rotation, boolean isFromUser) {
        super.setFoldRotation(rotation, isFromUser);
        if (mCoverView == null || mDetailsView == null) return; // nothing we can do here

        rotation = getFoldRotation(); // parent view will correctly keep rotation in bounds for us

        // translating from cover's position to details position
        float stage = rotation / 180; // from 0 = only cover view, to 1 - only details view

        float fromX = mCoverViewPosition.centerX();
        float toX = mDetailsViewPosition.centerX();

        float fromY = mCoverViewPosition.top;
        float toY = mDetailsViewPosition.centerY();

        setTranslationX((fromX - toX) * (1 - stage));
        setTranslationY((fromY - toY) * (1 - stage));

        // tracking states

        float lastRotatation = mLastFoldRotation;
        mLastFoldRotation = rotation;

        if (mListener != null) mListener.onFoldProgress(this, stage);

        if (rotation > lastRotatation && !mIsUnfolding) {
            mIsUnfolding = true;
            mIsFoldingBack = false;
            mIsUnfolded = false;

            if (mListener != null) mListener.onUnfolding(this);
        }

        if (rotation < lastRotatation && !mIsFoldingBack) {
            mIsUnfolding = false;
            mIsFoldingBack = true;
            mIsUnfolded = false;

            if (mListener != null) mListener.onFoldingBack(this);
        }

        if (rotation == 180 && !mIsUnfolded) {
            mIsUnfolding = false;
            mIsFoldingBack = false;
            mIsUnfolded = true;

            if (mListener != null) mListener.onUnfolded(this);
        }

        if (rotation == 0 && mIsFoldingBack) {
            mIsUnfolding = false;
            mIsFoldingBack = false;
            mIsUnfolded = false;

            onFoldedBack();
            if (mListener != null) mListener.onFoldedBack(this);
        }
    }

    @Override
    protected void onFoldRotationChanged(FoldableItemLayout layout, int position) {
        super.onFoldRotationChanged(layout, position);

        float stage = getFoldRotation() / 180; // from 0 = only cover view, to 1 - only details view

        float coverW = mCoverViewPosition.width();
        float detailsW = mDetailsViewPosition.width();

        if (position == 0) { // cover view
            // scaling cover view from origin size to the size (width) of the details view
            float coverScale = 1 - (1 - detailsW / coverW) * stage;
            layout.setScale(coverScale);
        } else { // details view
            // scaling details view from cover's size to the original size
            float detailsScale = 1 - (1 - coverW / detailsW) * (1 - stage);
            layout.setScale(detailsScale);

            float dH = mDetailsViewPosition.height() / 2 - mCoverViewPosition.height() * detailsW / coverW;
            float translationY = stage < 0.5f ? -dH * (1 - 2 * stage) : 0;

            layout.setRollingDistance(translationY);
        }
    }

    private void switchViews(View origin, View replacement, ViewGroup.LayoutParams params) {
        ViewGroup parent = (ViewGroup) origin.getParent();

        if (params == null) params = origin.getLayoutParams();

        int index = parent.indexOfChild(origin);
        parent.removeViewAt(index);
        parent.addView(replacement, index, params);
    }

    private Rect getViewGlobalPosition(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Rect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
    }


    /**
     * Simple adapter that will alternate between cover view holder layout and details layout
     */
    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View recycledView, ViewGroup parent) {
            return i == 0 ? mCoverHolderLayout : mDetailsView;
        }
    }


    /**
     * Cover view holder layout. It can contain at most one child which will be positioned in the top|center_horisontal
     * location of bottom half of the view.
     */
    private static class CoverHolderLayout extends FrameLayout {

        private final Rect mVisibleBounds = new Rect();

        private CoverHolderLayout(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int h = getMeasuredHeight();
            setPadding(0, h / 2, 0, 0);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            // Collecting visible bounds of child view, it will be used to correctly draw shadows and to
            // improve drawing performance
            View view = getView();
            if (view != null) {
                mVisibleBounds.set(view.getLeft(), view.getTop(),
                        view.getLeft() + view.getWidth(), view.getTop() + view.getHeight());

                FoldableItemLayout foldableLayout = findParentFoldableLayout();
                if (foldableLayout != null) foldableLayout.setLayoutVisibleBounds(mVisibleBounds);
            } else {
                mVisibleBounds.set(0, 0, 0, 0);
            }
        }

        private FoldableItemLayout findParentFoldableLayout() {
            ViewGroup parent = this;
            while (parent != null) {
                parent = (ViewGroup) parent.getParent();
                if (parent instanceof FoldableItemLayout) {
                    return (FoldableItemLayout) parent;
                }
            }
            return null;
        }

        private void setView(View view, int w, int h) {
            removeAllViews();
            LayoutParams params = new LayoutParams(w, h, Gravity.CENTER_HORIZONTAL);
            addView(view, params);
        }

        private View getView() {
            return getChildCount() > 0 ? getChildAt(0) : null;
        }

        private void clearView() {
            removeAllViews();
        }

    }

    public interface OnFoldingListener {
        void onUnfolding(UnfoldableView unfoldableView);

        void onUnfolded(UnfoldableView unfoldableView);

        void onFoldingBack(UnfoldableView unfoldableView);

        void onFoldedBack(UnfoldableView unfoldableView);

        void onFoldProgress(UnfoldableView unfoldableView, float progress);
    }

    public static class SimpleFoldingListener implements OnFoldingListener {
        @Override
        public void onUnfolding(UnfoldableView unfoldableView) {
        }

        @Override
        public void onUnfolded(UnfoldableView unfoldableView) {
        }

        @Override
        public void onFoldingBack(UnfoldableView unfoldableView) {
        }

        @Override
        public void onFoldedBack(UnfoldableView unfoldableView) {
        }

        @Override
        public void onFoldProgress(UnfoldableView unfoldableView, float progress) {
        }
    }

}
