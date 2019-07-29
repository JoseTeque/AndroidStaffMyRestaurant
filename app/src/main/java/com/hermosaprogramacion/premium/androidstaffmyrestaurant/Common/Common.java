package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderByRestaurantItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.RestaurantOwner;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.RestaurantOwnerItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.R;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IFcmService;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;

public class Common {
    public static String API_RESTAURANT_ENDPOINT = "http://192.168.8.103:3000/";
    public static String API_KEY = "1234";
    public static final String REMEMBER_FBID = "REMEMBER_FBID";
    public static final String APY_KEY_TAG = "APY_KEY";
    public static final String NOTIFI_TITLE = "title";
    public static final String NOTIFI_CONTENT = "content";
    public static RestaurantOwnerItem currentrestaurantOwner;

    public static IFcmService getFCMService()
    {
        return RetrofitClient.getInstance("https://fcm.googleapis.com/").create(IFcmService.class);
    }

    public static OrderByRestaurantItem currentOrder;

    public static String converStatusToString(int orderStatus) {

        switch (orderStatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Shipping";
            case 2:
                return "Shipped";
            case -1:
                return "Cancelled";
            default:
                return "Cancelled";
        }
    }

    public static int converStatusToIndex(int orderStatus) {

        if (orderStatus == -1)
            return 3;
        else
            return orderStatus;

    }

    public static void showNotification(Context context, int notiId, String title, String body, Intent intent) {

        PendingIntent pendingIntent = null;
        if (intent != null)
            pendingIntent = PendingIntent.getActivity(context,notiId,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        String NOTIFICATIN_CHANEL_ID = "JDev_My_Restaurant_Staff";
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIN_CHANEL_ID,
                    "My Restaurant Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("My Restaurant Staff App");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATIN_CHANEL_ID);

        builder.setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.app_icon));

        if (pendingIntent != null)
            builder.setContentIntent(pendingIntent);
        Notification mNotification = builder.build();

        notificationManager.notify(notiId,mNotification);

    }

    public static String getTopicChannel(int restaurantId) {
        return new StringBuilder("Restaurant_").append(restaurantId).toString();
    }

    public static int currentToStatus(String status) {
        switch (status) {
            case "Placed":
                return 0;
            case "Shipping":
                return 1;
            case "Shipped":
                return 2;
            case "Cancelled":
                return -1;
        }
        return -1;
    }
}
