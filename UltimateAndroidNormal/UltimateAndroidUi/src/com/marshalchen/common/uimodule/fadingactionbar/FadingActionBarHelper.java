/*
 * Copyright (c) 2014. Marshal Chen.
 */
package com.marshalchen.common.uimodule.fadingactionbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;

public class FadingActionBarHelper extends FadingActionBarHelperBase {

    private ActionBar mActionBar;

    @SuppressLint("NewApi")
    @Override
    public void initActionBar(Activity activity) {
        mActionBar = activity.getActionBar();
        super.initActionBar(activity);
    }

    @SuppressLint("NewApi")
    @Override
    protected int getActionBarHeight() {
        return mActionBar.getHeight();
    }

    @Override
    protected boolean isActionBarNull() {
        return mActionBar == null;
    }

    @SuppressLint("NewApi")
    @Override
    protected void setActionBarBackgroundDrawable(Drawable drawable) {
        mActionBar.setBackgroundDrawable(drawable);
    }
}
