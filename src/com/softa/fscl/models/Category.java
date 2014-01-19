package com.softa.fscl.models;

import android.graphics.Bitmap;

public class Category {
	private int id;
	private String name;
	private Bitmap icon;
	private String url;
	public Category() {
		}
	public Category(String name ,Bitmap bmp, Object ...other){
		this.name=name;
		icon=bmp;
		if(other.length>0){
			if(other[0] instanceof Integer){
				id=(Integer) other[0];
			}			
		}
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
