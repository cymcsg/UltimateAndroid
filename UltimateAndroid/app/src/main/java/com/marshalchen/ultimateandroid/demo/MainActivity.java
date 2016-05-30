package com.marshalchen.ultimateandroid.demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marshalchen.ua.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.ua.common.commonUtils.logUtils.Timber;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    ListView mainActivityListView;
    List<Map<String, ?>> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listData = getData("com.marshalchen.ultimateandroid.demo");
        mainActivityListView = (ListView) findViewById(R.id.mainActivityListView);
        mainActivityListView.setAdapter(new ListviewAdapter(listData));
        mainActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity((Intent) listData.get(position).get("intent"));
            }
        });
        Timber.plant(new Timber.DebugTree());
    }

    class ListviewAdapter extends BaseAdapter {
        private List<Map<String, ?>> mapList = new ArrayList<>();

        @Override
        public int getCount() {
            Timber.d(mapList.size() + "   size   ");
            return mapList.size();
        }

        @Override
        public Object getItem(int position) {
            return mapList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = MainActivity.this.getLayoutInflater().inflate(R.layout.item_listview_activity_main, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.main_activity_listview_item_textview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mTextView.setText(mapList.get(position).get("title").toString());

            return convertView;
        }

        private class ViewHolder {
            TextView mTextView;
        }

        private ListviewAdapter(List<Map<String, ?>> mLists) {
            this.mapList = mLists;
        }
    }

    protected void addItem(List<Map<String, ?>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    protected List<Map<String, ?>> getData(String prefix) {
        List<Map<String, ?>> myData = new ArrayList<Map<String, ?>>();

        Intent intent = new Intent(prefix, null);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);

        //      addItem(myData, "rx", new Intent(this, RxbasicActivity.class));
        if (null == list)
            return myData;

        int len = list.size();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            String activityName = info.activityInfo.name;
            String[] labelPath = activityName.split("\\.");
            String nextLabel = labelPath[labelPath.length - 1];
            addItem(myData,
                    nextLabel,
                    activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
        }
        Collections.sort(myData, sDisplayNameComparator);
        return myData;
    }

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    private final static Comparator<Map<String, ?>> sDisplayNameComparator = new Comparator<Map<String, ?>>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map<String, ?> map1, Map<String, ?> map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };
}
