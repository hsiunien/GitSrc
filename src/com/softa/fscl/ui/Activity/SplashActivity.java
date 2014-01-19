package com.softa.fscl.ui.Activity;

import com.softa.fscl.MyApplication;
import com.softa.fscl.R;
import com.softa.fscl.R.layout;
import com.softa.fscl.R.menu;
import com.softa.fscl.tools.ShopCartManager;
import com.softa.fscl.utils.Tools;

import android.R.interpolator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.app.Activity; 
import android.content.Intent;

public class SplashActivity extends   Activity {
/**
 * 为点击进入程序显示logo的界面 
 * 
 */
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiazai);
		
		ImageView iv1 = (ImageView)findViewById(R.id.imageviewTubiao);
		ImageView iv2 = (ImageView)findViewById(R.id.imageviewAction);
		int[] wh=Tools.getWH(this);
		TranslateAnimation an=new TranslateAnimation(0, -20, -20, wh[1]/2-35);
		an.setDuration(1800);
		an.setRepeatCount(0);
		an.setRepeatMode(Animation.REVERSE);
		an.setFillAfter(true);
		an.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.overshoot_interpolator));
	//	Animation  an = null;			
		//an = AnimationUtils.loadAnimation(this, R.anim.translate01);
		iv2.startAnimation(an);
		
		an.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				//读取购物车
			ShopCartManager scm=new ShopCartManager(SplashActivity.this);
			scm.loadFromSP();
			Tools.getApplication(getApplication()).loadUserFromSP() ;
			}	
			@Override
			public void onAnimationRepeat(Animation animation) {
			
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
			}
		});
		Animation an2=AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		 an2.setDuration(1600);
		iv1.startAnimation(an2);
		handler=new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
					// 这里些写跳转到另外一个activity语句
				startActivity(new  Intent(SplashActivity.this, MainActivity.class));
				finish();
			}
		}, 2000);
	}
}
