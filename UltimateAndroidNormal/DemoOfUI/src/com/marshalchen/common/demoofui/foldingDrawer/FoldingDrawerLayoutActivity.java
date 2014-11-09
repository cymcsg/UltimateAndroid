/*
 * Copyright 2013 Priboi Tiberiu
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.demoofui.foldingDrawer;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.marshalchen.common.commonUtils.basicUtils.HandlerUtils;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.uimodule.foldingLayout.FoldingDrawerLayout;
import com.marshalchen.common.demoofui.R;


/**
 * This example illustrates a common usage of the FoldingDrawerLayout widget to
 * add Folding Efect to DrawerLayout from Android support library.
 * <p/>
 * <p>
 * When a navigation (left) drawer is present, the host activity should detect
 * presses of the action bar's Up affordance as a signal to open and close the
 * navigation drawer. The ActionBarDrawerToggle facilitates this behavior. Items
 * within the drawer should fall into one of two categories:
 * </p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic
 * policies as list or tab navigation in that a view switch does not create
 * navigation history. This pattern should only be used at the root activity of
 * a task, leaving some form of Up navigation active for activities further down
 * the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an
 * alternate parent for Up navigation. This allows a user to jump across an
 * app's navigation hierarchy at will. The application should treat this as it
 * treats Up navigation from a different task, replacing the current task stack
 * using TaskStackBuilder or similar. This is the only form of navigation drawer
 * that should be used outside of the root activity of a task.</li>
 * </ul>
 * <p/>
 * <p>
 * Right side drawers should be used for actions, not navigation. This follows
 * the pattern established by the Action Bar that navigation should be to the
 * left and actions to the right. An action should be an operation performed on
 * the current contents of the window, for example enabling or disabling a data
 * overlay on top of the current content.
 * </p>
 */
public class FoldingDrawerLayoutActivity extends ActionBarActivity {

    private FoldingDrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mAnimalTitles;
    private int numOfFolder = 5;
    private ItemSelectedListener mItemSelectedListener;

    static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folding_drawer_activity_drawer);

        mTitle = mDrawerTitle = getTitle();
        mAnimalTitles = getResources().getStringArray(R.array.items_name);
        mDrawerLayout = (FoldingDrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // mFoldLayout = (FoldingNavigationLayout)findViewById(R.id.fold_view);
        // mFoldLayout.setBackgroundColor(Color.BLACK);

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);


        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.left_menu_layout, mAnimalTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mItemSelectedListener = new ItemSelectedListener();

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {

            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                if (IS_HONEYCOMB) {
                    invalidateOptionsMenu(); // creates call to
                    // onPrepareOptionsMenu()
                }

            }

            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                if (IS_HONEYCOMB) {
                    invalidateOptionsMenu(); // creates call to
                    // onPrepareOptionsMenu()
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
//        try {
//            mDrawerLayout.getFoldingLayout(mDrawerList).setNumberOfFolds(
//                    numOfFolder);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Logs.e(e, "" + (mDrawerLayout != null) + "   " + (mDrawerList != null)
//                    + "  " + (mDrawerLayout.getFoldingLayout(mDrawerList) != null));
//        }
        HandlerUtils.sendMessageHandlerDelay(getNumHandler, 0, 1000);

    }

    Handler getNumHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Logs.d("tttt");
                mDrawerLayout.getFoldingLayout(mDrawerList).setNumberOfFolds(
                        numOfFolder);
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "" + (mDrawerLayout != null) + "   " + (mDrawerList != null)
                        + "  " + (mDrawerLayout.getFoldingLayout(mDrawerList) != null));
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.menu_spinner, menu);
//
//		MenuItem spinerItem = menu.findItem(R.id.num_of_folds);
//		Spinner s = (Spinner) MenuItemCompat.getActionView(spinerItem);
//
//		s.setOnItemSelectedListener(mItemSelectedListener);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
            mDrawerLayout.getFoldingLayout(mDrawerList).setNumberOfFolds(
                    position + 3);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new AnimalFragment();
        Bundle args = new Bundle();
        args.putInt(AnimalFragment.ARG_ANIMAL_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mAnimalTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Listens for selection events of the spinner located on the action bar.
     * Every time a new value is selected, the number of folds in the folding
     * view is updated and is also restored to a default unfolded state.
     */
    private class ItemSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {
            int mNumberOfFolds = Integer.parseInt(parent.getItemAtPosition(pos)
                    .toString());

            mDrawerLayout.getFoldingLayout(mDrawerList).setNumberOfFolds(
                    mNumberOfFolds);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}