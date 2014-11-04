package com.marshalchen.common.demoofui.BlurNavigationDrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.GridView;
import com.marshalchen.common.demoofui.R;

import java.util.ArrayList;


public class BlurNavigationDrawerActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_navigation_drawer_activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "title_section1";
                break;
            case 2:
                mTitle = "title_section2";
                break;
            case 3:
                mTitle = "title_section3";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.android_hover, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.app_name));
            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
            alertDialog.setMessage("Developed by Basilis Charalampakis\n\n" +
                    "GitHub     : http://github.com/charbgr\n" +
                    "Linkedin  : http://linkedin.com/in/charalampakisbasilis/");
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.blur_navigation_drawer_fragment_main, container, false);

            GridView gridView = (GridView) rootView.findViewById(R.id.gridview);

            Resources res = getActivity().getResources();

            ArrayList<Movie> movies = new ArrayList<Movie>();
            movies.add(new Movie(res.getDrawable(R.drawable.test), "About Time"));
            movies.add(new Movie(res.getDrawable(R.drawable.test_back), "Enders Game"));
            movies.add(new Movie(res.getDrawable(R.drawable.test_back1), "Enough Said"));
            movies.add(new Movie(res.getDrawable(R.drawable.test_back2), "The fifth estate"));
            movies.add(new Movie(res.getDrawable(R.drawable.test), "Gravity"));
            movies.add(new Movie(res.getDrawable(R.drawable.test_back), "Cloudy with a chance of meatballs"));
            movies.add(new Movie(res.getDrawable(R.drawable.test), "Prisoners"));
            movies.add(new Movie(res.getDrawable(R.drawable.test_back), "Runner Runner"));
            movies.add(new Movie(res.getDrawable(R.drawable.test), "The counselor"));
            movies.add(new Movie(res.getDrawable(R.drawable.test), "About Time"));


            gridView.setAdapter(new MovieAdapter(getActivity(), movies));

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BlurNavigationDrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
