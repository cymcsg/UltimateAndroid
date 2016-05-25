package com.marshalchen.common.demoofui.pullMenuItem;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import progress.menu.item.ProgressMenuItemHelper;

public class ReloadIntentService extends IntentService {
    public ReloadIntentService() {
        super("ReloadIntentService");
    }

    public void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException e) {
            Log.d(getClass().getCanonicalName(), e.toString());
        }
        Intent resultIntent = new Intent(ProgressMenuItemActivity.RELOAD_FILTER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }

    public static class ReloadReceiver extends BroadcastReceiver {

        private ProgressMenuItemHelper progressHelper;

        public ReloadReceiver(ProgressMenuItemHelper progressHelper) {
            this.progressHelper = progressHelper;
        }

        public void startProgress() {
            progressHelper.startProgress();
        }

        @Override
        public void onReceive(Context receiverContext, Intent receiverIntent) {
            progressHelper.stopProgress();
        }
    }
}