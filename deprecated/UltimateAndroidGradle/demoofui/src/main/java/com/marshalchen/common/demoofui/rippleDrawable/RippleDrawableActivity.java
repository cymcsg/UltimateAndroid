package com.marshalchen.common.demoofui.rippleDrawable;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.rippleDrawable.RippleDrawable;

import java.util.ArrayList;
import java.util.List;


public class RippleDrawableActivity extends Activity{

    ColorStateList stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decor = getWindow().getDecorView();

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setPadding(20, 20, 20, 20);

        stateList = getResources().getColorStateList(R.color.ripple_drawable_example_colors_state);

        setContentView(recyclerView);

        recyclerView.setAdapter(new RecyclerAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(5);
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(), position + view.toString(), Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });


    }


    public class Holder extends RecyclerView.ViewHolder{

        TextView mNumberView;

        public Holder(View itemView) {
            super(itemView);
            mNumberView = (TextView) itemView.findViewById(R.id.number);
        }

        public void bindBackground(){
            mNumberView.setClickable(true);
            RippleDrawable.makeFor(mNumberView, stateList, true);
        }

        public void setText(Number number){
            mNumberView.setText( String.valueOf(number.intValue()) );
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<Holder>{

        public List<Integer> mItems = new ArrayList<Integer>();

        public RecyclerAdapter() {
            for(int index = 0; index < 100; index++){
                mItems.add(index);
            }
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup group, int i) {
            View v = LayoutInflater.from(group.getContext())
                    .inflate(R.layout.ripple_drawable_layout_item, group, false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(Holder holder, int i) {
            holder.setText(mItems.get(i));
            holder.bindBackground();
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

    class ListAdapter extends BaseAdapter{

        RecyclerAdapter mAdapter = new RecyclerAdapter();

        @Override
        public int getCount() {
            return mAdapter.getItemCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return mAdapter.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                Holder holder =  mAdapter.onCreateViewHolder(parent, position);
                convertView = holder.itemView;
                convertView.setTag(holder);
            }

            mAdapter.onBindViewHolder((Holder) convertView.getTag(), position);

            return convertView;
        }
    }

}
