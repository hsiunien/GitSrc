package com.softa.fscl.ui.widget;

import java.util.ArrayList;
import java.util.List;

import com.softa.fscl.models.Commodity;
import com.softa.fscl.utils.ImageManager;
import com.softa.fscl.utils.Tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class WatterFullLayout extends LinearLayout {
	private int[] screenWH;
	private int columns;
	LinearLayout[] linearLayouts; // 列
	private List<Commodity> commoditys;
	private List<CommodityItem> mCommodityItems;
    private int offset=10;
	public WatterFullLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCommodityItems = new ArrayList<CommodityItem>();
		screenWH = Tools.getWH(getContext());
	}

	public void addData(List<Commodity> commoditys) {
		this.commoditys = commoditys;
		int picWidth = screenWH[0] / columns;
		int i = minLayout();
		if(picWidth==0){
				Log.e("iszero", "图片宽度是0？");
		}
		for (Commodity commodity : commoditys) {
			CommodityItem cmItem = new CommodityItem(getContext());
			android.view.ViewGroup.LayoutParams layout = cmItem.getImageView()
					.getLayoutParams();
			layout.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
			layout.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
			// Log.d("height ", ""+layout.height);
			ImageManager.from(getContext()).displayImage(cmItem.getImageView(),
					commodity.getPics().get(0).getUrl(), -1,
					Tools.dip2px(getContext(), picWidth),
					Tools.dip2px(getContext(), picWidth));
			cmItem.setTitle(commodity.getTitle());
			cmItem.setPrice((int) (commodity.getPrice()));
			cmItem.setTag(commodity);
			mCommodityItems.add(cmItem);
			linearLayouts[i++ % linearLayouts.length].addView(cmItem);
		}

	}

	public int minLayout() {
		int i = 0;
		int temp=0;
		if (linearLayouts.length > 0) {
			temp = linearLayouts[0].getMeasuredHeight();
		}
		for (int j = 0; j < linearLayouts.length; j++) {
			if (temp > linearLayouts[j].getMeasuredHeight()) {
				temp = linearLayouts[j].getMeasuredHeight();
				i=j;
			}
		}
		return i;
	}

	// 检查可见性
	public void checkVisible(int scrolly) {
		int buttom = scrolly;
		
		Log.d(getClass().toString(), "waterfull buttom  ="
				+ (scrolly));
		

		
		for (CommodityItem item : mCommodityItems) {
			
			if ((item.getBottom()+offset < buttom)||item.getTop()-offset>buttom+screenWH[1]) {
				//Log.d(getClass().toString(), "offset  ="
				//		+ (item.getBottom()+offset));
				item.recycle();
			}else{
				item.reload();
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		linearLayouts = new LinearLayout[getChildCount()];
		for (int i = 0; i < getChildCount(); i++) {
			if (getChildAt(i) instanceof LinearLayout) {
				linearLayouts[i] = (LinearLayout) getChildAt(i);
			}
		}
		columns = linearLayouts.length <= 0 ? 1 : linearLayouts.length;
		//固定每列宽度
		for (LinearLayout e : linearLayouts) {
			e.getLayoutParams().width=screenWH[0]/columns;
		}

	}

}
