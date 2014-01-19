package com.softa.fscl.ui.widget;

import com.softa.fscl.utils.Tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class LoadingScrollView extends ScrollView {
	private OnScrollListener scrollListener;
	private ScrollHandler handler;
	private int screenHeight;
	private View firstView;
	private int refreshOffset=15;
	private int scrollOffset=300;
	public LoadingScrollView(Context context) {
		super(context);
	}

	public LoadingScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnTouchListener(ontouchListener);
		handler = new ScrollHandler();
		int[] wh = Tools.getWH(getContext());
		screenHeight = wh[1];
	}

	class ScrollHandler extends Handler {
		int pressState=0;
		int touchTopCount=0;
		int touchBottomCount=0;
		int touchScroll=0;
		@Override
	public void handleMessage(Message msg) {

		switch (msg.what) {
		case 100:
			pressState=1;
			scrollListener.onScroll(getScrollY());
			break;
		case 124:
			touchScroll++;
			break;
		case 125:
			touchTopCount++;
			break;
		case 126:
			touchBottomCount++;
			break;
		default:
			break;
		}
		if(pressState==1&&touchTopCount>refreshOffset){
			scrollListener.onTop();
			touchTopCount=0;
		}else if(pressState==1&&touchBottomCount>refreshOffset){
			scrollListener.onBottom();
			touchBottomCount=0;
		}else if(touchScroll>=scrollOffset){
			scrollListener.onScroll(getScrollY());
			touchScroll=0;
		}
		pressState=0;
	}
	}

	/**
	 * 找到最上头图用于判断位置
	 */
	public void inital() {
     if(getChildCount()>0){
    	 firstView=getChildAt(0);
     }
	}

	public void setOnScrollListener(OnScrollListener scrollListener) {
		this.scrollListener = scrollListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if(firstView!=null){
			if(oldt==t&&t!=0){
				Log.d("scrolltes", "lt ol ot "+l+" "+t+" "+oldl+" "+oldt+"  height="+getBottom());
			handler.sendMessageDelayed(handler.obtainMessage(126), 200);	
			}else if(t==oldt&&t==0){
				handler.sendMessageDelayed(handler.obtainMessage(125), 200);	
			}else{
				handler.sendMessageDelayed(handler.obtainMessage(124), 200);	
			}			
		}
		
	}

	@Override
	protected void onAnimationStart() {
		super.onAnimationStart();
	}

	OnTouchListener ontouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				handler.sendMessageDelayed(handler.obtainMessage(100), 200);	
				break;
			case MotionEvent.ACTION_DOWN:
				break;
			default:
				break;
			}
			return false;
		}
	};

	public interface OnScrollListener {
		void onBottom();

		void onTop();

		void onScroll(int scrolly);
	}

}
