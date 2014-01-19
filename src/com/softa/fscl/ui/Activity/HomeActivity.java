package com.softa.fscl.ui.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.softa.fscl.R;
import com.softa.fscl.ui.fragment.Advisory_Fragment;
import com.softa.fscl.ui.fragment.Category_Fragment;
import com.softa.fscl.ui.fragment.Home_Fragment;
import com.softa.fscl.ui.fragment.MyInfo_Fragment;
import com.softa.fscl.ui.fragment.ShoppingCart_Fragment;
import com.softa.fscl.utils.MyLinkedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;
import android.widget.TextView;
/*
 * 
 * 主activity
 * 
 */
public class HomeActivity extends FragmentActivity implements OnTabChangeListener{
	TabHost tabhost;
	TabWidget tabwidget;
	Home_Fragment homeFragment;
	Category_Fragment categoryFragment;
	FragmentTransaction ft;
	private int currentTab=0;
	
	private RelativeLayout[] buttomItems=new RelativeLayout[5];
	private MyLinkedList items=new MyLinkedList(){
		{
			put(R.string.index, R.drawable.selector_mood_home);
			put(R.string.category,R.drawable.selector_mood_wall);
			put(R.string.consultation,R.drawable.selector_mood_message);
			put(R.string.carrier,R.drawable.selector_mood_photograph);
			put(R.string.me,R.drawable.selector_mood_my_wall);
		}
	}; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		tabhost.setup();
		
		tabhost.setCurrentTab(0);
        tabhost.setOnTabChangedListener(this);
        initTab();
         /**  设置初始化界面  */
       tabhost.setCurrentTab(0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	private void initView(){
		tabhost=(TabHost) findViewById(android.R.id.tabhost);
		tabwidget=(TabWidget) findViewById(android.R.id.tabs);
		//LinearLayout layout=(LinearLayout) tabhost.getChildAt(0);
		for (int i = 0; i < buttomItems.length; i++) {
			buttomItems[i]=(RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_indicator,tabwidget, false);
		     TextView tvTab1 = (TextView)buttomItems[i].getChildAt(1);
	         ImageView ivTab1 = (ImageView)buttomItems[i].getChildAt(0);
	         ivTab1.setBackgroundResource((Integer)items.valueOfPosition(i));
	         tvTab1.setText((Integer) items.keyOfPosition(i));
		}
	}
	 /** 
     * 初始化选项卡
     * 
     * */
    public void initTab(){
    	for (int i = 0; i < items.size(); i++) {
            TabHost.TabSpec tSpecHome = tabhost.newTabSpec(getResources().getString((Integer) items.keyOfPosition(i)));
            tSpecHome.setIndicator(buttomItems[i]);        
            tSpecHome.setContent(new MyTabContent(getBaseContext()));
            tabhost.addTab(tSpecHome);
		}
        
    }

    class MyTabContent implements TabContentFactory{
    	private Context mContext;
    	
    	public MyTabContent(Context context){
    		mContext = context;
    	}
    	@Override
    	public View createTabContent(String tag) {
    		View v = new View(mContext);
    		return v;
    	}
    }

	@Override
	public void onTabChanged(String tabId) { 
		currentTab=findTabPosition(tabId);
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction ft= fm.beginTransaction();

		Fragment fragment=fm.findFragmentByTag(
				getResources().getString((Integer) items.keyOfPosition(currentTab)));
		//清除掉之前的界面
		for (int i = 0; i < items.size(); i++) {
			Fragment tFm=fm.findFragmentByTag(
					getResources().getString((Integer) items.keyOfPosition(i)));
			if(tFm!=null){
				ft.detach(tFm);
			}
		} 
		if(fragment!=null){
			ft.attach(fragment);
		}else{ 
			ft.add(R.id.pageContent,getCurrentFragment(currentTab), getResources().getString(
					(Integer) items.keyOfPosition(currentTab)));						
		}
     	ft.commit();
	}

	private int findTabPosition(String tabId){
		for (int i = 0; i < items.size(); i++) {
			if(tabId.equalsIgnoreCase(getResources().getString((Integer) items.keyOfPosition(i)))){
				return i;
			}
		}
		return 0;
	}
	private Fragment getCurrentFragment(int position){
		switch (position) {
		case 0:
			return new Home_Fragment();
		case 1:
			return new Category_Fragment();
		case 2:
			return new Advisory_Fragment();
		case 3:
			return new ShoppingCart_Fragment();
		case 4:
			return new MyInfo_Fragment();
		default:
			return new Home_Fragment();
			
		}
	}
  
}
