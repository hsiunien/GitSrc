package com.softa.fscl.ui.widget;

import com.softa.fscl.R;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.ui.Activity.CommodityDetailActivity;
import com.softa.fscl.utils.ImageManager;
import com.softa.fscl.utils.Tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommodityItem  extends LinearLayout{

	private LayoutInflater li;
	private Context mContext;
	private LinearLayout mLayout;
	private ImageView mIvImage;
	private TextView mTvTitle;
	private TextView mTvPrice;
	private Object tag;
	private String picUrl;//第一张图片
	private boolean visible=true;//初始为不可见状态
	//private static ColorDrawable defaultBG= new ColorDrawable(android.R.color.transparent);
	public CommodityItem(Context context) {
		super(context);
		mContext=context;
		li = LayoutInflater.from(context);		
		mLayout= (LinearLayout) li.inflate(R.layout.commodity_item, this);
		mIvImage=(ImageView) mLayout.findViewById(R.id.iv_commodifyItem);
		mTvPrice=(TextView) mLayout.findViewById(R.id.tv_price);
		mTvTitle=(TextView) mLayout.findViewById(R.id.tv_title);
		mIvImage.setImageResource(R.drawable.loading);
 		mIvImage.setScaleType(ScaleType.FIT_START);
      	this.setOnClickListener(onclick);
	} 
	
	 
	public void checkVisible(){
		int h=getBottom();
		Log.d(getClass().toString(), ((Commodity)tag).getTitle()+"check visible height="+h);
	}
	 
	public void recycle(){
		if(visible){ 
			Log.d(getClass().toString(), ((Commodity)tag).getTitle()+"已回收 bottom="+getBottom());
			android.view.ViewGroup.LayoutParams layout= mIvImage.getLayoutParams();
			layout.height=mIvImage.getHeight();
			layout.width=mIvImage.getWidth();
			mIvImage.setScaleType(ScaleType.CENTER);
			mIvImage.setImageResource(R.drawable.loading);

			mIvImage.setScaleType(ScaleType.FIT_START);
			visible=false;
			System.gc();
		}

	}
	public void reload(){
		if(!visible){
		String url=((Commodity)tag).getPics().get(0).getUrl();
		android.view.ViewGroup.LayoutParams layout= mIvImage.getLayoutParams();
		layout.height=mIvImage.getHeight();
		layout.width=mIvImage.getWidth();
		ImageManager.from(mContext).displayImage(mIvImage, url,-1, Tools.dip2px(getContext(),layout.width)
				,Tools.dip2px(getContext(),layout.height));
		visible=true;
		}
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		Log.d(getClass().toString(), ((Commodity)tag).getTitle()+"scrollChanged");
		super.onScrollChanged(l, t, oldl, oldt);
	}
	private OnClickListener onclick=new OnClickListener() {
		@Override
		public void onClick(View v) {
		 	Log.d(getClass().toString(), "testItem-----"+((Commodity)tag).getTitle()+"  "+visible);	
		 	Intent intent=new Intent(getContext(), CommodityDetailActivity.class);
		 	intent.putExtra("commodity", (Commodity)tag);
		 	getContext().startActivity(intent);
			}
	};
	public void setWidth(int width){
	}
	public void setTitle(String title){
		this.mTvTitle.setText(title);
	} 
	public void setPrice(int price){
		mTvPrice.setText("¥"+price);
	} 
	public void setTag(Object tag){
		this.tag=tag;
	}
	public ImageView getImageView(){
		return mIvImage;
	}
	public Object getTag(){
		return tag;
	}
	public void setCommodity(Commodity commodity){
		this.mTvTitle.setText(commodity.getTitle());
		setTag(commodity);
		this.picUrl=commodity.getPics().get(0).getUrl();
	}
}
