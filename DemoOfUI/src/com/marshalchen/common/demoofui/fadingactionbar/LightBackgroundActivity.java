/*
 * Copyright (c) 2014. Marshal Chen.
 */
package com.marshalchen.common.demoofui.fadingactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.fadingactionbar.FadingActionBarHelper;

public class LightBackgroundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = new FadingActionBarHelper()
            .actionBarBackground(R.drawable.fading_actionbar_ab_background)
            .headerLayout(R.layout.fading_actionbar_header_light)
            .contentLayout(R.layout.fading_actionbar_activity_scrollview);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fading_actionbar_activity_menu, menu);
        return true;
    }
}
