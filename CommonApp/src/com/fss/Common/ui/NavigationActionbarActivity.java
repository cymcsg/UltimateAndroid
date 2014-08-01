package com.fss.common.ui;

import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import com.fss.common.commonUtils.logUtils.Logs;

/**
 * Created by cym on 14-6-26.
 */
public class NavigationActionbarActivity extends ActionBarActivity {
    @Override
    public Intent getSupportParentActivityIntent() {
        Logs.d("upTask");
        //   finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }
}
