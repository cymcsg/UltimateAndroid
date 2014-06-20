package com.fss.common.demo.dynamicgrid.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.fss.Common.uiModule.dynamicgrid.DynamicGridView;
import com.fss.common.demo.R;


import java.util.ArrayList;
import java.util.Arrays;

public class DynamicGridActivity extends Activity {

    private DynamicGridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_grid_view_activity_grid);
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid_view);
        gridView.setAdapter(new CheeseDynamicAdapter(this,
                new ArrayList<String>(Arrays.asList(Cheeses.sCheeseStrings)),
                3));
//        add callback to stop edit mode if needed
//        gridView.setOnDropListener(new DynamicGridView.OnDropListener()
//        {
//            @Override
//            public void onActionDrop()
//            {
//                gridView.stopEditMode();
//            }
//        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode();
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DynamicGridActivity.this, parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        } else {
            super.onBackPressed();
        }
    }
}
