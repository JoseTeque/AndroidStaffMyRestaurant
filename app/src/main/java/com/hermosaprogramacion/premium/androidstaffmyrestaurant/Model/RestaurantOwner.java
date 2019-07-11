package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class RestaurantOwner{

	@SerializedName("result")
	private List<RestaurantOwnerItem> result;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setResult(List<RestaurantOwnerItem> result){
		this.result = result;
	}

	public List<RestaurantOwnerItem> getResult(){
		return result;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}