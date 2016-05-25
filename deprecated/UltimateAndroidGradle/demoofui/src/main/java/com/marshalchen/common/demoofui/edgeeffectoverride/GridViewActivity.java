package com.marshalchen.common.demoofui.edgeeffectoverride;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.edgeeffectoverride.EdgeEffectGridView;


public class GridViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edge_effect_gridview_layout);

        ((EdgeEffectGridView) findViewById(R.id.gridview))
                .setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.countries)));
    }

}
