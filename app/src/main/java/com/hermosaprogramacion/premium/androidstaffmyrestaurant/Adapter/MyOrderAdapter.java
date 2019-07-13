package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Common.Common;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.HomeActivity;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Interface.ClickListener;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Interface.ILoadMore;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderByRestaurantItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.R;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TIPE_LOADING = 0;
    private static final int VIEW_TIPE_ITEM = 1;
    private Context context;
    private List<OrderByRestaurantItem> orderItemList;
    private SimpleDateFormat simpleDateFormat;

    private RecyclerView recyclerView;

    private ILoadMore iLoadMore;

    private   boolean isloading = false;

    private  int totalItemCount= 0, lastVisibleItem = 0, visibleThresHolds = 10;

    public MyOrderAdapter(Context context, List<OrderByRestaurantItem> orderItemList, RecyclerView recyclerView) {
        this.context = context;
        this.orderItemList = orderItemList;
        this.recyclerView = recyclerView;
        simpleDateFormat= new SimpleDateFormat("MM/dd/yyyy");

        //init
        LinearLayoutManager linearLayout = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayout.getItemCount();
                lastVisibleItem = linearLayout.findLastVisibleItemPosition();
                if (!isloading && totalItemCount <= lastVisibleItem + visibleThresHolds)
                {
                    if (iLoadMore != null)
                        iLoadMore.onLoadMore();
                    isloading = true;

                }
            }
        });
    }

    public void setLoaded(){isloading=false;}

    public void addItem(List<OrderByRestaurantItem> list)
    {
        int startInsertedIndex = orderItemList.size();
        orderItemList.addAll(list);
        notifyItemInserted(startInsertedIndex);
    }

    public void setiLoadMore(ILoadMore iLoadMore) {
        this.iLoadMore = iLoadMore;
    }

    @Override
    public int getItemViewType(int position) {
        if (orderItemList.get(position) == null) //IF WE SEET "NULL" VALUE IN LIST, WE WILL UNDERSTAD THIS IS LOADING STATE
            return VIEW_TIPE_LOADING;
        else
            return VIEW_TIPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        if (viewType == VIEW_TIPE_ITEM)
        {
            itemView =  LayoutInflater.from(context).inflate(R.layout.layout_order,parent,false);
            return new ViewHolder(itemView);
        }
        else
        {
            itemView =  LayoutInflater.from(context).inflate(R.layout.layout_loadingholder_item,parent,false);
            return new MyLoadingHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder)
        {
            ViewHolder viewHolder = (ViewHolder)holder;

            OrderByRestaurantItem orderItem= orderItemList.get(position);

            viewHolder.txt_order_Address.setText(orderItem.getOrderAddress());
            viewHolder.txt_order_phone.setText(orderItem.getOrderPhone());
            viewHolder.txt_order_status.setText(Common.converStatusToString(orderItem.getOrderStatus()));
            viewHolder.txt_num_of_item.setText(new StringBuilder("Num of item: ").append(orderItem.getNumOfItem()));
            viewHolder.txt_order_total_price.setText(new StringBuilder("$").append(orderItem.getTotalPrice()));
            viewHolder.txt_order_number.setText(new StringBuilder("Num of orden: ").append(orderItem.getOrderId()));
            viewHolder.txt_order_date.setText(new StringBuilder(simpleDateFormat.format(orderItem.getOrderDate())));

            if (orderItem.isCod())
                viewHolder.txt_order_cod.setText(new StringBuilder("Cash on delivery"));
            else
                viewHolder.txt_order_cod.setText(new StringBuilder("TransId: ").append(orderItem.getTransactionId()));
        }
        else if (holder instanceof MyLoadingHolder)
        {
            MyLoadingHolder myLoadingHolder = (MyLoadingHolder)holder;

            myLoadingHolder.progressBAR.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_order_number)
        TextView txt_order_number;

        @BindView(R.id.txt_order_status)
        TextView txt_order_status;

        @BindView(R.id.txt_order_phone)
        TextView txt_order_phone;

        @BindView(R.id.txt_order_Address)
        TextView txt_order_Address;

        @BindView(R.id.txt_order_cod)
        TextView txt_order_cod;

        @BindView(R.id.txt_order_date)
        TextView txt_order_date;

        @BindView(R.id.txt_order_total_price)
        TextView txt_order_total_price;

        @BindView(R.id.txt_num_of_item)
        TextView txt_num_of_item;

        ClickListener clickListener;

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            clickListener.listener(v,getAdapterPosition());
        }
    }

    public class MyLoadingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBAR)
        ProgressBar progressBAR;


        public MyLoadingHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
