package com.marshalchen.common.demoofui.materialmenu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.materialmenu.MaterialMenuIconCompat;


import static com.marshalchen.common.ui.materialmenu.MaterialMenuDrawable.Stroke;
import static com.marshalchen.common.demoofui.materialmenu.BaseActivityHelper.generateState;
import static com.marshalchen.common.demoofui.materialmenu.BaseActivityHelper.intToState;


public class HomeIconActivity extends BaseActivity {

    private MaterialMenuIconCompat materialMenu;
    private int                    actionBarMenuState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialMenu = new MaterialMenuIconCompat(this, Color.WHITE, Stroke.THIN);
        helper.init(getWindow().getDecorView(), materialMenu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        materialMenu.syncState(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        materialMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.material_menu_home_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MaterialMenuAppcompatActivity.class));
            finish();
            return true;
        }
        if (id == android.R.id.home) {
            // random state
            actionBarMenuState = generateState(actionBarMenuState);
            materialMenu.animatePressedState(intToState(actionBarMenuState));
        }
        return super.onOptionsItemSelected(item);
    }
}
