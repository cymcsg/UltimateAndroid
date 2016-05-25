package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.listviewanimations.ArrayAdapter;
import com.marshalchen.common.uimodule.simplemodule.FilckerAnimationListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cym on 14-10-14.
 */
public class FilckerAnimationListActivity extends Activity {
    private FilckerAnimationListView mListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filcker_anim_list_activity);
        mListView = (FilckerAnimationListView) findViewById(R.id.list_animation_filcker_list_view);
        mListView.setDivider(null);
        mListView.setAdapter(createListAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.manipulate(new FilckerAnimationListView.Manipulator<ArrayAdapter<String>>() {
                    @Override
                    public void manipulate(final ArrayAdapter<String> adapter) {
                        adapter.add("Foo");
                    }
                });
            }
        });
    }

    public ListView getListView() {
        return mListView;
    }

    protected ArrayAdapter<String> createListAdapter() {
        return new MyListAdapter(getItems(), this);
    }

    public static ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            items.add(i + "");
        }
        return items;
    }

    private static class MyListAdapter extends ArrayAdapter<String> {

        private final Context mContext;

//        public MyListAdapter(final Context context, final ArrayList<Integer> items) {
//            super(items);
//            mContext = context;
//        }

        private MyListAdapter(List<String> objects, Context mContext) {
            super(objects);
            this.mContext = mContext;
        }

        @Override
        public long getItemId(final int position) {
            return getItem(position).hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            TextView tv = (TextView) convertView;
            if (tv == null) {
                tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_anim_list_row, parent, false);
            }
            tv.setText("This is row number " + getItem(position));
            return tv;
        }
    }
}
