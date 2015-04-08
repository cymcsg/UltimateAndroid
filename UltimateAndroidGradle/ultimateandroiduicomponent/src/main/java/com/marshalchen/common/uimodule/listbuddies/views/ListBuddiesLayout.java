package com.marshalchen.common.uimodule.listbuddies.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.uimodule.listbuddies.adapters.CircularLoopAdapter;
import com.marshalchen.common.uimodule.listbuddies.helpers.ListBuddiesAutoScrollHelper;
import com.marshalchen.common.uimodule.listbuddies.provider.ScrollConfigOptions;
import com.marshalchen.ultimateandroiduicomponent.R;

/**
 * LinerLayout that contains 2 ListViews. This ListViews auto-scroll while the user is not interacting with them.
 * When the user interact with one of the ListViews a parrallax effect is created during its scroll.
 */
public class ListBuddiesLayout extends LinearLayout implements View.OnTouchListener, ObservableListView.ListViewObserverDelegate {

    private static final String TAG = ListBuddiesLayout.class.getSimpleName();
    private static final long PRESS_DELAY = 100;
    private static final float CANCEL_CLICK_LIMIT = 8;
    public static final int DEFAULT_SPEED = 2;
    public static final int ATTR_NOT_SET = -1;

    private OnBuddyItemClickListener mItemBuddyListener;
    private ObservableListView mListViewLeft;
    private ObservableListView mListViewRight;
    private boolean mActionDown;
    private boolean isRightListEnabled = false;
    private boolean isLeftListEnabled = false;
    private boolean isUserInteracting = true;
    private boolean isAutoScrollLeftListFaster;
    private boolean isManualScrollLeftListFaster;
    private View mDownView;
    private View mGapView;
    private ViewStub mViewStubGap;
    private Rect mRect = new Rect();
    private ListBuddiesAutoScrollHelper mScrollHelper;
    private int[] mListViewCoords = new int[2];
    private int mGap;
    private int mGapColor;
    private int mSpeed = DEFAULT_SPEED;
    private int mSpeedLeft;
    private int mSpeedRight;
    private int mDividerHeight;
    private int mLastViewTouchId;
    private int mDownPosition;
    private int mChildCount;
    private float mDownEventY;
    private Drawable mDivider;

    public ListBuddiesLayout(Context context) {
        super(context);
        init(context);
    }

