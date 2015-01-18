package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.simplemodule.DraggableFlagView;

public class DraggableFlagViewActivity extends Activity implements DraggableFlagView.OnDraggableFlagViewListener, View.OnClickListener {

    private DraggableFlagView draggableFlagView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draggable_flag_activity_main);
        findViewById(R.id.main_btn).setOnClickListener(this);

        draggableFlagView = (DraggableFlagView) findViewById(R.id.main_dfv);
        draggableFlagView.setOnDraggableFlagViewListener(this);
        draggableFlagView.setText("7");


    }


    @Override
    public void onFlagDismiss(DraggableFlagView view) {
        Toast.makeText(this, "onFlagDismiss", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn:
                draggableFlagView.setText("7");
                break;
        }
    }
}
