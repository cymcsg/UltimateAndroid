package com.marshalchen.common.demoofui.driveimageview;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.driveimageview.dialogs.AboutDialog;
import com.marshalchen.common.demoofui.driveimageview.fragments.MainFragment;


public class DriveImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_image_view_activity_main_fragment);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment);
        fragmentTransaction.commit();

//        getActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drive_image_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            case R.id.menu_item_help:
                AboutDialog aboutDialog = new AboutDialog(this);
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
