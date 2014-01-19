package com.softa.fscl.tools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softa.fscl.MyApplication;
import com.softa.fscl.models.ShopCartItem;
import com.softa.fscl.ui.widget.CommodityItem;
import com.softa.fscl.utils.CommDef;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 用于购物车相关的数据村醋
 * 
 * @author mac
 * 
 */
public class ShopCartManager {
	private Context context;

	public ShopCartManager(Context context) {
		this.context = context;
	}

	public List<ShopCartItem> loadFromSP() {
		String jsonItems;
		SharedPreferences sp = context.getSharedPreferences(
				CommDef.SHARED_DATA, Context.MODE_WORLD_READABLE);
		jsonItems = sp.getString("shopCartItems", "[]");
	
		List<ShopCartItem> items = json2Array(jsonItems);
		// 保存到全局变量
		((MyApplication) context.getApplicationContext())
				.setShopCartItems(items);
		return items;
	}
	
	public  List<ShopCartItem>  loadFromApplication(){
		return ((MyApplication) context.getApplicationContext()).getShopCartItems();
	}
	public void addShopCart(ShopCartItem item){
		if(item==null) return ;
		List<ShopCartItem> items = ((MyApplication) context
				.getApplicationContext()).getShopCartItems();
		if (items == null) {
			items = new ArrayList<ShopCartItem>();
		}
		//重复只修改数量 这里判断条件需要 修改
		for (ShopCartItem shopCartItem : items) {
			if(shopCartItem.getCommodity().getTitle().equals(item.getCommodity().getTitle())){
				shopCartItem.setNumber(shopCartItem.getNumber()+item.getNumber());
				return ;
			}
		}
		items.add(item);
		((MyApplication) context
				.getApplicationContext()).setShopCartItems(items);
		
	}

	public boolean save2SP() {
		List<ShopCartItem> items = ((MyApplication) context
				.getApplicationContext()).getShopCartItems();
		if (items == null) {
			items = new ArrayList<ShopCartItem>();
		}
		Gson gson = new Gson();
		String jsonItems = gson.toJson(items);
		SharedPreferences sp = context.getSharedPreferences(
				CommDef.SHARED_DATA, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("shopCartItems", jsonItems);
		return editor.commit();

	}

	private List<ShopCartItem> json2Array(String json) {
		Type type = new TypeToken<List<ShopCartItem>>() {
		}.getType();
		Gson gson = new Gson();
		List<ShopCartItem> list = gson.fromJson(json, type);
		return list;
	}

}
