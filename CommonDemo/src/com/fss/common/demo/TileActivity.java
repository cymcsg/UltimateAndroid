package com.fss.common.demo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import com.fss.Common.uiModule.tileView.tileview.TileView;

public class TileActivity extends ActionBarActivity {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TileView tileView = new TileView(this);
        // Set the minimum parameters
        tileView.setSize(6252, 4087);
        tileView.addDetailLevel(1f, "big-world-map50.gif", "big-world-map.gif");
        tileView.addDetailLevel(0.1f, "big-world-map5.gif", "big-world-map.gif");
        setContentView(tileView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
