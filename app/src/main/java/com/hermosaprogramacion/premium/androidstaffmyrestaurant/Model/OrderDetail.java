package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class OrderDetail{

	@SerializedName("result")
	private List<OrderDetailItem> result;

	@SerializedName("success")
	private boolean success;

	public void setResult(List<OrderDetailItem> result){
		this.result = result;
	}

	public List<OrderDetailItem> getResult(){
		return result;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}
}