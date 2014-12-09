package com.marshalchen.common.ui;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public abstract class CustomViewActionbarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//        //使自定义的普通View能在title栏显示, actionBar.setCustomView能起作用.
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(getCustomActionBarView());
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
    }

    protected abstract View getCustomActionBarView();

}
