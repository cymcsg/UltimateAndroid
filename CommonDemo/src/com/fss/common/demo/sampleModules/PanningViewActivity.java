package com.fss.common.demo.sampleModules;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.fss.common.ui.HomeasUpActionbarActivity;
import com.fss.common.uiModule.panningview.PanningView;
import com.fss.common.demo.R;

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
