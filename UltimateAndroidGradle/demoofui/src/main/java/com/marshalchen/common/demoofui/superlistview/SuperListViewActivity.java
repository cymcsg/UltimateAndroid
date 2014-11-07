package com.marshalchen.common.demoofui.superlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.superlistview.SuperListview;


import java.util.ArrayList;


public class SuperListViewActivity extends Activity {

    private SuperListview mList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_list_view_activity_main);

        ArrayList<String> lst = new ArrayList<String>();
        lst.add("List example");
        lst.add("Grid example");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);
        mList = (SuperListview) findViewById(R.id.list);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                switch (position) {
                    case 0:
                        i.setClass(SuperListViewActivity.this, ListSample.class);
                        break;
                    case 1:
                        i.setClass(SuperListViewActivity.this, GridSample.class);
                }
                startActivity(i);
            }
        });
    }
}
