package com.marshalchen.common.demoofui.pullMenuItem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.common.demoofui.R;

import progress.menu.item.ProgressMenuItemHelper;
import progress.menu.item.ProgressMenuItemSize;

public class ProgressMenuItemActivity extends ActionBarActivity {

    private ProgressMenuItemHelper progressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_menu_item_activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.progress_menu_item_refresh_menu, menu);
        progressHelper = new ProgressMenuItemHelper(menu, R.id.action_refresh, ProgressMenuItemSize.LARGE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new ReloadAsyncTask(progressHelper).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}