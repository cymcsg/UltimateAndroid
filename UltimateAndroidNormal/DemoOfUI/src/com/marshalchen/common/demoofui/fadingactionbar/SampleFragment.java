/*
 * Copyright (c) 2014. Marshal Chen.
 */
package com.marshalchen.common.demoofui.fadingactionbar;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.fadingactionbar.FadingActionBarHelper;

public class SampleFragment extends Fragment {
    private FadingActionBarHelper mFadingHelper;
    private Bundle mArguments;

    public static final String ARG_IMAGE_RES = "image_source";
    public static final String ARG_ACTION_BG_RES = "image_action_bs_res";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);

        if (mArguments != null){
            ImageView img = (ImageView) view.findViewById(R.id.image_header);
            img.setImageResource(mArguments.getInt(ARG_IMAGE_RES));
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mArguments = getArguments();
        int actionBarBg = mArguments != null ? mArguments.getInt(ARG_ACTION_BG_RES) : R.drawable.fading_actionbar_ab_background_light;

        mFadingHelper = new FadingActionBarHelper()
            .actionBarBackground(actionBarBg)
            .headerLayout(R.layout.fading_actionbar_header_light)
            .contentLayout(R.layout.fading_actionbar_activity_scrollview)
            .lightActionBar(actionBarBg == R.drawable.fading_actionbar_ab_background_light);
        mFadingHelper.initActionBar(activity);
    }
}
