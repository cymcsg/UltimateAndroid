package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.simplemodule.NumberProgressBar;


import java.util.Timer;
import java.util.TimerTask;


public class NumberProgressBarActivity extends ActionBarActivity {
    private int counter = 0;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_progress_bar_activity_main);

        final NumberProgressBar bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        counter = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                        counter ++;
                        if (counter == 110) {
                            bnp.setProgress(0);
                            counter=0;

                        }
                    }
                });
            }
        }, 1000, 100);

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
