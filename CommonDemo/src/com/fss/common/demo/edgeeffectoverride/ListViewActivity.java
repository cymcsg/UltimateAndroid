package com.fss.common.demo.edgeeffectoverride;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.fss.common.demo.R;
import com.fss.common.uiModule.edgeeffectoverride.EdgeEffectListView;

public class ListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edge_effect_listview_layout);

        ((EdgeEffectListView) findViewById(R.id.listview))
                .setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items_name)));
    }

}
