package com.marshalchen.common.demoofui.sampleModules;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.DropDownListView;
import com.marshalchen.common.ui.ToastUtil;


/**
 * DropDownListViewDemo
 *
 * @author <a href="http://www.trinea.cn/android/dropdown-to-refresh-and-bottom-load-more-listview/"
 *         target="_blank">Trinea</a> 2013-6-1
 */
public class DropDownListViewDemo extends Activity {

    private LinkedList<String> listItems = null;
    private DropDownListView listView = null;
    private ArrayAdapter<String> adapter;

    private String[] mStrings = {"Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee", "Ffffff",
            "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm", "Nnnnnn",};
    public static final int MORE_DATA_MAX_COUNT = 3;
    public int moreDataCount = 0;

    private GetDataReceiver onDropDownReceiver;
    private GetDataReceiver onClickReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState, R.layout.drop_down_listview_demo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drop_down_listview_demo);
        listView = (DropDownListView) findViewById(R.id.list_view);
        // set drop down listener
        onDropDownReceiver = new GetDataReceiver(true);
        LocalBroadcastManager.getInstance(this).registerReceiver(onDropDownReceiver,
                new IntentFilter("onDropDownReceiver"));
        listView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {

            @Override
            public void onDropDown() {
                Intent getData = new Intent(DropDownListViewDemo.this, GetDataService.class);
                getData.putExtra("FILTER", "onDropDownReceiver");
                DropDownListViewDemo.this.startService(getData);
            }
        });

        // set on bottom listener
        onClickReceiver = new GetDataReceiver(false);
        LocalBroadcastManager.getInstance(this).registerReceiver(onClickReceiver,
                new IntentFilter("onClickReceiver"));
        listView.setOnBottomListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent getData = new Intent(DropDownListViewDemo.this, GetDataService.class);
                getData.putExtra("FILTER", "onClickReceiver");
                DropDownListViewDemo.this.startService(getData);
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.show(DropDownListViewDemo.this, "drop_down_tip");
            }
        });
        // listView.setShowFooterWhenNoMore(true);

        listItems = new LinkedList<String>();
        listItems.addAll(Arrays.asList(mStrings));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (onClickReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(onClickReceiver);
        if (onDropDownReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(onDropDownReceiver);
    }

    private class GetDataReceiver extends BroadcastReceiver {

        private boolean isDropDown;

        public GetDataReceiver(boolean isDropDown) {
            this.isDropDown = isDropDown;
        }

        @Override
        public void onReceive(Context receiverContext, Intent receiverIntent) {

            if (isDropDown) {
                listItems.addFirst("Added after drop down");
                adapter.notifyDataSetChanged();

                // should call onDropDownComplete function of DropDownListView at end of drop down complete.
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                listView.onDropDownComplete("update_at" + dateFormat.format(new Date()));
            } else {
                moreDataCount++;
                listItems.add("Added after on bottom");
                adapter.notifyDataSetChanged();

                if (moreDataCount >= MORE_DATA_MAX_COUNT) {
                    listView.setHasMore(false);
                }

                // should call onBottomComplete function of DropDownListView at end of on bottom complete.
                listView.onBottomComplete();
            }
        }
    }

    public static class GetDataService extends IntentService {

        public GetDataService() {
            super("GetDataService");
        }

        public void onHandleIntent(Intent intent) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                ;
            }
            Intent resultIntent = new Intent(intent.getStringExtra("FILTER"));
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        }
    }
}
