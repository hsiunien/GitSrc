package com.softa.fscl.ui.Activity;

import com.softa.fscl.R;
import com.softa.fscl.models.User;
import com.softa.fscl.net.UserServiceClient;
import com.softa.fscl.net.UserServiceClient.UserServiceListenr;
import com.softa.fscl.ui.widget.LoadingDialog;
import com.softa.fscl.utils.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class RegisterActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
	private Button register_btn;
	private EditText et_email,et_pwd,et_name,et_phone;
	private ToggleButton tb_isShowPassword; 
	private LoadingDialog dialog;
	UserServiceClient usc;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		usc=new UserServiceClient(listener);
	}

	private void initView() {

//		注册按钮
		register_btn = (Button)findViewById(R.id.btn_registe);
		register_btn.setOnClickListener(this);

		et_email = (EditText)findViewById(R.id.et_email);
		et_phone = (EditText)findViewById(R.id.et_phone);
//		输入密码
		et_pwd =  (EditText) findViewById(R.id.et_pwd);
		et_name=(EditText) findViewById(R.id.et_name);	
		tb_isShowPassword=(ToggleButton) findViewById(R.id.isShowPassword);
		tb_isShowPassword.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onDestroy() { 
		if(dialog!=null){
			dialog.dismiss();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_registe: 
			String email=et_email.getText().toString(),
			phone=et_phone.getText().toString(),
			pwd=et_pwd.getText().toString(),
			name=et_name.getText().toString();
			usc.regist(email, phone, pwd, name);
			break;
		default:
			break;
		}
		
	}
	UserServiceListenr listener = new UserServiceListenr() {
		private LoadingDialog dialog;
		public void onRequestStart() {
			dialog=new LoadingDialog(RegisterActivity.this);
			dialog.show();
		};
		public void onRegisted(int code, String msg) {
			dialog.dismiss();
			if(code==0){
				Toast.makeText(RegisterActivity.this, "注册成功", 3000).show();
				Intent intent=new Intent();
				intent.putExtra("email", et_email.getText().toString());
				intent.putExtra("phone", et_phone.getText().toString());
				intent.putExtra("pwd", et_pwd.getText().toString());
				setResult(100, intent);
				finish();
			}else{
				Toast.makeText(RegisterActivity.this, msg, 3000).show();
			}
			
		};
	};	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}else{
			et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}
}
