package com.softa.fscl.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softa.fscl.R;
import com.softa.fscl.models.OrderItem;
import com.softa.fscl.utils.ImageManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter{
	private List<Map<String, Object>> data;
	public Context context;
	private List<OrderItem> mItems;
	private int maxCount = 5;
	private Map<Integer, View> viewMap;
	private int index = 5;
	ViewHolder hodler = null;
	public OrderAdapter(Context context,List<OrderItem> items) {
		viewMap = new HashMap<Integer, View>();
		this.context = context;
		//maxCount = getCount();
		this.mItems=items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=convertView = LayoutInflater.from(context).inflate(
					R.layout.order_list_items, null);
			hodler = new ViewHolder();
			hodler.order_image = (ImageView)convertView.findViewById(R.id.imageView1);
			hodler.order_number = (TextView)convertView.findViewById(R.id.textView1);
			hodler.order_statud= (TextView)convertView.findViewById(R.id.textView2);
			hodler.order_name= (TextView)convertView.findViewById(R.id.textView3);
			hodler.order_describe= (TextView)convertView.findViewById(R.id.textView4);
			hodler.order_fukuang= (TextView)convertView.findViewById(R.id.textView5);
			hodler.order_price= (TextView)convertView.findViewById(R.id.textView6);
			hodler.order_wuliu= (TextView)convertView.findViewById(R.id.textView7);
		}else{
			hodler=(ViewHolder) convertView.getTag();
		}
		
			
		ImageManager.from(context).displayImage(hodler.order_image, mItems.get(position).getShopcartItems().get(0)
				.getCommodity().getPics().get(0).getUrl(), -1,60,90);
		hodler.order_number.setText("订单号:"+mItems.get(position).getOrderNo());
		hodler.order_statud.setText(mItems.get(position).orderState());
		hodler.order_name.setText(mItems.get(position).getShopcartItems().get(0).getCommodity().getTitle());
		hodler.order_describe.setText("尺码："+mItems.get(position).getShopcartItems().get(0).getSize()
				+"颜色："+mItems.get(position).getShopcartItems().get(0).getColor());
		hodler.order_fukuang.setText(mItems.get(position).getFukuang());
		hodler.order_price.setText("¥"+mItems.get(position).getPrice());
		hodler.order_wuliu.setText("顺丰速递");
		convertView.setTag(hodler);
		return convertView;
	}
	class ViewHolder {
		ImageView order_image;
		TextView order_number;
		TextView order_statud; 
		TextView order_name; 
		TextView order_describe; 
		TextView order_fukuang; 
		TextView order_price; 
		TextView order_wuliu; 
	}
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
