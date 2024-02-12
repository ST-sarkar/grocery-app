package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserOrderItemActivity extends AppCompatActivity {

    TextView prdName,prdQtyUnit,prdPrice,prdDesc,date,time;
    CircleImageView circularImageView;
    Button cancle;
    String pdName,pdPrice,pdDesc,pdQt,pdUt,orderDate,orderTime,pdimgUri,prodKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_item);

        prdName = findViewById(R.id.product_name);
        prdDesc = findViewById(R.id.prod_desc);
        prdPrice = findViewById(R.id.price);
        prdQtyUnit = findViewById(R.id.qty_unit);
        circularImageView = findViewById(R.id.imageView);
        cancle=findViewById(R.id.btn_cancle);
        date=findViewById(R.id.tx_date);
        time=findViewById(R.id.tx_time);

        Intent intent=getIntent();
        pdDesc=intent.getStringExtra("desc");
        pdName=intent.getStringExtra("name");
        pdUt=intent.getStringExtra("unit");
        pdQt=intent.getStringExtra("qty");
        pdPrice=intent.getStringExtra("price");
        pdimgUri=intent.getStringExtra("imgurl");
        orderDate=intent.getStringExtra("date");
        orderTime=intent.getStringExtra("time");
        prodKey=intent.getStringExtra("productKey");


        prdName.setText(pdName);
        prdDesc.setText(pdDesc);
        prdPrice.setText(pdPrice);
        prdQtyUnit.setText(pdQt+" "+pdUt);
        date.setText(orderDate);
        time.setText(orderTime);
        Glide.with(UserOrderItemActivity.this)
                .load(pdimgUri)
                .into(circularImageView);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Orders");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(prodKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(UserOrderItemActivity.this, "Order Cancled Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserOrderItemActivity.this, "Order not cancled, some Issue Occured!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        
    }
}