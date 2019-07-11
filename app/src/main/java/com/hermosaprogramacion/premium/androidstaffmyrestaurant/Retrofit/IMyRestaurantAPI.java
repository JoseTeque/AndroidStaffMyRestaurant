package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.RestaurantOwner;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.UpdateRestaurantOwner;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyRestaurantAPI {

    @GET("restaurantOwner")
    Observable<RestaurantOwner> getRestaurantOwner(@Query("key") String key, @Query("fbid") String fbid);

//POST

    @POST("restaurantOwner")
    @FormUrlEncoded
    Observable<UpdateRestaurantOwner> postUpdateRestaurantOwner(@Field("key") String key,
                                                                @Field("fbid") String fbid,
                                                                @Field("userPhone") String phone,
                                                                @Field("userName") String name);
}
