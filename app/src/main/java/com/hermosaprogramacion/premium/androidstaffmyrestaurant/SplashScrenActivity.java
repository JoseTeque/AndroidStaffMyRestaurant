package com.hermosaprogramacion.premium.androidstaffmyrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IMyRestaurantAPI;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashScrenActivity extends AppCompatActivity {

    IMyRestaurantAPI myRestaurantAPI;
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

        init();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                            @Override
                            public void onSuccess(Account account) {

                                dialog.show();

                                compositeDisposable.add(myRestaurantAPI.getRestaurantOwner(Common.API_KEY, account.getId())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(restaurantOwner -> {

                                                    if (restaurantOwner.isSuccess()) //if user available in database
                                                    {
                                                        Common.currentrestaurantOwner = restaurantOwner.getResult().get(0);
                                                        if (Common.currentrestaurantOwner.isStatus())
                                                        {
                                                            startActivity(new Intent(SplashScrenActivity.this, HomeActivity.class));
                                                            finish();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(SplashScrenActivity.this,R.string.permission_deneged, Toast.LENGTH_SHORT).show();
                                                        }

                                                    } else {  //if not available user in database, star UpdateInformationActivity for register
                                                        startActivity(new Intent(SplashScrenActivity.this, UpdateInformationActivity.class));
                                                        finish();
                                                    }

                                                    dialog.dismiss();
                                                },
                                                throwable -> {
                                                    dialog.dismiss();
                                                    Toast.makeText(SplashScrenActivity.this, "[GET USER API]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                }));
                            }

                            @Override
                            public void onError(AccountKitError accountKitError) {
                                Toast.makeText(SplashScrenActivity.this, "Not sign in ! please sign in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SplashScrenActivity.this, MainActivity.class));
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(SplashScrenActivity.this, "You must accept this permission to use our app", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
    }
}
