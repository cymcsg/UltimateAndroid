package com.fss.commons.demo.sampleModules;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.fss.commons.ui.HomeasUpActionbarActivity;
import com.fss.commons.uiModule.panningview.PanningView;
import com.fss.commons.demo.R;

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
