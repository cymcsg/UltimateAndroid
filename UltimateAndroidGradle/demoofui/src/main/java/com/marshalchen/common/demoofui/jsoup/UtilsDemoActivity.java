package com.marshalchen.common.demoofui.jsoup;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
    String url = "http://blog.marshalchen.com/";
    @InjectView(R.id.jsoupTextView)
    TextView mJsoupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup_activity);
        ButterKnife.inject(this);
        new Title().execute();
        mJsoupTextView.setText(UtilsDemo.TestJsoup());
    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get the html document title
                title = document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            Logs.d("title---" + title);
//            TextView txttitle = (TextView) findViewById(R.id.titletxt);
//            txttitle.setText(title);

        }
    }


}
