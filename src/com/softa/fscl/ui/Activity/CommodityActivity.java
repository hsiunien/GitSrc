package com.softa.fscl.ui.Activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softa.fscl.R;
import com.softa.fscl.adapter.CategoryAdapter;
import com.softa.fscl.models.Category;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.models.Picture;
import com.softa.fscl.net.NetClient;
import com.softa.fscl.net.NetClient.NetCompliteListener;
import com.softa.fscl.ui.widget.LoadingScrollView;
import com.softa.fscl.ui.widget.LoadingScrollView.OnScrollListener;
import com.softa.fscl.ui.widget.Tip;
import com.softa.fscl.ui.widget.TitleBar;
import com.softa.fscl.ui.widget.WatterFullLayout;
import com.softa.fscl.utils.CommDef;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CommodityActivity extends Activity {
	private LinearLayout lv0, lv1;
	private TitleBar titleBar;
	private WatterFullLayout wf;
	LoadingScrollView loadingScrollView;
	private Tip tip;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodity);
		loadingScrollView = (LoadingScrollView) find(R.id.sv_commodity);
		titleBar = (TitleBar) findViewById(R.id.titleBar);
		main();
		if (getIntent().getExtras() != null) {
			String title = getIntent().getExtras().getString("title", "分类");
			titleBar.setTitle(title);
		}
		loadingScrollView.inital();
		loadingScrollView.setOnScrollListener(onscrollListener);
		new AsyncTask<Void, String, String>() {
			protected void onPreExecute() {
				tip = new Tip(CommodityActivity.this);
				tip.show();
			};

			@Override
			protected String doInBackground(Void... params) {
				String mResult = NetClient.get(CommDef.CommodityUrl, "", null);
				return mResult;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result == null) {
					tip.setContent("网络错误");
					return;
				}
				List<Commodity> ct = getFromJson(result);
				wf = (WatterFullLayout) find(R.id.listContainer);
				wf.addData(ct);
				tip.dismiss();
				// System.out.println(new Gson().toJson(ct));
			}
		}.execute();
	}

	void main() {
		Gson gson = new Gson();
		List<Commodity> comms = new ArrayList<Commodity>() {
			{
				Commodity c = new Commodity();
				HashMap<String, List<String>> mp = new HashMap<String, List<String>>();
				mp.put("color", new ArrayList<String>() {
					{
						add("红色");
						add("蓝色");
					}
				});
				mp.put("size", new ArrayList<String>() {
					{
						add("L");
						add("XL");
					}
				});
				c.setParam(mp);
				add(c);
			}
		};
		Commodity c = new Commodity();
		HashMap<String, List<String>> mp = new HashMap<String, List<String>>();
		mp.put("color", new ArrayList<String>() {
			{
				add("红色");
				add("蓝色");
			}
		});
		mp.put("size", new ArrayList<String>() {
			{
				add("L");
				add("XL");
			}
		});
		c.setParam(mp);
		String json = gson.toJson(c);
		Log.d("test", json);
	}

	private OnScrollListener onscrollListener = new OnScrollListener() {

		@Override
		public void onTop() {
			Log.d(getClass().toString(), "top");
		}

		@Override
		public void onScroll(int scrolly) {
			// TODO Auto-generated method stub
			Log.d(getClass().toString(), "scroll");
			wf.checkVisible(scrolly);
		}

		@Override
		public void onBottom() {
			// TODO Auto-generated method stub
			Log.d(getClass().toString(), "onButtom");
			new AsyncTask<Void, String, String>() {
				protected void onPreExecute() {
					tip = new Tip(CommodityActivity.this);
					tip.show();
				};

				@Override
				protected String doInBackground(Void... params) {
					String mResult = NetClient.get(CommDef.CommodityUrl, "",
							null);
					return mResult;
				}

				@Override
				protected void onPostExecute(String result) {
					if (result == null) {
						tip.setContent("网络错误");
						return;
					}
					List<Commodity> ct = getFromJson(result);
					wf = (WatterFullLayout) find(R.id.listContainer);
					wf.addData(ct);
					tip.dismiss();
				}
			}.execute();
		}
	};

	private List<Commodity> getFromJson(String json) {
		Type type = new TypeToken<List<Commodity>>() {
		}.getType();
		List<Commodity> cList;
		Gson gson = new Gson();
		cList = gson.fromJson(json, type);
		return cList;
	}

	private View find(int id) {
		return findViewById(id);
	}

}
