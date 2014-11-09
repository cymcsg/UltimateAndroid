package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.usefulModule.BasicUltimateListView;
import com.marshalchen.common.usefulModule.UltimateListview;

/**
 * Created by cym on 14-9-15.
 */
public class UltimateListviewActivity extends Activity {
    ArrayAdapter<String> adapter;
    UltimateListview ultimateListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ultimate_list_view_temp_activity);
        ultimateListview = (UltimateListview) findViewById(R.id.ultimateListview);
        adapter = new ArrayAdapter<String>(this, R.layout.ultimate_list_view_activity_item,
                getResources().getStringArray(R.array.items_name));


        ultimateListview.setAdapter(adapter);
        ultimateListview.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ultimateListview.getSwipeRefreshLayout().setEnabled(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ultimateListview.getSwipeRefreshLayout().setRefreshing(false);
                    }
                }, 1000);
            }
        });
        ultimateListview.getListView().setOnLoadMoreListener(new BasicUltimateListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                Logs.d("loadMore");
            }
        });
    }
}
