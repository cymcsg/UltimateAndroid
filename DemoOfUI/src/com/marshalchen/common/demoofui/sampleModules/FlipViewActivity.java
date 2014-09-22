package com.marshalchen.common.demoofui.sampleModules;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.uimodule.flipViews.flipview.FlipView;
import com.marshalchen.common.uimodule.flipViews.flipview.OverFlipMode;
import com.marshalchen.common.demoofui.R;

import java.util.ArrayList;
import java.util.List;

public class FlipViewActivity extends ActionBarActivity {

    @InjectView(R.id.flipViewListHorizontal)
    FlipView flipViewListHorizontal;
    @InjectView(R.id.flipViewListVertical)
    FlipView flipViewListVertical;
    private FlipAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.flip_view_activity);
        ButterKnife.inject(this);
        mAdapter = new FlipAdapter(this);
        mAdapter.addItemsBefore(5);
        //  mAdapter.setCallback(this);
        flipViewListHorizontal.setAdapter(mAdapter);
        //  flipViewList.setOnFlipListener(this);
        flipViewListHorizontal.peakNext(false);
        flipViewListHorizontal.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        flipViewListVertical.setAdapter(mAdapter);
        flipViewListVertical.peakNext(false);
        flipViewListVertical.setOverFlipMode(OverFlipMode.RUBBER_BAND);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        this.finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flip_view_menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_horizontal_flip_view:
                flipViewListVertical.setVisibility(View.GONE);
                flipViewListHorizontal.setVisibility(View.VISIBLE);
                return true;
            case R.id.menu_vertical_flip_view:
                flipViewListVertical.setVisibility(View.VISIBLE);
                flipViewListHorizontal.setVisibility(View.GONE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

class FlipAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private Callback callback;
    private List<Item> items = new ArrayList<Item>();

    public FlipAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 10; i++) {
            items.add(new Item());
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.flip_view_item, parent, false);

            holder.text = (TextView) convertView.findViewById(R.id.flipViewTextView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //TODO set a text with the id as well


        return convertView;
    }


    public void addItems(int amount) {
        for (int i = 0; i < amount; i++) {
            items.add(new Item());
        }
        notifyDataSetChanged();
    }

    public void addItemsBefore(int amount) {
        for (int i = 0; i < amount; i++) {
            items.add(0, new Item());
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView text;

    }

    interface Callback {
        public void onPageRequested(int page);
    }

    static class Item {
        static long id = 0;

        long mId;

        public Item() {
            mId = id++;
        }

        long getId() {
            return mId;
        }
    }

}
