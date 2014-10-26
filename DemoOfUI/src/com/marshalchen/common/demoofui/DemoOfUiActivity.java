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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.commonUtils.urlUtils.HttpsUtils;
import com.marshalchen.common.uimodule.customFonts.CalligraphyContextWrapper;
import com.marshalchen.common.uimodule.nineoldandroids.animation.Animator;
import com.marshalchen.common.ui.ToastUtil;
import com.marshalchen.common.uimodule.shimmer.Shimmer;
import com.marshalchen.common.uimodule.shimmer.ShimmerTextView;


public class DemoOfUiActivity extends ActionBarActivity {
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mPlanetTitles;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer_listview)
    ListView mDrawerList;
    private Fragment mContent;
    @InjectView(R.id.favShimmerTextView)
    ShimmerTextView favShimmerTextView;
    Shimmer shimmer;
    @InjectView(R.id.main_content_frame)
    View main_content_frame;
    @InjectView(R.id.favShimmerReaLayout)
    View favShimmerReaLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_landing_activity);
        ButterKnife.inject(this);
        mContent = new LandingFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content_frame, mContent).commit();
        initViews();
        initShimmerTextView();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Logs.d("upTask");
        //   finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    //   @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_layout, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ToastUtil.show(DemoOfUiActivity.this, "test~", Toast.LENGTH_SHORT);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToastUtil.show(MainActivity.this,"show~", Toast.LENGTH_SHORT);
            }
        });

//        menu.findItem(R.id.action_search).getActionView() > API 11
//        if (searchItem != null) {
//            searchItem.collapseActionView();
//        }
        return super.onCreateOptionsMenu(menu);

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private <T> void selectItems(T T) {
//        Fragment fragment = new PrologueFragment();
//        Bundle args = new Bundle();
//        //  args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.main_content_frame, fragment)
//                .commit();
//        mDrawerLayout.closeDrawer(mDrawerList);

    }

    private void selectItem(int position) {

        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(title);
    }

    private void initViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(false);
        mPlanetTitles = getResources().getStringArray(R.array.items_name);
        mTitle = mDrawerTitle = getTitle();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        Logs.d("mDrawerLayout  " + (mDrawerLayout != null)
                + "    " + "mDrawerToggle  " + (mDrawerToggle != null));
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.CLIP_VERTICAL);
        //mDrawerLayout.setScrimColor(getResources().getColor(R.color.babyBlueColor));
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.left_menu, mPlanetTitles));
        //mDrawerList.setAdapter(new SimpleAdapter(this,null,R.layout.left_menu_layout,null,null));
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.left_menu_layout, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // getSupportParentActivityIntent();
    }

    private void testHttps() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpsUtils.sendWithSSlSocketWithCrt(DemoOfUiActivity.this, "ca.crt", "https://xxx.xxx");
            }
        }.start();
    }

    private void initShimmerTextView() {
        main_content_frame.setVisibility(View.INVISIBLE);
        shimmer = new Shimmer();
        shimmer.setRepeatCount(0)
                .setDuration(800)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
                .setAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        main_content_frame.setVisibility(View.VISIBLE);
                        main_content_frame.startAnimation(AnimationUtils.loadAnimation(DemoOfUiActivity.this, R.anim.fade_ins));
                        favShimmerReaLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
        shimmer.start(favShimmerTextView);
    }


}
