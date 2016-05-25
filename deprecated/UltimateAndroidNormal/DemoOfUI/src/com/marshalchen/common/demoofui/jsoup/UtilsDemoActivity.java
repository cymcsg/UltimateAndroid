package com.marshalchen.common.demoofui.jsoup;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.demoofui.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by cym on 14-7-19.
 */
public class UtilsDemoActivity extends Activity {
    private static final String TITLE_FILTER = "UtilsDemoActivity_titleReceiver";
    String url = "http://blog.marshalchen.com/";
    @InjectView(R.id.jsoupTextView)
    TextView mJsoupTextView;
    Title titleReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup_activity);
        ButterKnife.inject(this);
        titleReceiver = new Title();
        LocalBroadcastManager.getInstance(this).registerReceiver(titleReceiver, new IntentFilter(TITLE_FILTER));
        Intent title = new Intent(this, TitleService.class);
        title.putExtra("url", url);
        this.startService(title);
        mJsoupTextView.setText(UtilsDemo.TestJsoup());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (titleReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(titleReceiver);
    }

    private class Title extends BroadcastReceiver {
        @Override
        public void onReceive(Context receiverContext, Intent receiverIntent) {
            String title = receiverIntent.getStringExtra("title");
            // Set title into TextView
            Logs.d("title---" + title);
//            TextView txttitle = (TextView) findViewById(R.id.titletxt);
//            txttitle.setText(title);

        }
    }

    // Title IntentService
    public static class TitleService extends IntentService {
        private String url;
        private String title;

        public TitleService() {
            super("TitleService");
        }

        public void onHandleIntent(Intent intent) {
            this.url = intent.getStringExtra("url");
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get the html document title
                title = document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent resultIntent = new Intent(TITLE_FILTER);
            resultIntent.putExtra("title", title);
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        }
    }
}
