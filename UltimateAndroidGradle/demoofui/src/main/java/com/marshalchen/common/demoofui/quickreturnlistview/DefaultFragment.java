/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.demoofui.quickreturnlistview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.QuickReturnListView;

public class DefaultFragment extends ListFragment{
	
	private QuickReturnListView mListView;
	private View mHeader;
	private TextView mQuickReturnView;
	private View mPlaceHolder;
	
	private int mCachedVerticalScrollRange;
	private int mQuickReturnHeight;
	
	private static final int STATE_ONSCREEN = 0;
	private static final int STATE_OFFSCREEN = 1;
	private static final int STATE_RETURNING = 2;
	private int mState = STATE_ONSCREEN;
	private int mScrollY;
	private int mMinRawY = 0;
	
	private TranslateAnimation anim;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.quick_return_listview_fragment, null);
		mHeader = inflater.inflate(R.layout.quick_return_listview_header, null);
		mQuickReturnView = (TextView) view.findViewById(R.id.sticky);
		mPlaceHolder = mHeader.findViewById(R.id.placeholder);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mListView = (QuickReturnListView) getListView();
		
		mQuickReturnView.setText("Default");
		mListView.addHeaderView(mHeader);
		
		String[] array = new String[] { "Android", "Android", "Android",
				"Android", "Android", "Android", "Android", "Android",
				"Android", "Android", "Android", "Android", "Android",
				"Android", "Android", "Android" };
		
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.quick_return_listview_list_item,
				R.id.text1, array));
		
		mListView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						mQuickReturnHeight = mQuickReturnView.getHeight();
						mListView.computeScrollY();
						mCachedVerticalScrollRange = mListView.getListHeight();
					}
				});

		mListView.setOnScrollListener(new OnScrollListener() {
			@SuppressLint("NewApi")
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				mScrollY = 0;
				int translationY = 0;
				
				if (mListView.scrollYIsComputed()) {
					mScrollY = mListView.getComputedScrollY();
				}

				int rawY = mPlaceHolder.getTop()
						- Math.min(
								mCachedVerticalScrollRange
										- mListView.getHeight(), mScrollY);
				
				switch (mState) {
				case STATE_OFFSCREEN:
					if (rawY <= mMinRawY) {
						mMinRawY = rawY;
					} else {
						mState = STATE_RETURNING;
					}
					translationY = rawY;
					break;

				case STATE_ONSCREEN:
					if (rawY < -mQuickReturnHeight) {
						mState = STATE_OFFSCREEN;
						mMinRawY = rawY;
					}
					translationY = rawY;
					break;

				case STATE_RETURNING:
					translationY = (rawY - mMinRawY) - mQuickReturnHeight;
					if (translationY > 0) {
						translationY = 0;
						mMinRawY = rawY - mQuickReturnHeight;
					}

					if (rawY > 0) {
						mState = STATE_ONSCREEN;
						translationY = rawY;
					}

					if (translationY < -mQuickReturnHeight) {
						mState = STATE_OFFSCREEN;
						mMinRawY = rawY;
					}
					break;
				}
				
				/** this can be used if the build is below honeycomb **/
				if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
					anim = new TranslateAnimation(0, 0, translationY,
							translationY);
					anim.setFillAfter(true);
					anim.setDuration(0);
					mQuickReturnView.startAnimation(anim);
				} else {
					mQuickReturnView.setTranslationY(translationY);
				}

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
		});
	}
}