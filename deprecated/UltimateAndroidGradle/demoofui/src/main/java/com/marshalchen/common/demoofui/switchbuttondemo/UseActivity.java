package com.marshalchen.common.demoofui.switchbuttondemo;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.switchbutton.SwitchButton;


public class UseActivity extends Activity implements OnClickListener {

    private SwitchButton mListenerSb, mLongSb, mToggleSb, mCheckedSb;
    private ProgressBar mPb;
    private Button mStartBt, mToggleAniBt, mToggleNotAniBt, mCheckedAniBt, mCheckNotAniBt;
    private TextView mListenerFinish;
    private LongServiceReceiver longServiceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_button_activity_use);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        findView();

        // work with listener
        mListenerSb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListenerFinish.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        // work with stuff takes long
        longServiceReceiver = new LongServiceReceiver();
        IntentFilter recevierFilter = new IntentFilter();
        recevierFilter.addAction("LongServiceReceiver_ReturnIntent");
        recevierFilter.addAction("PROGRESS_UPDATE");
        LocalBroadcastManager.getInstance(this).registerReceiver(longServiceReceiver, recevierFilter);
        mStartBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent longService = new Intent(UseActivity.this, LongService.class);
                longServiceReceiver.onPreExecute();
                UseActivity.this.startService(longService);
            }
        });

        // toggle
        mToggleAniBt.setOnClickListener(this);
        mToggleNotAniBt.setOnClickListener(this);

        // checked
        mCheckedAniBt.setOnClickListener(this);
        mCheckNotAniBt.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (longServiceReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(longServiceReceiver);
    }

    private void findView() {
        mListenerSb = (SwitchButton) findViewById(R.id.sb_use_listener);
        mLongSb = (SwitchButton) findViewById(R.id.sb_use_long);
        mToggleSb = (SwitchButton) findViewById(R.id.sb_use_toggle);
        mCheckedSb = (SwitchButton) findViewById(R.id.sb_use_checked);

        mPb = (ProgressBar) findViewById(R.id.pb);
        mPb.setProgress(0);
        mPb.setMax(100);

        mStartBt = (Button) findViewById(R.id.long_start);
        mToggleAniBt = (Button) findViewById(R.id.toggle_ani);
        mToggleNotAniBt = (Button) findViewById(R.id.toggle_not_ani);
        mCheckedAniBt = (Button) findViewById(R.id.checked_ani);
        mCheckNotAniBt = (Button) findViewById(R.id.checked_not_ani);

        mListenerFinish = (TextView) findViewById(R.id.listener_finish);
        mListenerFinish.setVisibility(mListenerSb.isChecked() ? View.VISIBLE : View.INVISIBLE);
    }

    class LongServiceReceiver extends BroadcastReceiver {

        public void onPreExecute() {
            mLongSb.setChecked(false);
            mStartBt.setEnabled(false);
        }

        public void onProgressUpdate(Integer... values) {
            if (values == null || values.length == 0) {
                return;
            }
            int p = values[0];
            mPb.setProgress(p);
        }

        @Override
        public void onReceive(Context receiverContext, Intent receiverIntent) {
            if (receiverIntent.getAction().equals("PROGRESS_UPDATE")) {
                onProgressUpdate(receiverIntent.getIntExtra("progress", 0));
                return;
            }
            mLongSb.slideToChecked(true);
            mStartBt.setEnabled(true);
        }

    }

    public static class LongService extends IntentService {
        private int progress = 0;

        public LongService() {
            super("LongService");
        }

        public void onHandleIntent(Intent intent) {
            while (progress < 100) {
                progress++;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent updateIntent = new Intent("PROGRESS_UPDATE");
                updateIntent.putExtra("progress", progress);
                LocalBroadcastManager.getInstance(this).sendBroadcast(updateIntent);
            }
            Intent resultIntent = new Intent("LongServiceReceiver_ReturnIntent");
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.toggle_ani:
                mToggleSb.toggle();
                break;
            case R.id.toggle_not_ani:
                mToggleSb.toggle(false);
                break;
            case R.id.checked_ani:
                mCheckedSb.slideToChecked(!mCheckedSb.isChecked());
                break;
            case R.id.checked_not_ani:
                mCheckedSb.setChecked(!mCheckedSb.isChecked());
                break;

            default:
                break;
        }
    }
}
