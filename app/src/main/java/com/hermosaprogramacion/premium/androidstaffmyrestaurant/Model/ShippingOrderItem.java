package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;


import com.google.gson.annotations.SerializedName;


public class ShippingOrderItem {

	@SerializedName("shippingStatus")
	private int shippingStatus;

	@SerializedName("shipperId")
	private String shipperId;

	@SerializedName("orderId")
	private int orderId;

	public void setShippingStatus(int shippingStatus){
		this.shippingStatus = shippingStatus;
	}

	public int getShippingStatus(){
		return shippingStatus;
	}

	public void setShipperId(String shipperId){
		this.shipperId = shipperId;
	}

	public String getShipperId(){
		return shipperId;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}
}