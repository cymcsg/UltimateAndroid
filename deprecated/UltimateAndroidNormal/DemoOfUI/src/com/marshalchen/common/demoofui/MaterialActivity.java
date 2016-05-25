package com.marshalchen.common.demoofui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.commonUtils.urlUtils.HttpsUtils;
import com.marshalchen.common.ui.ToastUtil;
import com.marshalchen.common.uimodule.customFonts.CalligraphyContextWrapper;
import com.marshalchen.common.uimodule.nineoldandroids.animation.Animator;
import com.marshalchen.common.uimodule.shimmer.Shimmer;
import com.marshalchen.common.uimodule.shimmer.ShimmerTextView;


public class MaterialActivity extends ActionBarActivity {
    @InjectView(R.id.left_drawer_listview)
    ListView mDrawerList;
    private String[] mPlanetTitles;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_activity);
        ButterKnife.inject(this);
        mPlanetTitles = getResources().getStringArray(R.array.items_name);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.left_menu_layout, mPlanetTitles));

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        // Set an OnMenuItemClickListener to handle menu item clicks
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                // Handle the menu item
//                return true;
//            }
//        });

        // Inflate a menu to be displayed in the toolbar
//        toolbar.inflateMenu(R.menu.android_animations);
//        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_drawer);
        }
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.CLIP_VERTICAL);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logs.d("item---" + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
