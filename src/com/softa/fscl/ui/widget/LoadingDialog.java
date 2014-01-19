package com.softa.fscl.ui.widget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.softa.fscl.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class LoadingDialog {
	private Dialog dialog;
	private ImageView iv_loading;
	private Animation an;
	public  LoadingDialog(Context context) {
		dialog = new Dialog(context, R.style.MyDialog);
		LayoutInflater li = LayoutInflater.from(context);
		ViewGroup v = (ViewGroup) li.inflate(R.layout.toast_layout, null);
		iv_loading = (ImageView) v.findViewById(R.id.img);
	an = AnimationUtils.loadAnimation(context,
				R.anim.loadinganimation);
		iv_loading.setAnimation(an);
		dialog.setContentView(v);
		dialog.setCanceledOnTouchOutside(false);
	}

 
	public void show() {
		dialog.show();
		iv_loading.startAnimation(an);

	}

	public void setCancelable(boolean flag) {
		dialog.setCancelable(flag);
	}

	public void setCanceledOnTouchOutside(boolean flag) {
		dialog.setCanceledOnTouchOutside(flag);
	}

	public void setCancelListener(OnCancelListener listener) {
		dialog.setOnCancelListener(listener);
	}

	/**
	 * 
	 */
	public void cancel() {
		dialog.cancel();
	}
	public void dismiss(){
		dialog.dismiss();
	}

}
