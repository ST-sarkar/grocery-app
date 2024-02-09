package com.example.annusgroceries.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.annusgroceries.Model.OrderDetails;
import com.example.annusgroceries.R;
import com.example.annusgroceries.UserOrderItemActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.UserOrderViewHolder> {
    Context context;
    List<OrderDetails> orderList;
    List<String> productKey;

    public UserOrderAdapter(Context context, List<OrderDetails> orderList,List<String> productKe) {
        this.context =context;
        this.orderList = orderList;
        this.productKey=productKe;
    }

    @NonNull
    @Override
    public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);

        return new UserOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderViewHolder holder, int position) {

        OrderDetails data=orderList.get(position);

        //holder.categoryImage.setImageResource(data.getImgurl());
        holder.name.setText(data.getProdname());
        holder.price.setText(data.getProdPrice());
        holder.qty.setText(data.getProdQty());
        holder.unit.setText(data.getProdUnit());
        holder.desc.setText(data.getProdDesc());
        Glide.with(holder.itemView.getContext())
                .load(data.getProdImgUrl())
                .into(holder.circularImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cintent=new Intent(context, UserOrderItemActivity.class);
                cintent.putExtra("name",data.getProdname());
                cintent.putExtra("price",data.getProdPrice());
                cintent.putExtra("unit",data.getProdUnit());
                cintent.putExtra("qty",data.getProdQty());
                cintent.putExtra("desc",data.getProdDesc());
                cintent.putExtra("imgurl",data.getProdImgUrl());
                cintent.putExtra("date",data.getOrderDate());
                cintent.putExtra("time",data.getOrderTime());

                cintent.putExtra("productKey",productKey.get(holder.getAdapterPosition()));

                context.startActivity(cintent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class UserOrderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circularImageView;
        TextView name,desc,price,qty,unit;
        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            desc = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            circularImageView = itemView.findViewById(R.id.imageView);
            unit=itemView.findViewById(R.id.unit);
        }
    }
}
