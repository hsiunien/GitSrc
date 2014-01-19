package com.softa.fscl.models;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Picture implements Serializable{
	private Bitmap mBitmap;
	private String url;
	public Bitmap getmBitmap() {
		return mBitmap;
	}
	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
