package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;


import com.google.gson.annotations.SerializedName;


public class OrderDetailItem {

	@SerializedName("itemId")
	private int itemId;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("size")
	private String size;

	@SerializedName("orderId")
	private int orderId;

	@SerializedName("addon")
	private String addon;

	@SerializedName("orderFBID")
	private String orderFBID;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setAddon(String addon){
		this.addon = addon;
	}

	public String getAddon(){
		return addon;
	}

	public void setOrderFBID(String orderFBID){
		this.orderFBID = orderFBID;
	}

	public String getOrderFBID(){
		return orderFBID;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}
}