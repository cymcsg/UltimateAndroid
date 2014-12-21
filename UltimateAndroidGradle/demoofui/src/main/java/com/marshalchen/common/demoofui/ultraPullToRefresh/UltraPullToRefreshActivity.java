package com.marshalchen.common.demoofui.ultraPullToRefresh;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.demoofui.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by cym on 14-12-16.
 */
public class UltraPullToRefreshActivity extends ActionBarActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout);
        setContentView(R.layout.ultra_pull_refresh_activity_main);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        // the following are default settings
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
// default is false
        mPtrFrameLayout.setPullToRefresh(false);
// default is true
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
//        mPtrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // ptrFrame.autoRefresh();
//            }
//        }, 150);
//        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
//                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
//                Logs.d("canbepull----" + canbePullDown);
//                return canbePullDown;
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
//                ptrFrameLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mPtrFrameLayout.refreshComplete();
//                    }
//                }, 1800);
//            }
//        });

    }
}
