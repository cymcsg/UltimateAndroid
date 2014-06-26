package com.fss.Common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import com.fss.Common.commonUtils.logUtils.Logs;

/**
 * Created by cym on 14-6-26.
 */
public class HomeasUpActionbarActivity extends ActionBarActivity {
    private void beforeOnCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeOnCreate();
    }

    @Override
    public Intent getSupportParentActivityIntent() {

        //   finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }
}
