package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.patio.Patio;


public class PatioDemoActivity extends Activity implements Patio.PatioCallbacks {

    public static final int REQUEST_CODE_TAKE_PICTURE = 1000;
    public static final int REQUEST_CODE_ATTACH_PICTURE = 2000;

    private Patio mPatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patio_patio_activity_main);
        mPatio = (Patio) findViewById(R.id.patio);
        mPatio.setCallbacksListener(this);
    }

    @Override
    public void onTakePictureClick() {
        Intent intent = mPatio.getTakePictureIntent();
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    @Override
    public void onAddPictureClick() {
        Intent intent = mPatio.getAttachPictureIntent();
        startActivityForResult(intent, REQUEST_CODE_ATTACH_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_ATTACH_PICTURE) {
            mPatio.handleAttachPictureResult(data);
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_TAKE_PICTURE) {
            mPatio.handleTakePictureResult(data);
        }
    }
}
