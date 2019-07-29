package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import com.google.gson.annotations.SerializedName;

public class TokenItem {

    @SerializedName("fbid")
    private String fbid;

    @SerializedName("token")
    private String token;

    public void setFbid(String fbid){
        this.fbid = fbid;
    }

    public String getFbid(){
        return fbid;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
