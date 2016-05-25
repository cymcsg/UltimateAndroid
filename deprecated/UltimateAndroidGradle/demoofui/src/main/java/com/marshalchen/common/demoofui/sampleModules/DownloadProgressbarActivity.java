package com.marshalchen.common.demoofui.sampleModules;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.simplemodule.DownloadProgressBar;


public class DownloadProgressbarActivity extends AppCompatActivity {

    private int val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_progress__activity_main);
        final DownloadProgressBar downloadProgressView = (DownloadProgressBar)findViewById(R.id.dpv3);
        final TextView successTextView = (TextView)findViewById(R.id.success_text_view);
        successTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = val + 10;
                downloadProgressView.setProgress(val);
            }
        });
        Typeface robotoFont=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        successTextView.setTypeface(robotoFont);

        downloadProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadProgressView.playManualProgressAnimation();
            }
        });
        downloadProgressView.setOnProgressUpdateListener(new DownloadProgressBar.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(float currentPlayTime) {

            }

            @Override
            public void onAnimationStarted() {
                downloadProgressView.setEnabled(false);
            }

            @Override
            public void onAnimationEnded() {
                val = 0;
                successTextView.setText("Click to download");
                downloadProgressView.setEnabled(true);
            }

            @Override
            public void onAnimationSuccess() {
                successTextView.setText("Downloaded!");
            }

            @Override
            public void onAnimationError() {
                successTextView.setText("Aborted!");
            }

            @Override
            public void onManualProgressStarted() {

            }

            @Override
            public void onManualProgressEnded() {

            }
        });
    }
}
