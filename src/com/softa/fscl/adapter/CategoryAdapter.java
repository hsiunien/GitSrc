package com.softa.fscl.adapter;

import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.models.Category;
import com.softa.fscl.utils.ImageManager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater li;

	class Holder {
		ImageView imgv;
		TextView tv;
		
	}

	private Holder holder;

	private List<Category> mList;

	public CategoryAdapter(List<Category> list, Context context) {
		mList = list;
		this.context = context;
		li = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = li.inflate(R.layout.category_item_layout, null);
			holder = new Holder();
			holder.imgv = (ImageView) convertView.findViewById(R.id.iv);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Category c = mList.get(position);
		holder.tv.setText(c.getName());
		holder.imgv.setImageBitmap(c.getIcon());
		
		convertView.setTag(holder);
		// ImageManager.from(context).displayImage(holder.imgv,
		// "http://c.hiphotos.baidu.com/image/w%3D2048/sign=c3c069c1cb177f3e1034fb0d44f73bc7/aa18972bd40735faf4c5b5ce9c510fb30f24088b.jpg",
		// -1);
		return convertView;
	}

}
