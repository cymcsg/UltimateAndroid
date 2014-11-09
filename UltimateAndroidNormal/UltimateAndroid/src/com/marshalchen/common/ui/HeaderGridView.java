package com.marshalchen.common.ui;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ListView.FixedViewInfo;

public class HeaderGridView extends GridView implements OnScrollListener{

	private ListAdapter mAdapter;
	private boolean mShowHeader = false;
	private Context mContext;
	private int mScrollOfsset;
	private int initialTopPadding;
	private int mDisplayWidth = 0;
	
	public HeaderGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public HeaderGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HeaderGridView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		mContext = context;
		super.setOnScrollListener(this);
	}
	/**
     * A class that represents a fixed view in a list, for example a header at the top
     * or a footer at the bottom.
     */
	
	private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<FixedViewInfo>();
    // TODO: add in footer view support. pull request someone?
	private ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<FixedViewInfo>();
    
    private void notifiyChanged(){
    	this.requestLayout();
    	this.invalidate();
    }
    
    public void addHeaderView(View v, Object data, boolean isSelectable) {

        FixedViewInfo info = new ListView(getContext()).new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        mHeaderViewInfos.add(info);

        setupView(v);
        
        int topPadding = this.getPaddingTop();
        if(initialTopPadding == 0){
        	initialTopPadding = topPadding;
        }
        this.setPadding(this.getPaddingLeft(), topPadding+v.getMeasuredHeight(), this.getPaddingRight(), this.getPaddingBottom());
        
        // in the case of re-adding a header view, or adding one later on,
        // we need to notify the observer
        this.notifiyChanged();
    }
    
    private void setupView(View v){
    	// in my application the views are merely just inflated... because of this measure and layout need to be called
    	
    	boolean isLayedOut = !((v.getRight()==0) && (v.getLeft()==0) && (v.getTop()==0) && (v.getBottom()==0));
    	
    	// no need to do this more than nce per view. Sometimes people repeat headers.
    	if(v.getMeasuredHeight() != 0 && isLayedOut ) return;
    	
    	if(mDisplayWidth == 0){
    		DisplayMetrics displaymetrics = new DisplayMetrics();
        	((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    		mDisplayWidth = displaymetrics.widthPixels;
    	}
    	
    	v.measure(MeasureSpec.makeMeasureSpec(mDisplayWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        v.layout(0, getTotalHeaderHeight(), v.getMeasuredWidth(), getTotalHeaderHeight() + v.getMeasuredHeight());
    }
    
    public void addHeaderView(View v) {
    	this.addHeaderView(v, null, false);
    }

    private void drawHeaders(Canvas canvas) {
    	int startPos = -mScrollOfsset; // translate view all way up first...
    	int saveCount = canvas.save();
    	for(FixedViewInfo header: mHeaderViewInfos){
    		View view = header.view;
    		canvas.translate(0, startPos);
    		startPos = view.getMeasuredHeight();
    		view.draw(canvas);
    	}
    	canvas.restoreToCount(saveCount);
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {        
    	super.dispatchDraw(canvas);
        drawHeaders(canvas);
    }
    
    private void invalidateIfAnimating() {
    	invalidate();
    }

    public int getHeaderViewCount(){
    	return mHeaderViewInfos.size();
    }
    
    private int getTotalHeaderHeight(){
    	int totalHeaderHeight = 0;
		for(FixedViewInfo h: mHeaderViewInfos){
			totalHeaderHeight += h.view.getMeasuredHeight();
		}
		return totalHeaderHeight;
    }
    
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(this.getAdapter()!=null){
			int count  = this.getChildCount();
			int totalHeaderHeight = getTotalHeaderHeight();
			
			if(count > 0 && firstVisibleItem == 0){
				View child = this.getChildAt(0);
				mScrollOfsset = totalHeaderHeight - child.getTop() + initialTopPadding;
				
				// need to check to see if should show header
				// no need to dispatch otherwise
				if(child.getTop() >= 0){
					mShowHeader = true;
				}else{
					mShowHeader = false;
				}
			}else{
				mShowHeader = false;
			}
			
			if(mShowHeader){
				this.invalidateIfAnimating();
			}
		}
		
	}
	
	/**
	 * Remove the view and return true is a view has been removed.
	 * @param v
	 * @return
	 */
	public boolean removeHeaderView(View v) {
        if (mHeaderViewInfos.size() > 0) {
            return removeFixedViewInfo(v, mHeaderViewInfos);
        }
        return false;
    }

    private boolean removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        int count = 0;
        for (int i = 0; i < len; ++i) {
            FixedViewInfo info = where.get(i);
            if (info.view == v) {
            	this.setPadding(this.getPaddingLeft(), getPaddingTop()-v.getMeasuredHeight(), this.getPaddingRight(), this.getPaddingBottom());
                where.remove(i);
                count++;
                break;
            }
        }
        
        return count > 0;
    }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
}
