package com.softa.fscl.ui.Activity;

import com.softa.fscl.R;
import com.softa.fscl.models.User;
import com.softa.fscl.net.UserServiceClient;
import com.softa.fscl.net.UserServiceClient.UserServiceListenr;
import com.softa.fscl.net.WebServiceClient.WebServiceListener;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class LoginActivity extends Activity implements OnClickListener, OnCheckedChangeListener  {

	private Button Login, regist;
	private EditText account, password;

	UserServiceClient usc;
	private User user;
	ToggleButton tb_isShowPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	// 控件的声明
	public void initView() {
		// 登录按钮
		Login = (Button) findViewById(R.id.login_btn);
		Login.setOnClickListener(this);
		// 注册按钮
		regist = (Button) findViewById(R.id.regist_btn);
		regist.setOnClickListener(this);
		// 文本编辑框
		account = (EditText) findViewById(R.id.lgoin_accounts); // 账号
		password = (EditText) findViewById(R.id.login_password); // 密码
		tb_isShowPassword=(ToggleButton) findViewById(R.id.isShowPassword);
		tb_isShowPassword.setOnCheckedChangeListener(this);
		user = Tools.getApplication(this).loadUserFromSP();
		usc = new UserServiceClient(listener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(android.R.anim.fade_in,
				R.anim.slide_out_bottom);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==100){
			String acc=data.getExtras().getString("email");
			if(acc==null){
				acc=data.getExtras().getString("phone");
			}
			String pwd=data.getExtras().getString("pwd");
			account.setText(acc);
			password.setText(pwd);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			String loginstr = account.getText().toString();
			String pwd = password.getText().toString();
			if(StringUtils.isEmail(loginstr)){
				usc.loginByEmail(loginstr, pwd);
			}else{
				usc.loginByPhone(loginstr, pwd);
			}
			

			break;
		case R.id.regist_btn:
			startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 100);
			break;
		default:
			break;
		}

	}

	UserServiceListenr listener = new UserServiceListenr() {
		private LoadingDialog dialog;
		public void onRequestStart() {
			dialog=new LoadingDialog(LoginActivity.this);
			dialog.show();
		};
		public void onLoginFalse(String msg) {
			dialog.dismiss();
			Toast.makeText(LoginActivity.this, msg,3000).show();
		};

		public void onLoginSuccess(User user) {
			dialog.dismiss();
			Tools.getApplication(LoginActivity.this).loadUserFromSP().copy(user);
			finish();
		}
		 
	};
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			 password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}else{
			 password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}
}
