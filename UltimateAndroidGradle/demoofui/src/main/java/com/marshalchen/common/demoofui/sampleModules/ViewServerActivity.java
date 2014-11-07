package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.marshalchen.common.commonUtils.moduleUtils.ViewServer;
import com.marshalchen.common.demoofui.R;

public class ViewServerActivity extends Activity {
    private int mCounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_server_activity);

        if (getIntent().getExtras() != null) mCounter = getIntent().getExtras().getInt("counter");
        ((TextView) findViewById(R.id.viewServerActivityLabel)).setText("Activity #" + (mCounter + 1));

        ViewServer.get(this).addWindow(this);
    }

    public void nextActivity(View v) {
        Intent intent = new Intent(this, getClass());
        intent.putExtra("counter", mCounter + 1);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }
}
