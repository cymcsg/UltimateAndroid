package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.discreteseekbar.DiscreteSeekBar;


public class DiscreteSeekBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discrete_seek_bar_activity_main);
        DiscreteSeekBar discreteSeekBar1 = (DiscreteSeekBar) findViewById(R.id.discrete1);
        discreteSeekBar1.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * 100;
            }
        });
    }

}
