package com.marshalchen.common.demoofui.cooldraganddrop;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.marshalchen.common.uimodule.cooldraganddrop.SpanVariableGridView;
import com.marshalchen.common.demoofui.R;

public class ItemAdapter extends ArrayAdapter<Item> implements SpanVariableGridView.CalculateChildrenPosition {

    private final class ItemViewHolder {

        public TextView itemTitle;
        public TextView itemDescription;
        public ImageView itemIcon;

    }

    private Context mContext;
    private LayoutInflater mLayoutInflater = null;

    private View.OnClickListener onRemoveItemListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Integer position = (Integer) view.getTag();
            removeItem(getItem(position));

        }
    };

    public void insertItem(Item item, int where) {

        if (where < 0 || where > (getCount() - 1)) {

            return;
        }

        insert(item, where);
    }

    public boolean removeItem(Item item) {

        remove(item);

        return true;
    }

    public ItemAdapter(Context context, List<Item> plugins) {

        super(context, R.layout.cooldrag_drop_item, plugins);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ItemViewHolder itemViewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.cooldrag_drop_item, parent, false);

            itemViewHolder = new ItemViewHolder();
            itemViewHolder.itemTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            itemViewHolder.itemDescription = (TextView) convertView.findViewById(R.id.textViewDescription);
            itemViewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            convertView.setTag(itemViewHolder);

        } else {

            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        final Item item = getItem(position);

        SpanVariableGridView.LayoutParams lp = new SpanVariableGridView.LayoutParams(convertView.getLayoutParams());
        lp.span = item.getSpans();
        convertView.setLayoutParams(lp);

        itemViewHolder.itemTitle.setText(item.getTitle());
        itemViewHolder.itemDescription.setText(item.getDescription());
        itemViewHolder.itemIcon.setImageResource(item.getIcon());

        return convertView;
    }

    @Override
    public void onCalculatePosition(View view, int position, int row, int column) {

    }
}
