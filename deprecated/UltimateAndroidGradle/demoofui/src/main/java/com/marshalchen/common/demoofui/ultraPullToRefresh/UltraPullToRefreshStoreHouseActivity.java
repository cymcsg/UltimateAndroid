package com.marshalchen.common.demoofui.ultraPullToRefresh;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.commonUtils.uiUtils.BasicUiUtils;
import com.marshalchen.common.demoofui.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by cym on 14-12-16.
 */
public class UltraPullToRefreshStoreHouseActivity extends ActionBarActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout);
        setContentView(R.layout.ultra_pull_refresh_store_house_activity_main);
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
        final StoreHouseHeader header = new StoreHouseHeader(this);
        header.setPadding(0, BasicUiUtils.dip2px(this, 15.0f), 0, 0);

/**
 * using a string, support: A-Z 0-9 - .
 * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
 */
        header.initWithString("Marshal Chen");
      //  header.initWithStringArray(R.array.akta);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
//        mPtrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // ptrFrame.autoRefresh();
//            }
//        }, 150);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                Logs.d("canbepull----" + canbePullDown);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 1800);
            }
        });

    }
}
