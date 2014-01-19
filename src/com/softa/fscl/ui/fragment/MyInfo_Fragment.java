package com.softa.fscl.ui.fragment;

import java.io.File;

import javax.crypto.spec.IvParameterSpec;


import com.softa.fscl.R;
import com.softa.fscl.models.User;
import com.softa.fscl.ui.Activity.LoginActivity;
import com.softa.fscl.ui.Activity.OrderActivity;
import com.softa.fscl.ui.widget.MyDialog;
import com.softa.fscl.utils.ImageManager;
import com.softa.fscl.utils.Tools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyInfo_Fragment extends Fragment  implements OnClickListener {

	Button loginButton,logoutButton;
	View my_order_all,rlayout_myInfo,rlayout_clearCache;
	ImageView iv_head;
	TextView tv_nickName,iv_grade;
	User user;
	MyDialog dialog;
	private final int CHOICEPICREQUEST=6001,CROPREQUES=6003,CROPREQUEST=6004;
	@Override
	public void onCreate(Bundle savedInstanceState) {
   		super.onCreate(savedInstanceState);
		  dialog=new MyDialog(getActivity());

    
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		container=(ViewGroup) inflater.inflate(R.layout.fragment_personer_center, null);
		init(container);
		return container;
	}
	@Override
	public void onResume() {
		super.onResume();
		if(Tools.getApplication(getActivity()).isLogin()){
			rlayout_myInfo.setVisibility(View.VISIBLE);
			logoutButton.setVisibility(View.VISIBLE);
			loginButton.setVisibility(View.INVISIBLE);
			tv_nickName.setText(user.getUserName());
			iv_grade.setText("积分："+user.getCredits());
		}else{
			logoutButton.setVisibility(View.GONE);
			rlayout_myInfo.setVisibility(View.INVISIBLE);
			loginButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	public void init(ViewGroup v){
		loginButton=(Button) v.findViewById(R.id.btn_login);
		loginButton.setOnClickListener(this);
		my_order_all=v.findViewById(R.id.my_order_all);
		my_order_all.setOnClickListener(this);
		rlayout_myInfo=v.findViewById(R.id.rlayout_myInfo);
		loginButton=(Button) v.findViewById(R.id.btn_login);
		logoutButton=(Button) v.findViewById(R.id.btn_logout);
		logoutButton.setOnClickListener(this);
		tv_nickName=(TextView) v.findViewById(R.id.tv_nickName);
		user=Tools.getApplication(getActivity()).loadUserFromSP();
		iv_head=(ImageView) v.findViewById(R.id.iv_head);
		iv_head.setOnClickListener(this);
		iv_grade=(TextView) v.findViewById(R.id.iv_grade);
		 v.findViewById(R.id.rlayout_clearCache).setOnClickListener(this);
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CHOICEPICREQUEST) { // 选择图片得到的响应
			if (resultCode == android.app.Activity.RESULT_OK) {
				Uri uri = data.getData();
				Log.i("AAA", uri.toString());
				// 启动缩放工具Activity(URI 显示出来)
				Intent is = new Intent();
				is.setAction("com.android.camera.action.CROP");
				is.setDataAndType(uri, "image/*");
				is.putExtra("crop", "true");
				is.putExtra("aspectX", 1);
				is.putExtra("aspectY", 1);
				is.putExtra("outputX", 128);
				is.putExtra("outputY", 128);
				is.putExtra("return-data", true);
				startActivityForResult(is, CROPREQUEST);
			}
		}
		//剪裁结束后执行
		if (requestCode == CROPREQUEST) {
			if (resultCode == android.app.Activity.RESULT_OK) {
			// 取的方式：
				Bitmap bm = data.getParcelableExtra("data");
				iv_head.setImageBitmap(bm);
				File f = new File(ImageManager.from(getActivity()).urlToFilePath("http://baidu.com/a.png"));
				if(f!=null){
					f.delete();
				}
				Tools.bitm2png(bm, f.getPath());
			}

		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
			getActivity().overridePendingTransition(R.anim.slide_in_bottom, android.R.anim.fade_out);
			break;
		case R.id.btn_logout:
			dialog.setContent("确认退出登录吗？");
			dialog.setButton1("确定", this);
			dialog.setButton2("取消", null);
			dialog.show();
			break;
		case R.id.button1:
			user.setLoginState(0);
			dialog.close();
			onResume();
			break;
		case R.id.my_order_all:
			Intent intent=new Intent(getActivity(), OrderActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.iv_head:
			 Intent i = new Intent();
				i.setAction(Intent.ACTION_GET_CONTENT);
				i.setType("image/*"); // 资源的MIME类型 image/jpeg
				startActivityForResult(i, CHOICEPICREQUEST);
			break;
		case R.id.rlayout_clearCache:
			dialog.setContent("确定清除缓存吗？");
			dialog.setButton1("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
				 dialog.close();
				}
			});
			dialog.setButton2("取消", null);
			dialog.show();
			break;
		default:
			break;
		}
	}

}
