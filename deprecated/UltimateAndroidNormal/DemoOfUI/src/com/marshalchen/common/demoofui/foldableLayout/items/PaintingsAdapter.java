package com.marshalchen.common.demoofui.foldableLayout.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.foldableLayout.activities.UnfoldableDetailsActivity;


import java.util.Arrays;

public class PaintingsAdapter extends BaseAdapter {
    Context mContext;

    public PaintingsAdapter(Context context) {
        super();
        mContext = context;
    }
//    public PaintingsAdapter(Context context) {
//        super(context);
//       // setItemsList(Arrays.asList(Painting.getAllPaintings(context.getResources())));
//    }


    @Override
    public int getCount() {
        return Arrays.asList(Painting.getAllPaintings(mContext.getResources())).size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foldable_list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = (ImageView) view.findViewById(R.id.list_item_image);
        view.setTag(vh);
        if (position % 2 == 1) {
            vh.image.setImageResource(R.drawable.test_back);
        } else {
            vh.image.setImageResource(R.drawable.test);
        }

        vh.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext() instanceof UnfoldableDetailsActivity) {
                    UnfoldableDetailsActivity activity = (UnfoldableDetailsActivity) v.getContext();
                    activity.openDetails(v, (Painting) v.getTag());
                }
            }
        });
        vh.title = (TextView) view.findViewById(R.id.list_item_title);
        view.setTag(vh);

        return view;
    }


    private static class ViewHolder {
        ImageView image;
        TextView title;
    }

}
