package com.marshalchen.common.demoofui.pullMenuItem;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.demoofui.HowToUseActivity;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.pullMenuItem.ReloadIntentService;
import com.marshalchen.common.demoofui.pullMenuItem.ReloadIntentService.ReloadReceiver;

import progress.menu.item.ProgressMenuItemHelper;
import progress.menu.item.ProgressMenuItemSize;

public class ProgressMenuItemActivity extends ActionBarActivity {
    static final String RELOAD_FILTER = "ProgressMenuItemActivity_reloadReceiver";

    private ProgressMenuItemHelper progressHelper;
    private ReloadReceiver reloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_menu_item_activity_main);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reloadReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(reloadReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.progress_menu_item_refresh_menu, menu);
        progressHelper = new ProgressMenuItemHelper(menu, R.id.action_refresh, ProgressMenuItemSize.LARGE);
        reloadReceiver = new ReloadReceiver(progressHelper);
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(reloadReceiver, new IntentFilter(RELOAD_FILTER));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Intent reloadService = new Intent(this, ReloadIntentService.class);
                reloadReceiver.startProgress();
                this.startService(reloadService);
                return true;
            case R.id.howToUse:
                BasicUtils.sendIntent(this, HowToUseActivity.class, "data", "ProgressMenuItemActivity");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
