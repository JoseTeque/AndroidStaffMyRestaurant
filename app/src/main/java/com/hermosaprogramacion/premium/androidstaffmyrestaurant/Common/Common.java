package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderByRestaurantItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.RestaurantOwner;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.RestaurantOwnerItem;

public class Common {
    public static String API_RESTAURANT_ENDPOINT = "http://192.168.8.103:3000/";
    public static String API_KEY = "1234";
    public static RestaurantOwnerItem currentrestaurantOwner;

    public static OrderByRestaurantItem currentOrder;

    public static String converStatusToString(int orderStatus) {

        switch (orderStatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Shipping";
            case 2:
                return "shipped";
            case -1:
                return "cancelled";
            default:
                return "cancelled";
        }
    }

    public static int converStatusToIndex(int orderStatus) {

        if (orderStatus == -1)
            return 3;
        else
            return orderStatus;

    }
}
