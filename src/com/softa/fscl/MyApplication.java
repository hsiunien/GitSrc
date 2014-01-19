package com.softa.fscl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.models.OrderItem;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.models.User;
import com.softa.fscl.ui.widget.CommodityItem;
import com.softa.fscl.utils.CommDef;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApplication extends Application {

	private HttpClient httpClient;
	private  boolean isLogin = false;
	private List<ShopCartItem> shopCartItems;// 商品详情
	private List<OrderItem> orders; //订单详情列表
	private User user;

	public User loadUserFromSP() {
		if(user!=null) return user;
		SharedPreferences sp = getSharedPreferences(CommDef.SHARED_DATA,
				MODE_PRIVATE);
		String json = sp.getString("user", "{}");
		Gson gson = new Gson();
		user = gson.fromJson(json, User.class);
		
		return user;

	}

	@Override
	public void onCreate() {
		super.onCreate();
		httpClient = this.createHttpClient();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		this.shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		this.shutdownHttpClient();
	}

	// 创建HttpClient实例
	private HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}

	// 关闭连接管理器并释放资源
	private void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	// 对外提供HttpClient实例
	public HttpClient getHttpClient() {
		return httpClient;
	}

	public List<ShopCartItem> getShopCartItems() {
		if(shopCartItems==null){
			shopCartItems=new ArrayList<ShopCartItem>();
		}
		return shopCartItems;
	}

	public void setShopCartItems(List<ShopCartItem> shopCartItems) {
		this.shopCartItems = shopCartItems;
	}

	public boolean isLogin() {
		if(user!=null&&user.getLoginState()==1){
			isLogin=true;
		}else{
			isLogin=false;
		}
		return isLogin;
	}

	public List<OrderItem> getOrders() {
		if(orders==null){
			orders=new ArrayList<OrderItem>();
		}
		return orders;
	}

	public void setOrders(List<OrderItem> orders) {
		this.orders = orders;
	}

 

}
