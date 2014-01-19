package com.softa.fscl.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.mapgis.phone.zxing.CaptureActivity;
import com.softa.fscl.R;
import com.softa.fscl.adapter.CategoryAdapter;
import com.softa.fscl.models.Category;
import com.softa.fscl.ui.Activity.CommodityActivity;
import com.softa.fscl.ui.widget.WatterFullLayout;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Category_Fragment extends Fragment implements OnItemClickListener{

	private ListView lv;
	List<Category> categorys ;
	private ImageView iv_qrcode;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_category, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		  categorys = new ArrayList<Category>(){{
			add(new Category("上衣",	 BitmapFactory.decodeResource(getResources(), R.drawable.cp1)));  
			add(new Category("鞋子",	 BitmapFactory.decodeResource(getResources(), R.drawable.cp2)));  
			add(new Category("裤子",	 BitmapFactory.decodeResource(getResources(), R.drawable.cp3)));  
			add(new Category("裙子",	 BitmapFactory.decodeResource(getResources(), R.drawable.cp4)));  
			add(new Category("手提包",	 BitmapFactory.decodeResource(getResources(), R.drawable.cp5)));  
			add(new Category("配饰",	 BitmapFactory.decodeResource(getResources(), R.drawable.cp6)));  
		  } };
		 
		CategoryAdapter adp = new CategoryAdapter(categorys, getActivity());
		lv = (ListView) find(R.id.lv_category);
		lv.setAdapter(adp);
		lv.setOnItemClickListener(this);
		iv_qrcode=(ImageView) find(R.id.iv_qrcode);
		iv_qrcode.setOnClickListener(qrListener);
	}

	private OnClickListener qrListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_qrcode:
				startActivity(new Intent(getActivity(), CaptureActivity.class));
				break;

			default:
				break;
			}
			
		}
	};
	private View find(int id) {
		return getActivity().findViewById(id);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Log.d("tag--", ""+position);
		Intent intent=new Intent(getActivity(), CommodityActivity.class);
		intent.putExtra("title", categorys.get(position).getName());
		startActivity(intent);
		
	}
}
