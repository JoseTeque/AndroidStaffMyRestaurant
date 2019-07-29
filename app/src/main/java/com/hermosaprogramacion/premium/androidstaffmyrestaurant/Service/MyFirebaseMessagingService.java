package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Service;

import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IMyRestaurantAPI;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;

import java.util.Map;
import java.util.Random;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
        compositeDisposable = new CompositeDisposable();

        Paper.init(this);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);

        String fbid = Paper.book().read(Common.REMEMBER_FBID);
        String apy_key = Paper.book().read(Common.APY_KEY_TAG);

        compositeDisposable.add(myRestaurantAPI.postToken(apy_key,fbid,newToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                    if (token.isSuccess())
                    {

                    }
                    else
                    {

                    }

                },throwable -> {
                    Toast.makeText(this, "[REFRESH TOKEN]" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //get notification object from fcm
        // because we want to retrieve notification while app killed, so we must use data payload

        Map<String,String> dataRecv= remoteMessage.getData();
        if (dataRecv != null)
        {
            Common.showNotification(this, new Random().nextInt(),
                    dataRecv.get(Common.NOTIFI_TITLE),
                    dataRecv.get(Common.NOTIFI_CONTENT),null);
        }
    }
}
