package com.example.annusgroceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.annusgroceries.Admin.DeleteItemActivity;
import com.example.annusgroceries.Model.uploadPost;
import com.example.annusgroceries.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DeleteViewHolder> {

    Context Dcontext;
    List<uploadPost> DcategoryList;
    List<String> userList;

    public DeleteAdapter(DeleteItemActivity context, List<uploadPost> categoryList, List<String> userList) {
        this.Dcontext =context;
        this.DcategoryList = categoryList;
        this.userList=userList;
    }



    @NonNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(Dcontext).inflate(R.layout.category_item, parent, false);

        return new DeleteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteViewHolder holder, int position) {

        uploadPost data=DcategoryList.get(position);

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
                String user=userList.get(holder.getAdapterPosition());

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AllItems");
                reference.child(user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(Dcontext, "Product Successfully Removed ", Toast.LENGTH_SHORT).show();
                        DcategoryList.remove(holder.getAdapterPosition());
                        userList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Dcontext, "error :"+e, Toast.LENGTH_SHORT).show();
                    }
                });

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CartData");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            // Fetch posts for each user
                            ref.child(userSnapshot.getKey()).child(user).removeValue();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors
                    }
                });

            }
        });



        //Toast.makeText(context, "in allcat adapter", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return DcategoryList.size();
    }


    public void updateItemList(List<uploadPost> newItems) {
        DcategoryList = newItems;
        notifyDataSetChanged();  // This method notifies the adapter to refresh the RecyclerView
    }

    public  static class DeleteViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circularImageView;
        TextView name,desc,price,qty,unit;

        public DeleteViewHolder(@NonNull View itemView) {
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