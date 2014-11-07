package com.marshalchen.common.demoofui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.basicUtils.HandlerUtils;
import com.marshalchen.common.uimodule.smoothprogressbar.SmoothProgressBar;

public class SmoothProgressBarActivity extends ActionBarActivity {

    @InjectView(R.id.smoothProgressBar)
    SmoothProgressBar smoothProgressBar;
    @InjectView(R.id.smoothProgressBar1)
    SmoothProgressBar smoothProgressBar1;
    @InjectView(R.id.smoothProgressBar2)
    SmoothProgressBar smoothProgressBar2;
    @InjectView(R.id.smoothProgressBar3)
    SmoothProgressBar smoothProgressBar3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TileView tileView = new TileView(this);
//        // Set the minimum parameters
//        tileView.setSize(6252, 4087);
//        tileView.addDetailLevel(1f, "big-world-map50.gif", "big-world-map.gif");
//        tileView.addDetailLevel(0.1f, "big-world-map5.gif", "big-world-map.gif");
//        setContentView(tileView);
        setTheme(R.style.LandingTheme);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.smooth_progress_bar_activity);
        ButterKnife.inject(this);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar, 300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.invisiablViewHandler, 0, smoothProgressBar, 2000);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar1, 2300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.invisiablViewHandler, 0, smoothProgressBar1, 4000);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar2, 4300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.invisiablViewHandler, 0, smoothProgressBar2, 6000);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar3, 6300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.invisiablViewHandler, 0, smoothProgressBar3, 8000);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar, 8300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar1, 8300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar2, 8300);
        HandlerUtils.sendMessageHandlerDelay(HandlerUtils.visiablViewHandler, 0, smoothProgressBar3, 8300);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        this.finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }
}
