package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.AddonItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model.OrderDetailItem;
import com.hermosaprogramacion.premium.androidstaffmyrestaurant.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OrderDetailItem> orderDetailItems;

    public MyOrderDetailAdapter(Context context, List<OrderDetailItem> orderDetailItems) {
        this.context = context;
        this.orderDetailItems = orderDetailItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (orderDetailItems.get(position).getAddon().toLowerCase().equals("none") ||
                orderDetailItems.get(position).getAddon().toLowerCase().equals("normal"))
            return 0;
        else
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType == 0 ? new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_order_detail_item, parent, false)) :
                new MyViewHolderAddon(LayoutInflater.from(context).inflate(R.layout.layout_order_detail_item_addon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            myViewHolder.txt_food_name.setText(orderDetailItems.get(position).getName());
            myViewHolder.txt_food_quantity.setText(new StringBuilder("Quantity: ").append(orderDetailItems.get(position).getQuantity()));
            myViewHolder.txt_food_size.setText(new StringBuilder("Size: ").append(orderDetailItems.get(position).getSize()));

        } else if (holder instanceof MyViewHolderAddon) {

            MyViewHolderAddon myViewHolderAddon = (MyViewHolderAddon)holder;
            myViewHolderAddon.txt_food_name.setText(orderDetailItems.get(position).getName());
            myViewHolderAddon.txt_food_quantity.setText(new StringBuilder("Quantity: ").append(orderDetailItems.get(position).getQuantity()));
            myViewHolderAddon.txt_food_size.setText(new StringBuilder("Size: ").append(orderDetailItems.get(position).getSize()));

            List<AddonItem> addons = new Gson().fromJson(orderDetailItems.get(position).getAddon(),new TypeToken<List<AddonItem>>(){}.getType());
            StringBuilder add_on_text = new StringBuilder();
            for (AddonItem addon: addons)
                add_on_text.append(addon.getName()).append("\n");
            myViewHolderAddon.txt_add_on.setText(add_on_text);

        }
    }

    @Override
    public int getItemCount() {
        return orderDetailItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_food_image)
        ImageView img_food_image;

        @BindView(R.id.txt_food_name)
        TextView txt_food_name;

        @BindView(R.id.txt_food_quantity)
        TextView txt_food_quantity;

        @BindView(R.id.txt_food_size)
        TextView txt_food_size;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MyViewHolderAddon extends RecyclerView.ViewHolder {

        @BindView(R.id.img_food_image)
        ImageView img_food_image;

        @BindView(R.id.txt_food_name)
        TextView txt_food_name;

        @BindView(R.id.txt_food_quantity)
        TextView txt_food_quantity;

        @BindView(R.id.txt_food_size)
        TextView txt_food_size;

        @BindView(R.id.txt_add_on)
        TextView txt_add_on;

        public MyViewHolderAddon(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
