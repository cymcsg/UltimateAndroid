/*
 * Copyright (C) 2013 Priboi Tiberiu
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.uimodule.foldingLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.marshalchen.common.uimodule.R;


/**
 * FoldingPaneLayout change the sliding effect with folding effect of SlidingPaneLayout
 * 
 */
public class FoldingPaneLayout extends SlidingPaneLayout {

	protected BaseFoldingLayout foldingNavigationLayout = null;

	
	int mNumberOfFolds;

	public FoldingPaneLayout(Context context) {
		this(context, null);
		foldingNavigationLayout = new BaseFoldingLayout(getContext());
	}

	public FoldingPaneLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

		initView(context, attrs);
	}

	public FoldingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView(context, attrs);

	}

	private void initView(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.FoldingMenu);
		int mFoldNumber = ta.getInt(R.styleable.FoldingMenu_foldNumber,
				mNumberOfFolds);
		if (mFoldNumber > 0 && mFoldNumber < 7) {
			mNumberOfFolds = mFoldNumber;
		} else {
			mNumberOfFolds = 2;
		}
		ta.recycle();

		foldingNavigationLayout = new BaseFoldingLayout(getContext());
		foldingNavigationLayout.setNumberOfFolds(mNumberOfFolds);
		foldingNavigationLayout.setAnchorFactor(0);

	}

	public BaseFoldingLayout getFoldingLayout() {
		return foldingNavigationLayout;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		View child = getChildAt(0);
		if (child != null) {
			System.out.println(child);

			removeView(child);
			foldingNavigationLayout.addView(child);
			ViewGroup.LayoutParams layPar = child.getLayoutParams();
			addView(foldingNavigationLayout, 0, layPar);

		}

		setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelSlide(View arg0, float mSlideOffset) {
				if (foldingNavigationLayout != null) {
					foldingNavigationLayout.setFoldFactor(1-mSlideOffset);
				}

			}

			@Override
			public void onPanelOpened(View arg0) {

			}

			@Override
			public void onPanelClosed(View arg0) {

			}
		});

	}
}
