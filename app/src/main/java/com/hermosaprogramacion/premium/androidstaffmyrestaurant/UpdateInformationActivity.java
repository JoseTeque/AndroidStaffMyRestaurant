package com.hermosaprogramacion.premium.androidstaffmyrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IMyRestaurantAPI;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateInformationActivity extends AppCompatActivity {

    @BindView(R.id.edtx_user_name)
    EditText edtxName;

    @BindView(R.id.btn_update)
    Button btnUpdate;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AlertDialog dialog;
    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        ButterKnife.bind(this);

        init();
        initView();
    }

    private void initView() {

        toolbar.setTitle(getString(R.string.update_information));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnUpdate.setOnClickListener(v -> {
            dialog.show();

            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(Account account) {

                    compositeDisposable.add(
                            myRestaurantAPI.postUpdateRestaurantOwner(Common.API_KEY,account.getId(),
                                    account.getPhoneNumber().toString(), edtxName.getText().toString())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(updateRestaurantOwner -> {

                                        if (updateRestaurantOwner.isSuccess())
                                        {
                                            //if user has been update, just refresh again
                                            compositeDisposable.add(myRestaurantAPI.getRestaurantOwner(Common.API_KEY, account.getId())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(restaurantOwner -> {

                                                        if (restaurantOwner.isSuccess())
                                                        {
                                                            Common.currentrestaurantOwner = restaurantOwner.getResult().get(0);
                                                            if (Common.currentrestaurantOwner.isStatus())
                                                            {
                                                                startActivity(new Intent(UpdateInformationActivity.this, HomeActivity.class));
                                                                finish();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(UpdateInformationActivity.this, R.string.permission_deneged, Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(UpdateInformationActivity.this, "[GET USER RESULT]" + restaurantOwner.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                        dialog.dismiss();

                                                    },throwable -> {
                                                        dialog.dismiss();
                                                        Toast.makeText(UpdateInformationActivity.this, "[ GET USER ]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }));

                                        }else
                                        {
                                            dialog.dismiss();
                                            Toast.makeText(UpdateInformationActivity.this, "[ UPDATE USER API RETURN ]" + updateRestaurantOwner.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    },throwable -> {
                                        dialog.dismiss();
                                        Toast.makeText(UpdateInformationActivity.this, "[UPDATE USER API]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Toast.makeText(UpdateInformationActivity.this, "[Account kit error]" + accountKitError.getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI= RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id == android.R.id.home){
            finish(); //close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
