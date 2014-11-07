package com.marshalchen.common.demoofui.parallaxscrollexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;

public class CustomListAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	public CustomListAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		return 20;
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
		TextView textView = (TextView) convertView;
		if (textView == null)
			textView = (TextView) inflater.inflate(R.layout.parallax_scroll_item, null);
		textView.setText("Item " + position);
		return textView;
	}
}
