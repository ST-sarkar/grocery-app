package com.example.annusgroceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.annusgroceries.Model.OrderDetails;
import com.example.annusgroceries.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {
    Context context;
    List<OrderDetails> orderList;
    List<String> userKey,prodKey;

    public OrderAdapter(Context context, List<OrderDetails> orderList,List<String> userKey,List<String> prodKey) {
        this.context = context;
        this.orderList = orderList;
        this.prodKey=prodKey;
        this.userKey=userKey;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_view, parent, false);

        return new orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        OrderDetails orderDetails=orderList.get(position);

        //holder.categoryImage.setImageResource(data.getImgurl());
        holder.prdname.setText(orderDetails.getProdname()+"");
        holder.prdprice.setText(orderDetails.getProdPrice()+"");
        holder.prdqty.setText(orderDetails.getProdQty()+"  "+orderDetails.getProdUnit());
        Glide.with(holder.itemView.getContext())
                .load(orderDetails.getProdImgUrl())
                .into(holder.circleImageView);
        holder.userAddr.setText(orderDetails.getUserAddr()+"");
        holder.username.setText(orderDetails.getUserName()+"");
        holder.userphon.setText(orderDetails.getUserPhone()+"");
        holder.date.setText(orderDetails.getOrderDate()+"");
        holder.time.setText(orderDetails.getOrderTime()+"");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.cancel.setVisibility(view.VISIBLE);
                holder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index=holder.getAdapterPosition();
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Orders");
                        reference.child(userKey.get(index)).child(prodKey.get(index)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "product removed successfully", Toast.LENGTH_SHORT).show();
                                orderList.remove(index);
                                userKey.remove(index);
                                prodKey.remove(index);
                                notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "product not deleted "+e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder {

        TextView prdname,prdprice,prdqty,username,userphon,userAddr,date,time;
        ImageView cancel;
        CircleImageView circleImageView;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);

            prdname=itemView.findViewById(R.id.product_name);
            prdprice=itemView.findViewById(R.id.price);
            prdqty=itemView.findViewById(R.id.qty_unit);
            username=itemView.findViewById(R.id.tx_username);
            userphon=itemView.findViewById(R.id.tx_user_phone);
            date=itemView.findViewById(R.id.tx_date);
            time=itemView.findViewById(R.id.tx_time);
            userAddr=itemView.findViewById(R.id.tx_user_address);
            circleImageView=itemView.findViewById(R.id.imageView);
            cancel=itemView.findViewById(R.id.img_delete);

        }
    }
}
