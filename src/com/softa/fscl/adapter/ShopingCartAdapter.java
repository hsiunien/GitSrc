package com.softa.fscl.adapter;

import java.util.List;


import com.softa.fscl.R;
import com.softa.fscl.models.Category;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.utils.ImageManager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopingCartAdapter extends BaseAdapter   {
	private Context context;
	private LayoutInflater li;	
	private OnClickListener listener;
	class Holder {
		ImageView iv_pic;
		TextView tv_title,tv_price,tv_color,tv_number;
		Button btn_del;
		int position;
		
	}

	private Holder holder;

	public void setOnDelListener(OnClickListener listener){
		this.listener=listener;
	}
	private List<ShopCartItem> mList;
	public ShopingCartAdapter(List<ShopCartItem> list,Context context) {
		mList=list;
		this.context = context;
		li=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return mList==null?0:mList.size();
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
		if(convertView==null){
			convertView = li.inflate(R.layout.shopcart_list_item, null);
			holder=new Holder();
			holder.iv_pic=(ImageView) convertView.findViewById(R.id.iv_pic);
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_color=(TextView) convertView.findViewById(R.id.tv_color);
			holder.tv_number=(TextView) convertView.findViewById(R.id.tv_number);
			holder.btn_del=(Button) convertView.findViewById(R.id.btn_del);
			if(listener!=null){
				holder.btn_del.setVisibility(View.VISIBLE);
				holder.btn_del.setOnClickListener(listener);
			}else{
				holder.btn_del.setVisibility(View.INVISIBLE);
			}
		}else{
			holder=(Holder) convertView.getTag();
		}
		
		  ShopCartItem item=mList.get(position);
		  holder.btn_del.setTag(position);
		  holder.tv_title.setText(item.getCommodity().getTitle()+"  颜色:"+item.getColor());
		  ImageManager.from(context).displayImage(holder.iv_pic,item.getCommodity().getPics().get(0).getUrl(),-1);
		  holder.tv_price.setText("¥"+item.getCommodity().getPrice());
		  holder.tv_number.setText(item.getNumber()+"件");
		  holder.tv_color.setText(item.getSize());
		  
		convertView.setTag(holder);
		return convertView;
	}

}
