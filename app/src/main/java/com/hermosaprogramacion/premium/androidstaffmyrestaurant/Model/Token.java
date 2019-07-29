package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Token {

    @SerializedName("result")
    private List<TokenItem> result;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public void setResult(List<TokenItem> result){
        this.result = result;
    }

    public List<TokenItem> getResult(){
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
