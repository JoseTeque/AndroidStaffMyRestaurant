package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;


import com.google.gson.annotations.SerializedName;


public class RestaurantOwnerItem {

	@SerializedName("userPhone")
	private String userPhone;

	@SerializedName("name")
	private String name;

	@SerializedName("fbid")
	private String fbid;

	@SerializedName("restaurantId")
	private int restaurantId;

	@SerializedName("status")
	private boolean status;

	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}

	public String getUserPhone(){
		return userPhone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFbid(String fbid){
		this.fbid = fbid;
	}

	public String getFbid(){
		return fbid;
	}

	public void setRestaurantId(int restaurantId){
		this.restaurantId = restaurantId;
	}

	public int getRestaurantId(){
		return restaurantId;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}