package com.softa.fscl.ui.Activity;

import com.softa.fscl.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CategoryActivity extends Fragment {
	private ListView lv_category;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("LOG","onCreate");
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_category, null);
	}
}
