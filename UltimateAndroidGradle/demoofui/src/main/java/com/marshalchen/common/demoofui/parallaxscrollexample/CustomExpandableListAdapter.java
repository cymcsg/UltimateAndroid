package com.marshalchen.common.demoofui.parallaxscrollexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;

	public CustomExpandableListAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		return "Group " + groupPosition + ", child " + childPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = (TextView) convertView;
		if (textView == null)
			textView = (TextView) inflater.inflate(R.layout.parallax_scroll_item_child, null);
		textView.setText(getChild(groupPosition, childPosition));
		return textView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groupPosition*2+1;
	}

	@Override
	public String getGroup(int groupPosition) {
		return "Group " + groupPosition;
	}

	@Override
	public int getGroupCount() {
		return 20;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		TextView textView = (TextView) convertView;
		if (textView == null)
			textView = (TextView) inflater.inflate(R.layout.parallax_scroll_item, null);
		textView.setText(getGroup(groupPosition));
		return textView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}
