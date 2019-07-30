package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShippingOrder{

	@SerializedName("result")
	private List<ShippingOrderItem> result;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setResult(List<ShippingOrderItem> result){
		this.result = result;
	}

	public List<ShippingOrderItem> getResult(){
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