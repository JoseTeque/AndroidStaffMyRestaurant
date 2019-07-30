package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.HotFood;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.MaxOrderByRestaurant;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderByRestaurant;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderDetail;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.RestaurantOwner;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.ShippingOrder;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.Token;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.UpdateOrder;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.UpdateRestaurantOwner;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IMyRestaurantAPI {
    //GET

    @GET("token")
    Observable<Token> getToken(@Query("key") String key,
                               @Query("fbid") String fbid);

    @GET("shippingOrder")
    Observable<ShippingOrder> getShippingOrder(@Query("key") String key,
                                               @Query("restaurantId") int restaurantId);

    @GET("restaurantOwner")
    Observable<RestaurantOwner> getRestaurantOwner(@Query("key") String key, @Query("fbid") String fbid);

    @GET("orderByRestaurant")
    Observable<OrderByRestaurant> getOrderByRestaurant(@Query("key") String key,
                                                       @Query("restaurantId") int restaurantId,
                                                       @Query("from") int from,
                                                       @Query("to") int to);

    @GET("maxOrderByRestaurant")
    Observable<MaxOrderByRestaurant> getMaxOrderByRestaurant(@Query("key") String key,
                                                             @Query("restaurantId") int restaurantId);

    @GET("orderDetailByRestaurant")
    Observable<OrderDetail> getOrderDetail(@Query("key") String key,
                                           @Query("orderId") int orderId);

    @GET("hotfood")
    Observable<HotFood> getHotFood(@Query("key") String key);

    //POST

    @POST("token")
    @FormUrlEncoded
    Observable<Token> postToken(@Field("key") String key,
                                @Field("fbid") String fbid,
                                @Field("token") String token);

    @POST("restaurantOwner")
    @FormUrlEncoded
    Observable<UpdateRestaurantOwner> postUpdateRestaurantOwner(@Field("key") String key,
                                                                @Field("fbid") String fbid,
                                                                @Field("userPhone") String phone,
                                                                @Field("userName") String name);

    @POST("shippingOrder")
    @FormUrlEncoded
    Observable<ShippingOrder> postShippingOrder(@Field("key") String key,
                                                @Field("orderId") int orderId,
                                                @Field("restaurantId") int restaurantId);

    //PUT
    @PUT("updateOrder")
    @FormUrlEncoded
    Observable<UpdateOrder> updateOrder(@Field("key") String key,
                                        @Field("orderId") int orderId,
                                        @Field("orderStatus") int orderStatus);
}
