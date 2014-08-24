/*
 * Copyright (c) 2014. Marshal Chen.
 */
package com.fss.common.demo.fadingactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.fss.common.demo.R;

public class SampleFragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fading_actionbar_activity_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fading_actionbar_activity_menu, menu);
        return true;
    }
}
