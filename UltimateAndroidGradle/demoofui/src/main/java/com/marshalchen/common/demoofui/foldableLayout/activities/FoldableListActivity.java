package com.marshalchen.common.demoofui.foldableLayout.activities;

import android.os.Bundle;

import com.marshalchen.common.uimodule.foldablelayout.FoldableListLayout;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.foldableLayout.items.PaintingsAdapter;

public class FoldableListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foldable_activity_foldable_list);

        FoldableListLayout foldableListLayout = (FoldableListLayout) findViewById(R.id.foldable_list);
        foldableListLayout.setAdapter(new PaintingsAdapter(this));
    }

}
