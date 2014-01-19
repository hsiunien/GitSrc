package com.softa.fscl.ui.Activity;

import com.softa.fscl.R;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.tools.ShopCartManager;
import com.softa.fscl.ui.widget.TitleBar;
import com.softa.fscl.utils.CommDef;
import com.softa.fscl.utils.ImageManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CommodityBuyActivity extends Activity implements OnClickListener{
	TitleBar titleBar;
	private int i=1;
	private Commodity commodity;
	private TextView tv_commodityNumver,tv_title,tv_size;
	private ImageView iv_commodity;
	ImageButton btn_add,btn_sub;
	Button btn_add_cart;
	RadioGroup rg_color,rg_size;
	private ShopCartItem item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodity_buy);
		init();
	}
	public void init(){
		titleBar=(TitleBar) findViewById(R.id.titleBar);
		tv_commodityNumver=(TextView) findViewById(R.id.tv_commodityNumber);
		btn_add=(ImageButton) findViewById(R.id.btn_add);
		tv_title=(TextView) findViewById(R.id.tv_title);
		btn_sub=(ImageButton) findViewById(R.id.btn_sub);
		btn_add_cart=(Button) findViewById(R.id.btn_add_cart);
		iv_commodity=(ImageView) findViewById(R.id.iv_commodity);
		rg_color=(RadioGroup) findViewById(R.id.rg_color);
		rg_size= (RadioGroup) findViewById(R.id.rg_size);
		btn_add.setOnClickListener(this);
		btn_sub.setOnClickListener(this);
		btn_add_cart.setOnClickListener(this);
		commodity=(Commodity) getIntent().getExtras().get("commodity");
		ImageManager.from(this).displayImage(iv_commodity, commodity.getPics().get(0).getUrl(), -1);
		tv_title.setText(commodity.getTitle());
		/*for (String str : commodity.getParam().get("color")) {
			 ViewGroup vg= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.radiobutton_color_layout,null);
			 RadioButton button=(RadioButton) vg.findViewById(R.id.rd);
			 vg.removeView(button);
			 button.setText(str);
			 rg_color.addView(button);
		}*/
		

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
				tv_commodityNumver.setText(""+(++i));
			break;
		case R.id.btn_sub:
			 tv_commodityNumver.setText(""+(i<=1?1:--i));
			break;
		case R.id.btn_add_cart:
			item=new ShopCartItem();
			item.setCommodity(commodity);
			item.setNumber(i);
			int cid=rg_color.getCheckedRadioButtonId();
			int sid=rg_size.getCheckedRadioButtonId();
			 if(cid<0){
				 Toast.makeText(this, "请选择颜色", 3000).show();
				 return ;
			 }else if(sid<0){
				 Toast.makeText(this, "请选择尺码", 3000).show();
				 return ;
			 }
			item.setColor((String)((RadioButton)findViewById(cid)).getTag());
			item.setSize((String)((RadioButton)findViewById(sid)).getTag());
		//	item.setColor("黑色");
			save2Item();
			Toast.makeText(this, "购买成功", 3000).show();
			this.finish();
			break;
		}
	}
	private void save2Item(){
	   ShopCartManager scm=new ShopCartManager(this);
	   scm.addShopCart(item);
	}
 
}
