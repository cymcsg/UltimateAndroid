package com.fss.commons.demo.foldableLayout.activities;

import android.os.Bundle;

import com.fss.commons.uiModule.foldablelayout.FoldableListLayout;
import com.fss.commons.demo.R;
import com.fss.commons.demo.foldableLayout.items.PaintingsAdapter;

public class FoldableListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foldable_activity_foldable_list);

        FoldableListLayout foldableListLayout = (FoldableListLayout) findViewById(R.id.foldable_list);
        foldableListLayout.setAdapter(new PaintingsAdapter(this));
    }

}
