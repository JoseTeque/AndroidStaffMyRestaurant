package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import com.google.gson.annotations.SerializedName;

public class AddonItem {

    @SerializedName("extraPrice")
    private double extraPrice;

    @SerializedName("description")
    private String description;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public void setExtraPrice(double extraPrice){
        this.extraPrice = extraPrice;
    }

    public double getExtraPrice(){
        return extraPrice;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
