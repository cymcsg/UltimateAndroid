package com.marshalchen.common.demoofui.edgeeffectoverride;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleExpandableListAdapter;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.edgeeffectoverride.EdgeEffectExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExpandableListViewActivity extends Activity {

    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edge_effect_expandablelistview_layout);

        //this comes from android samples
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < 20; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, "Group " + i);
            curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < 15; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, "Child " + j);
                curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
            }
            childData.add(children);
        }

        // Set up our adapter
        ((EdgeEffectExpandableListView) findViewById(R.id.expandablelistview)).setAdapter(new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{NAME, IS_EVEN},
                new int[]{android.R.id.text1, android.R.id.text2},
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{NAME, IS_EVEN},
                new int[]{android.R.id.text1, android.R.id.text2}
        ));
    }


}
