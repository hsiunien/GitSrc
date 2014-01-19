package com.softa.fscl.models;

import java.io.Serializable;

public class ShopCartItem  implements Serializable{
	private Commodity commodity;// 商品
	private int number;
	private String color;
	private  String size;
	public Commodity getCommodity() {
		return commodity;
	}
	public void setCommodity(Commodity commdity) {
		this.commodity = commdity;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
