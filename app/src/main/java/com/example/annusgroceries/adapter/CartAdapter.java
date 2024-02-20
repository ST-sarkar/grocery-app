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
import com.example.annusgroceries.Model.CartData;
import com.example.annusgroceries.ProductDetailsActivity;
import com.example.annusgroceries.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<CartData> cartList;

    public CartAdapter(Context context, List<CartData> cartList) {
        this.context =context;
        this.cartList = cartList;
    }



    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        CartData data=cartList.get(position);

        //holder.categoryImage.setImageResource(data.getImgurl());
        holder.name.setText(data.getName());
        holder.price.setText(data.getPrice());
        holder.qty.setText(data.getQty());
        holder.unit.setText(data.getUnit());
        holder.desc.setText(data.getDesc());
        Glide.with(holder.itemView.getContext())
                .load(data.getImgUri())
                .into(holder.circularImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cintent=new Intent(context, ProductDetailsActivity.class);
                cintent.putExtra("name",data.getName());
                cintent.putExtra("price",data.getPrice());
                cintent.putExtra("unit",data.getUnit());
                cintent.putExtra("qty",data.getQty());
                cintent.putExtra("desc",data.getDesc());
                cintent.putExtra("imgurl",data.getImgUri());

                context.startActivity(cintent);

            }
        });
        //Toast.makeText(context, "in allcat adapter", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    /*
    public void updateItemList(List<CartData> newItems) {
        cartList = newItems;
        notifyDataSetChanged();  // This method notifies the adapter to refresh the RecyclerView
    }

     */

    public  static class CartViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circularImageView;
        TextView name,desc,price,qty,unit;

        public CartViewHolder(@NonNull View itemView) {
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

// lets import all the category images}
