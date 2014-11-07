package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.ui.HomeasUpActionbarActivity;
import com.marshalchen.common.uimodule.panningview.PanningView;
import com.marshalchen.common.demoofui.R;

public class PanningViewActivity extends HomeasUpActionbarActivity {
    @InjectView(R.id.panningView)
    PanningView panningView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panning_view_activity);
        ButterKnife.inject(this);
        panningView.startPanning();


    }


}
