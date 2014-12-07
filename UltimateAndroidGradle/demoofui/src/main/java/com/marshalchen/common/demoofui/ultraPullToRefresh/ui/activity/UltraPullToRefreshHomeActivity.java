package com.marshalchen.common.demoofui.ultraPullToRefresh.ui.activity;

import android.os.Bundle;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.ultraPullToRefresh.ui.HomeFragment;

import in.srain.cube.mints.base.DemoBaseActivity;


public class UltraPullToRefreshHomeActivity extends DemoBaseActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ultra_pull_refresh_activity_main);
        pushFragmentToBackStack(HomeFragment.class, null);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}