package com.softa.fscl.ui.Activity;

import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.adapter.ShopingCartAdapter;
import com.softa.fscl.models.OrderItem;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.tools.ShopCartManager;
import com.softa.fscl.utils.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class OrderContentActivity extends Activity{
	
	private OrderItem item;
	private TextView tv_price,tv_orderNo,tv_orderState,tv_order_time,tv_name,tv_address,tv_phone;
	private ListView lv_shopCart;

	private List<ShopCartItem> items;
	private ShopingCartAdapter adp;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_content);
		Intent intent=getIntent();
		if(intent.getExtras()!=null){
			item=(OrderItem) intent.getExtras().get("order");
		}
		init();
		items=item.getShopcartItems();
		adp=new ShopingCartAdapter(items, this);
		lv_shopCart.setAdapter(adp);
		
	}
	private void init(){
		tv_price=(TextView) findViewById(R.id.tv_price);
		tv_orderNo=(TextView) findViewById(R.id.tv_orderNo);
		tv_orderState=(TextView) findViewById(R.id.tv_orderState);
		tv_order_time=(TextView) findViewById(R.id.tv_order_time);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		tv_address=(TextView) findViewById(R.id.tv_arddress);
		tv_name.setText(item.getName());
		tv_address.setText(item.getAddress());
		tv_phone.setText(item.getPhone());
		tv_price.setText("¥"+item.getPrice());
		tv_orderNo.setText(item.getOrderNo());
		tv_orderState.setText(item.orderState());
		tv_order_time.setText(StringUtils.date2Str(item.getDate(), "yyyy-MM-dd HH:mm:ss"));
		lv_shopCart=(ListView) findViewById(R.id.lv_shopCart);
	}
	public void order_content_back(View v){
		this.finish();
	}
	public void btn_pay(View v){
	}
}
