package com.hermosaprogramacion.premium.androidstaffmyrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.HotFoodItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.IMyRestaurantAPI;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HotFoodActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pieChart)
    PieChart pieChart;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<PieEntry> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_food);

        init();
        initView();
        loadChart();
    }

    private void loadChart() {
        entryList = new ArrayList<>();
        compositeDisposable.add(myRestaurantAPI.getHotFood(Common.API_KEY)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(hotFood -> {
            if (hotFood.isSuccess())
            {
                int i= 0;
                for (HotFoodItem hotFoodItem: hotFood.getResult())
                {
                    entryList.add(new PieEntry(Float.parseFloat(String.valueOf(hotFoodItem.getPercent())), hotFoodItem.getName()));
                    i++;
                }

                PieDataSet dataSet= new PieDataSet(entryList, "Hotes Food");
                PieData data = new PieData();
                data.setDataSet(dataSet);
                data.setValueTextSize(14f);
                data.setValueFormatter(new PercentFormatter(pieChart));

                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                pieChart.setData(data);
                pieChart.animateXY(2000,2000);
                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);

                pieChart.invalidate();

            }else
            {
                Toast.makeText(this, "[GET HOT FOOD]" +hotFood.getMessage(), Toast.LENGTH_SHORT).show();
            }

        },throwable -> {
            Toast.makeText(this, "[GET HOT FOOD]" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }));
    }

    private void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("HOT FOOD");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
