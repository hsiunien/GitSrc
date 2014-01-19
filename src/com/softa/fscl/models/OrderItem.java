package com.softa.fscl.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderItem implements Serializable{
private List<ShopCartItem> shopcartItems;
private int state;//0未支付 1 正在配送  2已完成
private  String orderNo;
private String fukuang;//付款方式
private Date date;
private String name;
private String address;
private String phone;
private float price;
public float getPrice() {
	return price;
}
public void setPrice(float price) {
	this.price = price;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public List<ShopCartItem> getShopcartItems() {
	return shopcartItems;
}
public void setShopcartItems(List<ShopCartItem> shopcartItems) {
	this.shopcartItems = shopcartItems;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public String getOrderNo() {
	return orderNo;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public String getFukuang() {
	return fukuang;
}
public void setFukuang(String fukuang) {
	this.fukuang = fukuang;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String orderState(){
	switch (state) {
	case 0:
		return "未支付";
	case 1:
		return "正在配送";
	case 2:
		return "已完成";
	default:
		return "未知";
	}
 
}

}
