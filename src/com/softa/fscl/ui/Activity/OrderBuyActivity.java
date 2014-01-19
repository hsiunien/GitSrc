package com.softa.fscl.ui.Activity;

import java.util.Date;
import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.adapter.ShopingCartAdapter;
import com.softa.fscl.models.OrderItem;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.models.User;
import com.softa.fscl.net.UserServiceClient;
import com.softa.fscl.net.UserServiceClient.UserServiceListenr;
import com.softa.fscl.net.WebServiceClient.WebServiceListener;
import com.softa.fscl.tools.ShopCartManager;
import com.softa.fscl.ui.widget.LoadingDialog;
import com.softa.fscl.utils.StringUtils;
import com.softa.fscl.utils.Tools;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OrderBuyActivity extends Activity implements OnClickListener   {


	private ListView lv_shopCart;
	private List<ShopCartItem> items;
	private ShopCartManager scm;
	private ShopingCartAdapter adp;	
	TextView tv_price;
	private EditText et_name,et_address,et_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderbuy);
		scm = new ShopCartManager(this);
		items = scm.loadFromApplication();
		adp = new ShopingCartAdapter(items, this);
		initView();
		lv_shopCart.setAdapter(adp);
		setListViewHeightBasedOnChildren(lv_shopCart);
	}

	private float countPrice(){
		float count = 0;
		for (int i = 0; i < items.size(); i++) {
			count += (items.get(i).getNumber() * items.get(i)
					.getCommodity().getPrice());
		}
		return count;
	}
	// 控件的声明
	public void initView() {
		lv_shopCart=(ListView) findViewById(R.id.lv_shopCart);
		tv_price=(TextView) findViewById(R.id.tv_price);
		et_address=(EditText) findViewById(R.id.et_address);
		et_name=(EditText) findViewById(R.id.et_receiveName);
		et_phone=(EditText) findViewById(R.id.et_phone);
		findViewById(R.id.goAccount).setOnClickListener(this);
		tv_price.setText("¥"+countPrice());
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
 
	}
	  public void setListViewHeightBasedOnChildren(ListView listView) { 
	        // 获取ListView对应的Adapter 
	        ListAdapter listAdapter = listView.getAdapter(); 
	        if (listAdapter == null) { 
	            return; 
	        } 
	 
	        int totalHeight = 0; 
	        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { 
	            // listAdapter.getCount()返回数据项的数目 
	            View listItem = listAdapter.getView(i, null, listView); 
	            // 计算子项View 的宽高 
	            listItem.measure(0, 0);  
	            // 统计所有子项的总高度 
	            totalHeight += listItem.getMeasuredHeight();  
	        } 
	 
	        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	        // listView.getDividerHeight()获取子项间分隔符占用的高度 
	        // params.height最后得到整个ListView完整显示需要的高度 
	        listView.setLayoutParams(params); 
	    } 
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goAccount:
			List<OrderItem> orderItems=Tools.getApplication(this).getOrders();
			OrderItem item=new OrderItem();
			Date date=new Date();
			item.setDate(date);
			item.setFukuang("货到付款");
			item.setShopcartItems(items);
			item.setOrderNo(StringUtils.date2Str(date, "yyyyMMddHHmmss")) ;
			item.setState(0);
			item.setAddress(et_address.getText().toString());
			item.setPhone(et_phone.getText().toString());
			item.setName(et_name.getText().toString());
			item.setPrice(countPrice());
			Tools.getApplication(this).setShopCartItems(null);
			orderItems.add(item);
			Tools.getApplication(this).setOrders(orderItems);
			Toast.makeText(this, "提交订单成功，您可以到我的订单中查看详情", 5000).show();
			this.finish();
			break;
		case R.id.regist_btn:
			break;
		default:
			break;
		}

	}
 
}
