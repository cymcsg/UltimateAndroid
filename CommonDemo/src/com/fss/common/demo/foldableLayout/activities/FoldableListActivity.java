package com.fss.common.demo.foldableLayout.activities;

import android.os.Bundle;

import com.fss.Common.uiModule.foldablelayout.FoldableListLayout;
import com.fss.common.demo.R;
import com.fss.common.demo.foldableLayout.items.PaintingsAdapter;

public class FoldableListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foldable_activity_foldable_list);

        FoldableListLayout foldableListLayout = (FoldableListLayout) findViewById(R.id.foldable_list);
        foldableListLayout.setAdapter(new PaintingsAdapter(this));
    }

}
