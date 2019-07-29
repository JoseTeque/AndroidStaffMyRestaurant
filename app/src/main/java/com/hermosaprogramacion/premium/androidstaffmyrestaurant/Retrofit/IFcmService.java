package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.FCMResponse;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.FCMSendData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFcmService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA8e0NHtA:APA91bFlBavtFuf2GqZ3kaNJBbQ3j5JMN8QabRBoGL7400FS2m9JsOwh0toZqUsuwl2ZDmLOMn-mpUWwBL54w6AO9aRY7lQaMHI-l-ZSPd7DF_vsbLciHmu5nBZSVOzDJl2CbnBHfWey"
    })
    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);
}
