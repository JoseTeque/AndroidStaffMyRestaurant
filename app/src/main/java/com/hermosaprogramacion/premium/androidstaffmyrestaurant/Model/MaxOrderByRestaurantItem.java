package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;


import com.google.gson.annotations.SerializedName;


public class MaxOrderByRestaurantItem {

	@SerializedName("maxRowNum")
	private int maxRowNum;

	public void setMaxRowNum(int maxRowNum){
		this.maxRowNum = maxRowNum;
	}

	public int getMaxRowNum(){
		return maxRowNum;
	}
}