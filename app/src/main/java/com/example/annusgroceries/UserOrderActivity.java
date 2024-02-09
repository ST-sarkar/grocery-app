package com.example.annusgroceries;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Model.OrderDetails;
import com.example.annusgroceries.adapter.UserOrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserOrderActivity extends AppCompatActivity {

    RecyclerView orderRecview;
    UserOrderAdapter userOrderAdapter;
    List<OrderDetails> userOrderList;
    List<String> prodKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        orderRecview=findViewById(R.id.order_recView);

        userOrderList = new ArrayList<>();
        prodKey = new ArrayList<>();
        userOrderAdapter = new UserOrderAdapter(UserOrderActivity.this, userOrderList,prodKey);
        orderRecview.setLayoutManager(new LinearLayoutManager(this));
        orderRecview.setAdapter(userOrderAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userOrderList.clear(); // Clear the list before adding new data

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            OrderDetails orderDetails = dataSnapshot1.getValue(OrderDetails.class);
                            userOrderList.add(orderDetails);
                            prodKey.add(dataSnapshot1.getKey());
                            //Toast.makeText(ViewOrderActivity.this, "inside add list " + orderDetails.getOrderDate() + orderDetails.getProdname(), Toast.LENGTH_SHORT).show();
                        }

                    userOrderAdapter.notifyDataSetChanged(); // Notify adapter here after data is added
                } else {
                    Toast.makeText(UserOrderActivity.this, "data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserOrderActivity.this, "error: " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }
}