/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.risenumber.RiseNumberTextView;


public class RiseNumberActivity extends Activity {

    private RiseNumberTextView mTextViewAll;
    private RiseNumberTextView mTextViewIn;
    private RiseNumberTextView mTextViewOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rise_number_activity);

        mTextViewAll = (RiseNumberTextView) findViewById(R.id.text);
        mTextViewIn = (RiseNumberTextView) findViewById(R.id.in);
        mTextViewOut = (RiseNumberTextView) findViewById(R.id.out);

        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init();

            }
        });

        init();

    }

    private void init() {
        mTextViewAll.withNumber(46000).start();
        mTextViewIn.withNumber(489.15f).start();
        mTextViewOut.withNumber(367.24f).start();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
