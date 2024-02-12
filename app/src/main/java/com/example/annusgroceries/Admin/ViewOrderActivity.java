package com.example.annusgroceries.Admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Model.OrderDetails;
import com.example.annusgroceries.R;
import com.example.annusgroceries.adapter.OrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderActivity extends AppCompatActivity {
    RecyclerView rec_order;
    List<OrderDetails> orderList;
    OrderAdapter orderAdapter;
    List<String> userKey,prodKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        rec_order = findViewById(R.id.rec_order);

        orderList = new ArrayList<>();
        userKey = new ArrayList<>();
        prodKey = new ArrayList<>();
        orderAdapter = new OrderAdapter(ViewOrderActivity.this, orderList,userKey,prodKey);
        rec_order.setLayoutManager(new LinearLayoutManager(this));
        rec_order.setAdapter(orderAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    orderList.clear(); // Clear the list before adding new data
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            OrderDetails orderDetails = dataSnapshot1.getValue(OrderDetails.class);
                            orderList.add(orderDetails);
                            userKey.add(dataSnapshot.getKey());
                            prodKey.add(dataSnapshot1.getKey());
                            //Toast.makeText(ViewOrderActivity.this, "inside add list " + orderDetails.getOrderDate() + orderDetails.getProdname(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    orderAdapter.notifyDataSetChanged(); // Notify adapter here after data is added
                } else {
                    Toast.makeText(ViewOrderActivity.this, "data not found outside", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewOrderActivity.this, "error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}