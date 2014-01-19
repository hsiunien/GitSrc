package com.softa.fscl.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyLinkedList  {
	private List<Map> mList=new LinkedList<Map>();
	public void put(final Object key,final Object value){
		mList.add(new LinkedHashMap<Integer, Object>(){
			{
				put(0, key);
				put(1,value);
			}
		});
	}
	public Object keyOfPosition(int index){
		return mList.get(index).get(0);
	}
	public Object valueOfPosition(int index){
		return mList.get(index).get(1);
	}
	public int size(){
		return mList.size();
	}
}
