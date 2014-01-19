package com.softa.fscl.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.adapter.ShopingCartAdapter;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.tools.ShopCartManager;
import com.softa.fscl.ui.Activity.CommodityDetailActivity;
import com.softa.fscl.ui.Activity.OrderBuyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShoppingCart_Fragment extends Fragment implements OnClickListener,
		OnItemClickListener {
	private ShopCartManager scm;
	private List<ShopCartItem> items;
	private ListView lv_shopCart;
	private View noShoping;
	private ShopingCartAdapter adp;
	private ArrayAdapter<String> aadp;
	List<String> list;
	private TextView tv_info;
	private View v;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scm = new ShopCartManager(getActivity());
		items = scm.loadFromApplication();
		adp = new ShopingCartAdapter(items, getActivity());
		adp.setOnDelListener(this);
		items = scm.loadFromApplication();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		container = (ViewGroup) inflater.inflate(
				R.layout.fragment_shoping_cart, null);
		noShoping = container.findViewById(R.id.noShoping);
		lv_shopCart = (ListView) container.findViewById(R.id.lv_shopCart);
		tv_info = (TextView) container.findViewById(R.id.tv_info);
		lv_shopCart.setAdapter(adp);
		v = container;
		lv_shopCart.setOnItemClickListener(this);
		v.findViewById(R.id.btn_goAccount).setOnClickListener(this);
		return container;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (items != null && items.size() != 0) {
			float count = 0;
			for (int i = 0; i < items.size(); i++) {
				count += (items.get(i).getNumber() * items.get(i)
						.getCommodity().getPrice());
			}
			StringBuffer sb = new StringBuffer();
			sb.append("共 ").append(items.size()).append(" 条纪录  总计：")
					.append(count).append("元");
			tv_info.setText(sb);
		} else {
			noShoping.setVisibility(View.VISIBLE);
			tv_info.setText("共 0 条纪录");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (items != null && items.size() != 0) {
			noShoping.setVisibility(View.INVISIBLE);
			adp.notifyDataSetChanged();
			float count = 0;
			for (int i = 0; i < items.size(); i++) {
				count += (items.get(i).getNumber() * items.get(i)
						.getCommodity().getPrice());
			}
			StringBuffer sb = new StringBuffer();
			sb.append("共 ").append(items.size()).append(" 条纪录  总计：")
					.append(count).append("元");
			tv_info.setText(sb);
		} else {
			noShoping.setVisibility(View.VISIBLE);
			tv_info.setText("共 0 条纪录");
		}

	}

	@Override
	public void onDetach() {
		super.onDetach();
		scm.save2SP();
	}

 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==100){
			items = scm.loadFromApplication();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_del:
			int p = (Integer) ((Button) v).getTag();
			items.remove(p);
			adp.notifyDataSetChanged();
			onResume();
			break;
		case R.id.btn_goAccount:
			Intent intent=new Intent(getActivity(), OrderBuyActivity.class);
			 startActivityForResult(intent, 100);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Commodity commodity = items.get(position).getCommodity();
		Intent intent = new Intent(getActivity(), CommodityDetailActivity.class);
		intent.putExtra("commodity", commodity);
		startActivity(intent);
	}

}
