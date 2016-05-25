package com.marshalchen.common.demoofui.materialmenu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.marshalchen.common.demoofui.R;


public abstract class BaseActivity extends ActionBarActivity {

    protected BaseActivityHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_menu_demo);
        helper = new BaseActivityHelper();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        helper.refreshDrawerState();
    }
}
