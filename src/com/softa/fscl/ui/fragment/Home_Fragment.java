package com.softa.fscl.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.ui.Activity.CommodityActivity;
import com.softa.fscl.ui.Activity.QikanActivity;
import com.softa.fscl.ui.widget.ImageScroll;
import com.softa.fscl.ui.widget.MenuContainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class Home_Fragment extends Fragment implements OnClickListener {
	private Button bt_login;
	ImageScroll myPager; // 图片容器
	LinearLayout ovalLayout; // 圆点容器
	private List<View> listViews; // 图片组
	private MenuContainer mcDanpin, mcDapei, mcQikan, mcZuixin;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		myPager = (ImageScroll) getActivity().findViewById(R.id.myvp);
		ovalLayout = (LinearLayout) getActivity().findViewById(R.id.vb);
		mcDanpin = (MenuContainer) find(R.id.mcDanpin);
		mcDanpin.setOnClickListener(this);

		mcQikan = (MenuContainer) find(R.id.mcQikan);
		mcQikan.setOnClickListener(this);

		mcZuixin = (MenuContainer) find(R.id.mcZuixin);
		mcZuixin.setOnClickListener(this);

		mcDapei = (MenuContainer) find(R.id.mcDapei);
		mcDapei.setOnClickListener(this);

		InitViewPager();// 初始化图片
		// 开始滚动
		myPager.start(getActivity(), listViews, 5000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, null);
	}

	private void InitViewPager() {
		listViews = new ArrayList<View>();
		int[] imageResId = new int[] { R.drawable.a, R.drawable.b,
				R.drawable.c, R.drawable.d, R.drawable.e };
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {// 设置图片点击事件
					Toast.makeText(getActivity(),
							"点击了:" + myPager.getCurIndex(), Toast.LENGTH_SHORT)
							.show();
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			listViews.add(imageView);
		}
	}

	private View find(int id) {
		return getActivity().findViewById(id);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		myPager.startTimer();
		super.onStart();
	}

	@Override
	public void onStop() {
		myPager.stopTimer();
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.mcDanpin:
			intent = new Intent(getActivity(), CommodityActivity.class);
			intent.putExtra("title", "单品");
			getActivity().startActivity(intent);
			break;
		case R.id.mcDapei:
			intent = new Intent(getActivity(), CommodityActivity.class);
			intent.putExtra("title", "搭配");
			getActivity().startActivity(intent);
			break;
		case R.id.mcQikan:
			intent = new Intent(getActivity(), QikanActivity.class);
			getActivity().startActivity(intent);
			break;
		default:
			break;
		}
	}
}
