package com.marshalchen.common.demoofui.snackbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.marshalchen.common.demoofui.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;

public class SnackbarNavigationBarTranslucentSampleActivity extends ActionBarActivity {

    private static final String TAG = SnackbarNavigationBarTranslucentSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack_bar_activity_navigation_bar_translucent_sample);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableTransparentSystemBars(getWindow());

        Button singleLineButton = (Button) findViewById(R.id.single_line);
        singleLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarNavigationBarTranslucentSampleActivity.this)
                                .text("Single-line snackbar"));
            }
        });

        Button multiLineButton = (Button) findViewById(R.id.multi_line);
        multiLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarNavigationBarTranslucentSampleActivity.this)
                                .type(SnackbarType.MULTI_LINE)
                                .text("This is a multi-line snackbar. Keep in mind that snackbars" +
                                        " are meant for VERY short messages"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disableTransparentSystemBars(getWindow());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean isTranslucentSystemBarsCapable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void enableTransparentSystemBars(Window window) {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void disableTransparentSystemBars(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
