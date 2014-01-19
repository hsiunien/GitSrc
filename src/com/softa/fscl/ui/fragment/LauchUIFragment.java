package com.softa.fscl.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.adapter.OrderAdapter;
import com.softa.fscl.models.OrderItem;
import com.softa.fscl.ui.Activity.OrderActivity;
import com.softa.fscl.ui.Activity.OrderContentActivity;
import com.softa.fscl.utils.Tools;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class LauchUIFragment extends Fragment {
	private ListView orderListView;
	private OrderAdapter orderAdapter;
	private List<OrderItem> items;
	private int mstate;

	public LauchUIFragment() {
	}

	public LauchUIFragment(int state) {
		this.mstate = state;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_launch, container,
				false);
		List<OrderItem> tItems = Tools.getApplication(getActivity())
				.getOrders();
		items = new ArrayList<OrderItem>();
		for (OrderItem orderItem : tItems) {
			if (orderItem.getState() == mstate) {
				items.add(orderItem);
			}
		}
		if (items.size() == 0) {
			rootView = inflater.inflate(R.layout.fragment_commonui, container,
					false);
		} else {
			orderAdapter = new OrderAdapter(getActivity(), items);
			orderListView = (ListView) rootView
					.findViewById(R.id.order_list_ok);
			orderListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent = new Intent();
					intent.putExtra("order", items.get(arg2));
					intent.setClass(getActivity(), OrderContentActivity.class);
					startActivity(intent);
				}
			});
			orderListView.setAdapter(orderAdapter);
		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}
