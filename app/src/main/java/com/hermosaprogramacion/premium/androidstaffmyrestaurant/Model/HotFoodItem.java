package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;


import com.google.gson.annotations.SerializedName;


public class HotFoodItem {

	@SerializedName("itemId")
	private int itemId;

	@SerializedName("name")
	private String name;

	@SerializedName("percent")
	private double percent;

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPercent(double percent){
		this.percent = percent;
	}

	public double getPercent(){
		return percent;
	}
}