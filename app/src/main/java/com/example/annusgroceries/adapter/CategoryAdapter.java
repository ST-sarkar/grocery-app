package com.example.annusgroceries.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Categries.FruitsActivity;
import com.example.annusgroceries.Model.Category;
import com.example.annusgroceries.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(com.example.annusgroceries.R.layout.category_row_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.categoryImage.setImageResource(categoryList.get(position).getImageurl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FruitsActivity.class);
                if(holder.getAdapterPosition()==0){

                    intent.putExtra("type","Fruits");

                }else if(holder.getAdapterPosition()==1){
                    intent.putExtra("type","Fishes");

                }else if(holder.getAdapterPosition()==2){
                    intent.putExtra("type","Meets");

                }else if(holder.getAdapterPosition()==3){
                    intent.putExtra("type","Veggies");

                }else if(holder.getAdapterPosition()==4){
                    intent.putExtra("type","Vaverange");

                }else if(holder.getAdapterPosition()==5){
                    intent.putExtra("type","Eggs");

                }else if(holder.getAdapterPosition()==6){
                    intent.putExtra("type","Cookies");

                }else if(holder.getAdapterPosition()==7){
                    intent.putExtra("type","Juice");

                }
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(com.example.annusgroceries.R.id.categoryImage);

        }
    }

}

// lets import all the category images