    public ListBuddiesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListBuddiesAttributes(context, attrs);
        init(context);
        applyViewParams();
    }

    private void setListBuddiesAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListBuddiesOptions, 0, 0);
            mGap = a.getDimensionPixelSize(R.styleable.ListBuddiesOptions_gap, 12);
            mSpeed = a.getInteger(R.styleable.ListBuddiesOptions_speed, DEFAULT_SPEED);
            int configOptionFasterList = a.getInteger(R.styleable.ListBuddiesOptions_autoScrollFaster, ScrollConfigOptions.LEFT.getConfigValue());
            isAutoScrollLeftListFaster = configOptionFasterList == ScrollConfigOptions.LEFT.getConfigValue();
            isManualScrollLeftListFaster = a.getInteger(R.styleable.ListBuddiesOptions_scrollFaster, configOptionFasterList) == ScrollConfigOptions.LEFT.getConfigValue();
            mDivider = a.getDrawable(R.styleable.ListBuddiesOptions_listsDivider);
            mDividerHeight = a.getDimensionPixelSize(R.styleable.ListBuddiesOptions_listsDividerHeight, ATTR_NOT_SET);
            mGapColor = a.getColor(R.styleable.ListBuddiesOptions_gapColor, ATTR_NOT_SET);
            a.recycle();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Logs.e(e, "");
        }
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.listbuddies, this, true);
        mListViewLeft = (ObservableListView) findViewById(R.id.list_left);
        mListViewRight = (ObservableListView) findViewById(R.id.list_right);
        mLastViewTouchId = mListViewRight.getId();
        calibrateSpeed();
        setListeners();
        setScrollHelpers();
        startAutoScroll();
    }

    private void calibrateSpeed() {
        //Parallax doesnt work with speed 1
        if (mSpeed == 1) {
            mSpeed = DEFAULT_SPEED;
        }

        if (isAutoScrollLeftListFaster) {
            mSpeedLeft = mSpeed;
            mSpeedRight = mSpeed / 2;
        } else {
            mSpeedLeft = mSpeed / 2;
            mSpeedRight = mSpeed;
        }
    }

    private void setListeners() {
        mListViewLeft.setOnTouchListener(this);
        mListViewLeft.setObserver(this);
        setOnListScrollListener(mListViewLeft, true);
        mListViewRight.setOnTouchListener(this);
        mListViewRight.setObserver(this);
        setOnListScrollListener(mListViewRight, false);
    }

    private void setOnListScrollListener(ObservableListView list, final boolean isLeftList) {
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int state) {
                switch (state) {
                    case SCROLL_STATE_IDLE:
                        setListState(true, isLeftList);
                        forceScrollIfNeeded(isOtherListEnable(isLeftList));
                        break;

                    case SCROLL_STATE_TOUCH_SCROLL:
                        setListState(false, isLeftList);
                        isUserInteracting = true;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }
        });
    }

    private void forceScrollIfNeeded(boolean isListEnabled) {
        if (isUserInteracting && isListEnabled) {
            isUserInteracting = false;
            if (!mActionDown) {
                forceScroll();
            }
        }
    }

    private boolean isOtherListEnable(boolean isLeftList) {
        boolean result;
        if (isLeftList) {
            result = isRightListEnabled;
        } else {
            result = isLeftListEnabled;
        }
        return result;
    }

    private void setListState(boolean isEnabled, boolean isLeftList) {
        if (isLeftList) {
            isLeftListEnabled = isEnabled;
        } else {
            isRightListEnabled = isEnabled;
        }
    }

    private void setScrollHelpers() {
        mScrollHelper = new ListBuddiesAutoScrollHelper(mListViewLeft) {
            @Override
            public void scrollTargetBy(int deltaX, int deltaY) {
                mListViewLeft.smoothScrollBy(mSpeedLeft, 0);
                mListViewRight.smoothScrollBy(mSpeedRight, 0);
            }

            @Override
            public boolean canTargetScrollHorizontally(int i) {
                return false;
            }

            @Override
            public boolean canTargetScrollVertically(int i) {
                return true;
            }
        };

        mScrollHelper.setEnabled(isEnable());
        mScrollHelper.setEdgeType(AutoScrollHelper.EDGE_TYPE_OUTSIDE);
    }

    private boolean isEnable() {
        return mSpeed != 0;
    }

    private void startAutoScroll() {
        mListViewLeft.post(new Runnable() {
            @Override
            public void run() {
                forceScroll();
            }
        });
    }

    private void forceScroll() {
        MotionEvent event = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), MotionEvent.ACTION_MOVE, 570, -1, 0);
        mScrollHelper.onTouch(mListViewLeft, event);
    }

    private void applyViewParams() {
        setGap();
        setDividerAndHeight(mDivider, mDividerHeight);
    }

    private void setDividerAndHeight(Drawable drawable, int dividerHeight) {
        setListsDivider(drawable);
        setHeightDivider(dividerHeight);
    }

    private void setListsDivider(Drawable drawable) {
        if (drawable != null) {
            mListViewLeft.setDivider(drawable);
            mListViewRight.setDivider(drawable);
        }
    }

    private void setHeightDivider(int dividerHeight) {
        if (dividerHeight > ATTR_NOT_SET) {
            mListViewLeft.setDividerHeight(dividerHeight);
            mListViewRight.setDividerHeight(dividerHeight);
        }
    }

    private void setGap() {
        if (mGapColor != ATTR_NOT_SET) {
            createGap();
            setGapColor();
        } else {
            emptyGap();
        }
    }

    private void setGapColor() {
        if (mGapView != null && mGapColor != ATTR_NOT_SET) {
            mGapView.setBackgroundColor(mGapColor);
        }
    }

    private void emptyGap() {
        setLeftListMargin(mGap);
        if (mGapView != null) {
            mGapView.setVisibility(View.GONE);
        }
    }

    private void setLeftListMargin(int marginRigth) {
        if (mListViewLeft != null) {
            LayoutParams params = (LayoutParams) mListViewLeft.getLayoutParams();
            params.setMargins(0, 0, marginRigth, 0);
            mListViewLeft.setLayoutParams(params);
        }
    }

    private void createGap() {
        if (mViewStubGap == null) {
            mViewStubGap = (ViewStub) findViewById(R.id.gap);
        }
        if (mGapView == null) {
            mGapView = mViewStubGap.inflate();
        }

        setLeftListMargin(0);
        setGapWidth(mGap);
        if (mGapView != null) {
            mGapView.setVisibility(View.VISIBLE);
        }
    }

    private void setGapWidth(int width) {
        if (mGapView != null) {
            LayoutParams params = (LayoutParams) mGapView.getLayoutParams();
            params.width = width;
            mGapView.setLayoutParams(params);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ListView list = (ListView) v;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown(list, event);
                break;
            case MotionEvent.ACTION_UP:
                actionUp(list);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
        }
        return mScrollHelper.onTouch(v, event);
    }

    private void actionDown(ListView list, MotionEvent event) {
        mActionDown = true;
        toogleListView(list);
        mLastViewTouchId = list.getId();
        startClickSelection(event, list, event.getY());
    }

    /**
     * Each time we touch the opposite ListView than the last one we have selected
     * we need to activate it as the enable one
     */
    private void toogleListView(View v) {
        if (mLastViewTouchId != v.getId()) {
            if (mLastViewTouchId == mListViewLeft.getId()) {
                isLeftListEnabled = true;
                isRightListEnabled = false;
            } else {
                isLeftListEnabled = false;
                isRightListEnabled = true;
            }
        }
    }

    private void startClickSelection(MotionEvent event, ListView list, float eventY) {
        if (!isUserInteracting || mSpeed == 0) {
            findViewClicked(event, eventY, list);

            setSelectorPressed(list);
        }
    }

    private void findViewClicked(MotionEvent event, float eventY, ListView list) {
        mChildCount = list.getChildCount();
        mListViewCoords = new int[2];
        list.getLocationOnScreen(mListViewCoords);
        int x = (int) event.getRawX() - mListViewCoords[0];
        int y = (int) event.getRawY() - mListViewCoords[1];
        View child;
        for (int i = 0; i < mChildCount; i++) {
            child = list.getChildAt(i);
            child.getHitRect(mRect);
            if (mRect.contains(x, y)) {
                mDownView = child;
                mDownEventY = eventY;
                break;
            }
        }
    }

    private void setSelectorPressed(ListView list) {
        if (mDownView != null) {
            mDownPosition = list.getPositionForView(mDownView);
            mDownView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isUserInteracting) {
                        if (mDownView != null) {
                            mDownView.setPressed(true);
                        }
                    }
                }
            }, PRESS_DELAY);
        }
    }

    private void actionUp(ListView list) {
        mActionDown = false;
        performClick(list);
    }

    private void actionMove(MotionEvent event) {
        cancelClick(event.getY());
    }

    private void cancelClick(float eventY) {
        if (mDownView != null && (Math.abs(mDownEventY - eventY) > CANCEL_CLICK_LIMIT)) {
            mDownView.setPressed(false);
            mDownView = null;
        }
    }

    private void performClick(ListView list) {
        //In some occasions setOnListScrollListener-SCROLL_STATE_TOUCH_SCROLL
        //doesn't get called when we perform a click, so we need to double check
        if (!isUserInteracting) {
            isUserInteracting = true;
        }
        if (mDownView != null || mSpeed == 0) {
            mDownView.setPressed(false);
            if (mItemBuddyListener != null) {
                int buddy = 0;
                if (list.getId() != mListViewLeft.getId()) {
                    buddy = 1;
                }
                int position = getPosition(list, mDownPosition);
                mItemBuddyListener.onBuddyItemClicked(list, mDownView, buddy, position, mDownView.getId());
            }
        }
    }

    private int getPosition(ListView list, int position) {
        return ((CircularLoopAdapter) list.getAdapter()).getCircularPosition(position);
    }

    /**
     * Receives the distance scroll on listView.
     */
    @Override
    public void onListScroll(View view, float deltaY) {
        int speed;
        if (view.getId() == mListViewLeft.getId() && !isLeftListEnabled) {
            speed = getSpeed(false, deltaY);

            mListViewRight.smoothScrollBy(speed, 0);
        } else if (view.getId() == mListViewRight.getId() && !isRightListEnabled) {
            speed = getSpeed(true, deltaY);
            mListViewLeft.smoothScrollBy(speed, 0);
        }
    }

    private int getSpeed(boolean isCalculationForLeft, float deltaY) {
        int speed;

        if (isManualScrollLeftListFaster && isCalculationForLeft || !isManualScrollLeftListFaster && !isCalculationForLeft) {
            speed = (int) -deltaY * 2;
        } else {
            speed = (int) -deltaY / 2;
        }
        return speed;
    }

    public interface OnBuddyItemClickListener {
        //Buddy corresponde with the list (0-left, 1-right)
        void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id);
    }

    /**
     * Public setters
     */

    public void setOnItemClickListener(OnBuddyItemClickListener listener) {
        mItemBuddyListener = listener;
    }

    public ListBuddiesLayout setGap(int value) {
        mGap = value;
        setGap();
        return this;
    }

    public ListBuddiesLayout setSpeed(int value) {
        mSpeed = value;
        calibrateSpeed();
        return this;
    }

    public ListBuddiesLayout setDividerHeight(int value) {
        mDividerHeight = value;
        setDividerAndHeight(mDivider, value);
        return this;
    }

    public ListBuddiesLayout setDivider(Drawable drawable) {
        mDivider = drawable;
        setDividerAndHeight(drawable, mDividerHeight);
        return this;
    }


    public ListBuddiesLayout setGapColor(int color) {
        mGapColor = color;
        setGap();
        return this;
    }

    public ListBuddiesLayout setAutoScrollFaster(int option) {
        isAutoScrollLeftListFaster = option == 1;
        calibrateSpeed();
        return this;
    }

    public ListBuddiesLayout setManualScrollFaster(int option) {
        isManualScrollLeftListFaster = option == 1;
        return this;
    }

    public void setAdapters(CircularLoopAdapter adapter, CircularLoopAdapter adapter2) {
        mListViewLeft.setAdapter(adapter);
        mListViewRight.setAdapter(adapter2);
        mListViewLeft.setSelection(Integer.MAX_VALUE / 2);
        mListViewRight.setSelection(Integer.MAX_VALUE / 2);
    }

    /**
     * Public getters
     */
    public ListView getListViewLeft() {
        return mListViewLeft;
    }

    public ListView getListViewRight() {
        return mListViewRight;
    }

    public int getGapColor() {
        return mGapColor;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public int getGap() {
        return mGap;
    }

    public int getDividerHeight() {
        return mDividerHeight;
    }

    public Drawable getDivider() {
        return mDivider;
    }
}
