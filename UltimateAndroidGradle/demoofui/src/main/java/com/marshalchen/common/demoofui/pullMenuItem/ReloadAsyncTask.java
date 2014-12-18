package com.marshalchen.common.demoofui.pullMenuItem;

import android.os.AsyncTask;
import android.util.Log;

import progress.menu.item.ProgressMenuItemHelper;

public class ReloadAsyncTask extends AsyncTask<Void, Void, Void> {

    private ProgressMenuItemHelper progressHelper;

    public ReloadAsyncTask(ProgressMenuItemHelper progressHelper) {
        this.progressHelper = progressHelper;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressHelper.startProgress();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException e) {
            Log.d(getClass().getCanonicalName(), e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressHelper.stopProgress();
    }

}