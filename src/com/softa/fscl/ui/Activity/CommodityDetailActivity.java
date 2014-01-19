package com.softa.fscl.ui.Activity;

import com.softa.fscl.R;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.ui.widget.TitleBar;
import com.softa.fscl.utils.ImageManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CommodityDetailActivity extends Activity implements OnClickListener {
	TitleBar titleBar;
	Button btn_buy;
	private Commodity commodity;
	private ImageView iv_pic;
	private TextView tv_price,tv_title;
	private WebView wv_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodity_detail);
		init();
		
	}
	public void init(){
		titleBar=(TitleBar) findViewById(R.id.titleBar);
		btn_buy=(Button) findViewById(R.id.btn_buy);
		btn_buy.setOnClickListener(this);
		commodity=(Commodity) getIntent().getExtras().get("commodity");
		iv_pic=(ImageView) findViewById(R.id.iv_pic);
		ImageManager.from(this).displayImage(iv_pic, commodity.getPics().get(0).getUrl(), -1);
		wv_detail=(WebView) findViewById(R.id.wv_detail);
		tv_price=(TextView) findViewById(R.id.tv_price);
		tv_price.setText("¥"+commodity.getPrice());
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText(commodity.getTitle());
		wv_detail.loadUrl("http://mp.weixin.qq.com/mp/appmsg/show?__biz=MjM5MzA1MTM2OQ==&appmsgid=10013073&itemidx=1&sign=740d2266a90849de3ff22b2202703603");
		wv_detail.getSettings().setJavaScriptEnabled(true);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_buy:
			Intent intent=new Intent(this,CommodityBuyActivity.class);
			intent.putExtra("commodity", commodity);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
