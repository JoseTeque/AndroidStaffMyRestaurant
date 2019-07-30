package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class HotFood{

	@SerializedName("result")
	private List<HotFoodItem> result;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setResult(List<HotFoodItem> result){
		this.result = result;
	}

	public List<HotFoodItem> getResult(){
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