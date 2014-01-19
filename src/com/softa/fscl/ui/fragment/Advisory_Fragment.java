package com.softa.fscl.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softa.fscl.R;
import com.softa.fscl.models.User;
import com.softa.fscl.ui.Activity.LoginActivity;
import com.softa.fscl.ui.Activity.OrderActivity;
import com.softa.fscl.ui.widget.MyDialog;
import com.softa.fscl.utils.Tools;

public class Advisory_Fragment  extends Fragment{
	/*
	 * 咨询界面
	 */
		 
		EditText et_msg;
		Button btnSend;
	@Override
	public void onCreate(Bundle savedInstanceState) {
   		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		container=(ViewGroup) inflater.inflate(R.layout.fragment_advisory, null);
		init(container);
		return container;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	public void init(ViewGroup v){
		btnSend=(Button) v.findViewById(R.id.btn_send);
	}
}

