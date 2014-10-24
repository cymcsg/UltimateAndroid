package com.marshalchen.common.demoofui.materialmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.materialmenu.MaterialMenuIconToolbar;


import static com.marshalchen.common.ui.materialmenu.MaterialMenuDrawable.Stroke;



public class MaterialMenuToolbarActivity extends ActionBarActivity {

    private MaterialMenuIconToolbar materialMenu;

    private int actionBarMenuState;

    protected BaseActivityHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_menu_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // random state
                actionBarMenuState = BaseActivityHelper.generateState(actionBarMenuState);
                materialMenu.animatePressedState(BaseActivityHelper.intToState(actionBarMenuState));
            }
        });
        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, Stroke.THIN) {
            @Override public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        materialMenu.setNeverDrawTouch(true);
        helper = new BaseActivityHelper();
        helper.init(getWindow().getDecorView(), materialMenu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        helper.refreshDrawerState();
        materialMenu.syncState(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        materialMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
