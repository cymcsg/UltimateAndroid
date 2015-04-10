package com.marshalchen.common.uimodule.simplemodule;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.marshalchen.common.uimodule.widgets.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressLayout extends RelativeLayout {

    private static final String TAG_PROGRESS = "ProgressLayout.TAG_PROGRESS";

    private View mProgressView;
    private List<View> mContentViews = new ArrayList<View>();

    public ProgressLayout(Context context) {
        super(context);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayout);
        int backgroundColor = a.getColor(R.styleable.ProgressLayout_progressLayoutProgressBackground, Color.TRANSPARENT);
        boolean startFromProgress = a.getBoolean(R.styleable.ProgressLayout_progressLayoutProgress, false);
        a.recycle();

        LayoutParams layoutParams;

        // if progressBackground color == Color.TRANSPARENT just add progress bar
        if (backgroundColor == Color.TRANSPARENT) {
            mProgressView = new ProgressBar(getContext());

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(CENTER_IN_PARENT);
        } else { // else wrap progress bar in LinearLayout and set background color to LinearLayout
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setBackgroundColor(backgroundColor);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            ProgressBar progressBar = new ProgressBar(getContext());
            linearLayout.addView(progressBar);

            mProgressView = linearLayout;
        }

        mProgressView.setTag(TAG_PROGRESS);
        if (!startFromProgress) {
            mProgressView.setVisibility(View.GONE);
        }
        addView(mProgressView, layoutParams);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || !child.getTag().equals(TAG_PROGRESS)) {
            mContentViews.add(child);
        }
    }

    public boolean isProgress() {
        return mProgressView.getVisibility() == View.VISIBLE;
    }

    public void setProgress(boolean visible) {
        setProgress(visible, Collections.<Integer>emptyList());
    }

    public void setProgress(boolean visible, List<Integer> skipIds) {
        mProgressView.setVisibility(visible ? View.VISIBLE : View.GONE);

        for (View v : mContentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.GONE : View.VISIBLE);
            }
        }
    }
}
