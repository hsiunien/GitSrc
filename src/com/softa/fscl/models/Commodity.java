package com.softa.fscl.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;

public class Commodity implements Serializable{
	private int id;
	private String title;
	private float price;
	private List<Picture> pics;
	private HashMap<String, List<String>>  param;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

 

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

  

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Picture> getPics() {
		return pics;
	}

	public void setPics(List<Picture> pics) {
		this.pics = pics;
	}

	public HashMap<String, List<String>> getParam() {
		return param;
	}

 

	public void setParam(HashMap<String, List<String>> param) {
		this.param = param;
	}

}
