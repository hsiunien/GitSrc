package com.softa.fscl.ui.widget;

import com.softa.fscl.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuContainer extends RelativeLayout {
	private String TAG=getClass().toString();
	private ImageView imageView;
	private TextView textView;
	private int textColor=-1,bgColor=-1;
	//title icon textColor bgSelector
	public MenuContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater li=LayoutInflater.from(context);
		View v = li.inflate(R.layout.home_menucontainer, this);
		if (isInEditMode()) { return; }
		imageView = (ImageView) v.findViewById(R.id.iv_menuImage);
		textView=(TextView) v.findViewById(R.id.tv_menuTitle); 
		
		textColor=attrs.getAttributeResourceValue(null, "textColor", -1);
		if (textColor != -1) {
			 textView.setTextColor(getResources().getColor(textColor));
		}
		textView.setText(attrs.getAttributeValue(null, "title") == null ? ""
				: attrs.getAttributeValue(null, "title"));
        
		int selector = attrs.getAttributeResourceValue(null, "icon", -1);
		if (selector != -1) {
			imageView.setBackgroundResource(selector);
		}		
		bgColor=attrs.getAttributeResourceValue(null, "bgColor", -1);
		if (bgColor != -1) {
			 v.setBackgroundDrawable(getResources().getDrawable(bgColor));
		}
	}

	
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
	//	Log.d("sss----", ""+this.getLayoutParams().width);
	}
}
