package com.hermosaprogramacion.premium.androidstaffmyrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Adapter.MyOrderDetailAdapter;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.FCMResponse;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.FCMSendData;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.Status;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IFcmService;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IMyRestaurantAPI;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitFCMClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_number_order)
    TextView txt_number_order;

    @BindView(R.id.spinner_status)
    AppCompatSpinner spinner_status;

    @BindView(R.id.recycler_order_detail)
    RecyclerView recycler_order_detail;

    IMyRestaurantAPI myRestaurantAPI;
    IFcmService iFcmService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        init();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.order_Detail));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_order_detail.setLayoutManager(layoutManager);
        recycler_order_detail.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        txt_number_order.setText(new StringBuilder("Order number #: ").append(Common.currentOrder.getOrderId()));

        initStatusSpinner();

        loadOrderDetail();
    }

    private void loadOrderDetail() {
        dialog.show();

        compositeDisposable.add(myRestaurantAPI.getOrderDetail(Common.API_KEY, Common.currentOrder.getOrderId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(orderDetail -> {

            if (orderDetail.isSuccess())
            {
                if (orderDetail.getResult().size() > 0)
                {
                    MyOrderDetailAdapter adapter= new MyOrderDetailAdapter(this,orderDetail.getResult());
                    recycler_order_detail.setAdapter(adapter);
                }

            }
            dialog.dismiss();

        }, throwable -> {
            dialog.dismiss();
            Toast.makeText(this, "[GET ORDER DETAIL]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }));
    }

    private void initStatusSpinner() {
        List<Status> statusList = new ArrayList<Status>();

        statusList.add(new Status(0, "Placed")); //Index 0
        statusList.add(new Status(1, "Shipping")); //Index 1
      //  statusList.add(new Status(2, "Shipped")); //Index 2
        statusList.add(new Status(3, "Cancelled")); //Index 3

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(adapter);
        spinner_status.setSelection(Common.converStatusToIndex(Common.currentOrder.getOrderStatus()));
}

    private void init() {
        iFcmService= RetrofitFCMClient.getInstance().create(IFcmService.class);
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            finish();
            return true;
        }
        else if (id == R.id.action_save)
        {
            updateOrder();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateOrder() {

        int status = Common.currentToStatus(spinner_status.getSelectedItem().toString());
        if (status == 1) //shipping status
        {
            compositeDisposable.add(myRestaurantAPI.updateOrder(Common.API_KEY,Common.currentOrder.getOrderId()
                    ,Common.currentToStatus(spinner_status.getSelectedItem().toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(updateOrder -> {
                        if (updateOrder.isSuccess())
                        {
                            Common.currentOrder.setOrderStatus(Common.currentToStatus(spinner_status.getSelectedItem().toString()));

                          compositeDisposable.add(myRestaurantAPI.postShippingOrder(Common.API_KEY,
                                  Common.currentOrder.getOrderId(),Common.currentrestaurantOwner.getRestaurantId())
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(shippingOrder -> {

                              if (shippingOrder.isSuccess())
                              {
                                  //get Token send notification
                                  compositeDisposable.add(myRestaurantAPI.getToken(Common.API_KEY,Common.currentOrder.getOrderFBID())
                                          .subscribeOn(Schedulers.io())
                                          .observeOn(AndroidSchedulers.mainThread())
                                          .subscribe(token -> {

                                              if (token.isSuccess())
                                              {
                                                  Map<String,String> messageSend = new HashMap<>();
                                                  messageSend.put(Common.NOTIFI_TITLE, "Your Order has been updated..");
                                                  messageSend.put(Common.NOTIFI_CONTENT, new StringBuilder("Your Order ")
                                                          .append(Common.currentOrder.getOrderId())
                                                          .append(" has been update to ")
                                                          .append(Common.converStatusToString(Common.currentOrder.getOrderStatus())).toString());

                                                  FCMSendData sendData = new FCMSendData(token.getResult().get(0).getToken(),messageSend);

                                                  compositeDisposable.add(iFcmService.sendNotification(sendData).subscribeOn(Schedulers.io())
                                                          .observeOn(AndroidSchedulers.mainThread())
                                                          .subscribe(fcmResponse -> {

                                                              Toast.makeText(OrderDetailActivity.this, "Update Order", Toast.LENGTH_SHORT).show();

                                                          }, throwable -> {
                                                              Toast.makeText(this, "Order was update but can't send notification..", Toast.LENGTH_SHORT).show();
                                                          }));

                                              }

                                          },throwable -> {

                                          }));
                              }
                              else
                              {
                                  Toast.makeText(this, "" + shippingOrder.getMessage(), Toast.LENGTH_SHORT).show();
                              }

                          },throwable -> {
                              Toast.makeText(this, "[SET SHIPPER]" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                          }));
                        }

                    },throwable -> {
                        Toast.makeText(this, "[UPDATE ORDER]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        }
        else {
            compositeDisposable.add(myRestaurantAPI.updateOrder(Common.API_KEY,Common.currentOrder.getOrderId()
                    ,Common.currentToStatus(spinner_status.getSelectedItem().toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(updateOrder -> {
                        if (updateOrder.isSuccess())
                        {
                            Common.currentOrder.setOrderStatus(Common.currentToStatus(spinner_status.getSelectedItem().toString()));

                            //get Token send notification
                            compositeDisposable.add(myRestaurantAPI.getToken(Common.API_KEY,Common.currentOrder.getOrderFBID())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(token -> {

                                        if (token.isSuccess())
                                        {
                                            Map<String,String> messageSend = new HashMap<>();
                                            messageSend.put(Common.NOTIFI_TITLE, "Your Order has been updated..");
                                            messageSend.put(Common.NOTIFI_CONTENT, new StringBuilder("Your Order ")
                                                    .append(Common.currentOrder.getOrderId())
                                                    .append(" has been update to ")
                                                    .append(Common.converStatusToString(Common.currentOrder.getOrderStatus())).toString());

                                            FCMSendData sendData = new FCMSendData(token.getResult().get(0).getToken(),messageSend);

                                            compositeDisposable.add(iFcmService.sendNotification(sendData).subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(fcmResponse -> {

                                                        Toast.makeText(OrderDetailActivity.this, "Update Order", Toast.LENGTH_SHORT).show();

                                                    }, throwable -> {
                                                        Toast.makeText(this, "Order was update but can't send notification..", Toast.LENGTH_SHORT).show();
                                                    }));

                                        }

                                    },throwable -> {

                                    }));
                        }

                    },throwable -> {
                        Toast.makeText(this, "[UPDATE ORDER]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        }

    }
}
