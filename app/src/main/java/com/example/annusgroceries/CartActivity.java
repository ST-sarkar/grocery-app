package com.example.annusgroceries;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Model.CartData;
import com.example.annusgroceries.adapter.CartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartrec;
    List<CartData> cartList;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartrec=findViewById(R.id.cart_recView);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartList=new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this, cartList);
        cartrec.setLayoutManager(new LinearLayoutManager(this));
        cartrec.setAdapter(cartAdapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("CartData");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            cartList.add(dataSnapshot.getValue(CartData.class));

                    }

                    cartAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(CartActivity.this, "data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "error "+error, Toast.LENGTH_SHORT).show();
            }
        });



    }
}