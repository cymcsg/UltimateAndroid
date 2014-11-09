package com.marshalchen.common.demoofui.materialmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.materialmenu.MaterialMenuView;


import static com.marshalchen.common.demoofui.materialmenu.BaseActivityHelper.generateState;
import static com.marshalchen.common.demoofui.materialmenu.BaseActivityHelper.intToState;

public class MaterialMenuAppcompatActivity extends BaseActivity implements View.OnClickListener {

    private MaterialMenuView materialMenu;
    private int              actionBarMenuState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCustomActionBar();
        helper.init(getWindow().getDecorView(), materialMenu);
    }

    private void initCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.material_menu_action_bar);
        materialMenu = (MaterialMenuView) actionBar.getCustomView().findViewById(R.id.action_bar_menu);
        materialMenu.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        if (v.getId() == R.id.action_bar_menu) {
            // random state on click
            actionBarMenuState = generateState(actionBarMenuState);
            materialMenu.animatePressedState(intToState(actionBarMenuState));
            return;
        }
        helper.onClick(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.material_menu_custom_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(this, HomeIconActivity.class));
//            finish();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
