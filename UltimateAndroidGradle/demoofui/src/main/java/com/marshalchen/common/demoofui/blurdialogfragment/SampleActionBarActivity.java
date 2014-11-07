package com.marshalchen.common.demoofui.blurdialogfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.blurdialogfragment.SupportBlurDialogFragment;


public class SampleActionBarActivity extends ActionBarActivity implements View.OnClickListener {

    /**
     * Seek bar used to change the blur radius.
     */
    SeekBar mBlurRadiusSeekbar;

    /**
     * TextView used to display the current blur radius.
     */
    TextView mBlurRadiusTextView;

    /**
     * Prefix used to explain blur radius.
     */
    String mBlurPrefix;

    /**
     * Seek bar used to change the down scale factor.
     */
    SeekBar mDownScaleFactorSeekbar;

    /**
     * TextView used to display the current down scale factor.
     */
    TextView mDownScaleFactorTextView;

    /**
     * Checkbox used to enable or disable debug mode.
     */
    CheckBox mDebugMode;

    /**
     * Prefix used to explain down scale factor.
     */
    String mDownScalePrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_dialog_activity_sample);

        findViewById(R.id.button).setOnClickListener(this);
        mBlurRadiusTextView = ((TextView) findViewById(R.id.blurRadius));
        mBlurRadiusSeekbar = ((SeekBar) findViewById(R.id.blurRadiusSeekbar));
        mDownScaleFactorTextView = ((TextView) findViewById(R.id.downScalefactor));
        mDownScaleFactorSeekbar = ((SeekBar) findViewById(R.id.downScaleFactorSeekbar));
        mDebugMode = ((CheckBox) findViewById(R.id.debugMode));

        setUpView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blur_dialog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.actions_fullscreen) {
            startActivity(new Intent(this, SampleFullScreenActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                SampleSupportDialogFragment fragment = new SampleSupportDialogFragment();
                Bundle args = new Bundle();
                args.putInt(
                        SupportBlurDialogFragment.BUNDLE_KEY_BLUR_RADIUS,
                        mBlurRadiusSeekbar.getProgress()
                );
                args.putFloat(
                        SupportBlurDialogFragment.BUNDLE_KEY_DOWN_SCALE_FACTOR,
                        mDownScaleFactorSeekbar.getProgress()
                );
                fragment.setArguments(args);
                fragment.debug(mDebugMode.isChecked());
                fragment.show(getSupportFragmentManager(), "blur_sample");
                break;
            default:
                break;
        }
    }

    /**
     * Set up widgets.
     */
    private void setUpView() {

        mBlurRadiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlurRadiusTextView.setText(mBlurPrefix + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mDownScaleFactorSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDownScaleFactorTextView.setText(mDownScalePrefix + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBlurPrefix = "Blur radius : ";
        mDownScalePrefix = "Down Scale Factor : ";

        //set default blur radius to 8.
        mBlurRadiusSeekbar.setProgress(8);
        mDownScaleFactorSeekbar.setProgress(4);
    }
}
