package com.hermosaprogramacion.premium.androidstaffmyrestaurant;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Adapter.MyOrderAdapter;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Interface.ILoadMore;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.MaxOrderByRestaurant;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderByRestaurant;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderByRestaurantItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IMyRestaurantAPI;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ILoadMore {

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;

    @BindView(R.id.recycler_order)
    RecyclerView recycler_order;

    LayoutAnimationController animationController;
    private int maxData= 0;
    MyOrderAdapter myOrderAdapter;
    List<OrderByRestaurantItem> orderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        initView();

        subscribeToTopic(Common.getTopicChannel(Common.currentrestaurantOwner.getRestaurantId()));

        getMaxOrder();
    }

    private void subscribeToTopic(String topicChannel) {
        FirebaseMessaging.getInstance()
                .subscribeToTopic(topicChannel)
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "subscribe failed ! you may not receive new order notification..", Toast.LENGTH_SHORT).show();

                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {


                    }
                    else {
                        Toast.makeText(this, "Failed.." + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    }

                });
    }


    private void getMaxOrder() {
        dialog.show();

        compositeDisposable.add(myRestaurantAPI.getMaxOrderByRestaurant(Common.API_KEY, Common.currentrestaurantOwner.getRestaurantId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(maxOrderByRestaurant -> {

            if (maxOrderByRestaurant.isSuccess())
            {
                maxData = maxOrderByRestaurant.getResult().get(0).getMaxRowNum();
                dialog.dismiss();
                getAllOrders(0,10,false);
            }
            else
            {
                Toast.makeText(this, " " + maxOrderByRestaurant.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        }, throwable -> {

        }));
    }

    private void getAllOrders(int from, int to, boolean isRefresh) {
        dialog.show();

        compositeDisposable.add(myRestaurantAPI.getOrderByRestaurant(Common.API_KEY,Common.currentrestaurantOwner.getRestaurantId(),
                from,to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderByRestaurant -> {

                    if (orderByRestaurant.isSuccess())
                    {
                        if (orderByRestaurant.getResult().size() > 0)
                        {
                            if (myOrderAdapter == null)
                            {
                                orderItemList = new ArrayList<>();
                                orderItemList = orderByRestaurant.getResult();
                                myOrderAdapter= new MyOrderAdapter(this, orderItemList, recycler_order);
                                myOrderAdapter.setiLoadMore(this);
                                recycler_order.setAdapter(myOrderAdapter);
                                recycler_order.setLayoutAnimation(animationController);
                            }
                            else
                            {
                                if (!isRefresh)
                                {
                                    orderItemList.remove(orderItemList.size()-1);
                                    orderItemList = orderByRestaurant.getResult();
                                    myOrderAdapter.addItem(orderItemList);
                                }
                                else
                                {
                                    orderItemList = new ArrayList<>();
                                    orderItemList = orderByRestaurant.getResult();
                                    myOrderAdapter= new MyOrderAdapter(this, orderItemList, recycler_order);
                                    myOrderAdapter.setiLoadMore(this);
                                    recycler_order.setAdapter(myOrderAdapter);
                                    recycler_order.setLayoutAnimation(animationController);
                                }

                            }

                        }
                        else
                        {
                            Toast.makeText(this, "Order empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "[GET ORDER RESULT]" + orderByRestaurant.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                },throwable -> {
                    dialog.dismiss();
                    Toast.makeText(this, "[GET ORDER]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                })
        );
    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    private void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_order.setLayoutManager(layoutManager);
        recycler_order.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        animationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Restaurant Order");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getAllOrders(0,10,true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hot_food) {
            startActivity(new Intent(HomeActivity.this,HotFoodActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoadMore() {
        if (myOrderAdapter.getItemCount() < maxData)
        {
            orderItemList.add(null);
            myOrderAdapter.notifyItemInserted(orderItemList.size()-1);

            getAllOrders(myOrderAdapter.getItemCount() + 1, myOrderAdapter.getItemCount() + 10 , false);
            myOrderAdapter.notifyDataSetChanged();
            myOrderAdapter.setLoaded();
        }
        else
        {
            Toast.makeText(this, "Max data to load..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
