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
import com.example.annusgroceries.Model.uploadPost;
import com.example.annusgroceries.ProductDetailsActivity;
import com.example.annusgroceries.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {

    Context context;
    List<uploadPost> categoryList;
    List<String> keyList;

    public AllCategoryAdapter(Context context, List<uploadPost> categoryList,List<String> keyList) {
        this.context =context;
        this.categoryList = categoryList;
        this.keyList=keyList;
    }



    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);

        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {

        uploadPost data=categoryList.get(position);

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
                Intent intent=new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("name",data.getName());
                intent.putExtra("price",data.getPrice());
                intent.putExtra("unit",data.getUnit());
                intent.putExtra("qty",data.getQty());
                intent.putExtra("desc",data.getDesc());
                intent.putExtra("imgurl",data.getImgUri());
                intent.putExtra("key",keyList.get(holder.getAdapterPosition()));

                context.startActivity(intent);

            }
        });
        //Toast.makeText(context, "in allcat adapter", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateItemList(List<uploadPost> newItems) {
        categoryList = newItems;
        notifyDataSetChanged();  // This method notifies the adapter to refresh the RecyclerView
    }

    public  static class AllCategoryViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circularImageView;
        TextView name,desc,price,qty,unit;

        public AllCategoryViewHolder(@NonNull View itemView) {
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

// lets import all the category images