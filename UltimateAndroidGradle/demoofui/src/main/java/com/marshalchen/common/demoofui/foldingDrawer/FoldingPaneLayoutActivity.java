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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.marshalchen.common.uimodule.foldingLayout.FoldingPaneLayout;
import com.marshalchen.common.demoofui.R;


/**
 * This example illustrates a common usage of the FoldingPaneLayout widget to
 * add fold effect to SlidingPaneLayout from Android support library.
 */
public class FoldingPaneLayoutActivity extends ActionBarActivity {

    private FoldingPaneLayout mPaneLayout;
    private ListView mPaneList;
    private CharSequence mTitle;
    private String[] mAnimalTitles;
    private ItemSelectedListener mItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folding_drawer_activity_pane);

        mAnimalTitles = getResources().getStringArray(R.array.items_name);
        mPaneLayout = (FoldingPaneLayout) findViewById(R.id.drawer_layout);
        mPaneList = (ListView) findViewById(R.id.left_drawer);

        mItemSelectedListener = new ItemSelectedListener();

        mPaneLayout.getFoldingLayout().setBackgroundColor(Color.BLACK);

        // set up the drawer's list view with items and click listener
        mPaneList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.left_menu_layout, mAnimalTitles));
        mPaneList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        mPaneLayout.getFoldingLayout().setNumberOfFolds(5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_layout, menu);

//		MenuItem spinerItem = menu.findItem(R.id.num_of_folds);
//		Spinner s = (Spinner) MenuItemCompat.getActionView(spinerItem);
//
//		s.setOnItemSelectedListener(mItemSelectedListener);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        // view

        return super.onPrepareOptionsMenu(menu);
    }

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		// Handle action buttons
//		switch (item.getItemId()) {
//		case R.id.action_websearch:
//			// create intent to perform web search for this planet
//			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//			intent.putExtra(SearchManager.QUERY, getSupportActionBar()
//					.getTitle());
//			// catch event that there's no activity to handle intent
//			if (intent.resolveActivity(getPackageManager()) != null) {
//				startActivity(intent);
//			} else {
//				Toast.makeText(this, R.string.app_not_available,
//						Toast.LENGTH_LONG).show();
//			}
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (mPaneLayout.isOpen()) {
                selectItem(position);
            }
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
        mPaneList.setItemChecked(position, true);
        setTitle(mAnimalTitles[position]);
        mPaneLayout.closePane();
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

            mPaneLayout.getFoldingLayout().setNumberOfFolds(mNumberOfFolds);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}