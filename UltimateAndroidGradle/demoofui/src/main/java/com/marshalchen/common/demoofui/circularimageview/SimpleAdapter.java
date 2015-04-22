package com.marshalchen.common.demoofui.circularimageview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.CircularImageView;

public class SimpleAdapter extends BaseAdapter
{
	private Context mContext;
	private int[] mImages;
	
	public SimpleAdapter(Context context, int[] images) {
		this.mContext = context;
		this.mImages = images;
	}
	
	@Override
	public int getCount() {
		return mImages.length;
	}

	@Override
	public Integer getItem(int position) {
		return mImages[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		
		if (convertView == null)
		{
			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.circular_image_view_row, parent, false);
			
			holder = new ViewHolder();
			holder.imgAvatar = (CircularImageView) convertView.findViewById(R.id.imgAvatar);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		holder.imgAvatar.setImageResource(mImages[position]);
		
		holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://google.com")));
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder {
		public CircularImageView imgAvatar;
	}
}