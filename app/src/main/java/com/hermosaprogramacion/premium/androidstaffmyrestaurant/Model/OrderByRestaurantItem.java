package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;


import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class OrderByRestaurantItem {

	@SerializedName("orderAddress")
	private String orderAddress;

	@SerializedName("orderId")
	private int orderId;

	@SerializedName("totalPrice")
	private Double totalPrice;

	@SerializedName("orderFBID")
	private String orderFBID;

	@SerializedName("orderStatus")
	private int orderStatus;

	@SerializedName("RowNum")
	private String rowNum;

	@SerializedName("restaurantId")
	private int restaurantId;

	@SerializedName("transactionId")
	private String transactionId;

	@SerializedName("numOfItem")
	private int numOfItem;

	@SerializedName("cod")
	private boolean cod;

	@SerializedName("orderPhone")
	private String orderPhone;

	@SerializedName("orderDate")
	private Date orderDate;

	@SerializedName("orderName")
	private String orderName;

	public void setOrderAddress(String orderAddress){
		this.orderAddress = orderAddress;
	}

	public String getOrderAddress(){
		return orderAddress;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setTotalPrice(Double totalPrice){
		this.totalPrice = totalPrice;
	}

	public Double getTotalPrice(){
		return totalPrice;
	}

	public void setOrderFBID(String orderFBID){
		this.orderFBID = orderFBID;
	}

	public String getOrderFBID(){
		return orderFBID;
	}

	public void setOrderStatus(int orderStatus){
		this.orderStatus = orderStatus;
	}

	public int getOrderStatus(){
		return orderStatus;
	}

	public void setRowNum(String rowNum){
		this.rowNum = rowNum;
	}

	public String getRowNum(){
		return rowNum;
	}

	public void setRestaurantId(int restaurantId){
		this.restaurantId = restaurantId;
	}

	public int getRestaurantId(){
		return restaurantId;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setNumOfItem(int numOfItem){
		this.numOfItem = numOfItem;
	}

	public int getNumOfItem(){
		return numOfItem;
	}

	public void setCod(boolean cod){
		this.cod = cod;
	}

	public boolean isCod(){
		return cod;
	}

	public void setOrderPhone(String orderPhone){
		this.orderPhone = orderPhone;
	}

	public String getOrderPhone(){
		return orderPhone;
	}

	public void setOrderDate(Date orderDate){
		this.orderDate = orderDate;
	}

	public Date getOrderDate(){
		return orderDate;
	}

	public void setOrderName(String orderName){
		this.orderName = orderName;
	}

	public String getOrderName(){
		return orderName;
	}


}