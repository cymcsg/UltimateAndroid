package com.marshalchen.common.demoofui.matchview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.matchview.MatchTextView;


public class MatchViewActivity extends ActionBarActivity {

    private SeekBar mSeekBar;
    private MatchTextView mMatchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_activity_main);

        mMatchTextView = (MatchTextView) findViewById(R.id.mMatchTextView);
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
        mSeekBar.setProgress(100);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMatchTextView.setProgress(progress * 1f / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.mButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchDialog matchDialog = new MatchDialog();
                getSupportFragmentManager().beginTransaction().add(matchDialog, "matchDialog").commit();
            }
        });
    }

}
