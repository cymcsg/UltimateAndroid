package com.marshalchen.common.demoofui.showcaseview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.marshalchen.common.uimodule.showcaseview.ApiUtils;
import com.marshalchen.common.uimodule.showcaseview.OnShowcaseEventListener;
import com.marshalchen.common.uimodule.showcaseview.ShowcaseView;
import com.marshalchen.common.uimodule.showcaseview.targets.ViewTarget;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.showcaseview.animations.AnimationSampleActivity;


public class ShowCaseSampleActivity extends Activity implements View.OnClickListener,
        OnShowcaseEventListener, AdapterView.OnItemClickListener {

    private static final float ALPHA_DIM_VALUE = 0.1f;

    ShowcaseView sv;
    Button buttonBlocked;
    ListView listView;

    private final ApiUtils apiUtils = new ApiUtils();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcase_activity);

        HardcodedListAdapter adapter = new HardcodedListAdapter(this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        buttonBlocked = (Button) findViewById(R.id.buttonBlocked);
        buttonBlocked.setOnClickListener(this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        ViewTarget target = new ViewTarget(R.id.buttonBlocked, this);
        sv = new ShowcaseView.Builder(this, true)
                .setTarget(target)
                .setContentTitle("showcase_main_title")
                .setContentText("showcase_main_message")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setShowcaseEventListener(this)
                .build();
        sv.setButtonPosition(lps);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void dimView(View view) {
        if (apiUtils.isCompatWithHoneycomb()) {
            view.setAlpha(ALPHA_DIM_VALUE);
        }
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        switch (viewId) {
            case R.id.buttonBlocked:
                if (sv.isShown()) {
                    sv.setStyle(R.style.CustomShowcaseTheme);
                } else {
                    sv.show();
                }
                break;
        }
    }

    @Override
    public void onShowcaseViewHide(ShowcaseView showcaseView) {
        if (apiUtils.isCompatWithHoneycomb()) {
            listView.setAlpha(1f);
        }
        buttonBlocked.setText("button_show");
        //buttonBlocked.setEnabled(false);
    }

    @Override
    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
    }

    @Override
    public void onShowcaseViewShow(ShowcaseView showcaseView) {
        dimView(listView);
        buttonBlocked.setText("button_hide");
        //buttonBlocked.setEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {

            case 0:
                startActivity(new Intent(this, AnimationSampleActivity.class));
                break;

            case 1:
                startActivity(new Intent(this, AnimationSampleActivity.class));
                break;

            case 2:
                startActivity(new Intent(this, SingleShotActivity.class));
                break;

            // Not currently used
            case 3:
                startActivity(new Intent(this, MemoryManagementTesting.class));
        }
    }

    private static class HardcodedListAdapter extends ArrayAdapter {

        private static final String[] TITLE_RES_IDS = new String[] {
                "title_action_items",
                "title_animations",
                "title_single_shot"//, "title_memory"
        };

        private static final String[] SUMMARY_RES_IDS = new String[] {
                "sum_action_items",
                "sum_animations",
                "sum_single_shot"//, "sum_memory"
        };

        public HardcodedListAdapter(Context context) {
            super(context, R.layout.showcase_item_next_thing);
        }

        @Override
        public int getCount() {
            return TITLE_RES_IDS.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.showcase_item_next_thing, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.textView)).setText(TITLE_RES_IDS[position]);
            ((TextView) convertView.findViewById(R.id.textView2)).setText(SUMMARY_RES_IDS[position]);
            return convertView;
        }
    }

}
