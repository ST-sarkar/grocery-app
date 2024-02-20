package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.annusgroceries.Model.CartData;
import com.example.annusgroceries.Model.OrderDetails;
import com.example.annusgroceries.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ProductDetailsActivity extends AppCompatActivity implements PaymentResultListener {
    ImageView cart, back,img,seeCart;
    Button buy;
    ImageView minus,plus;
    TextView proName, proPrice, proDesc, proQty, proUnit;

    String name, price, desc, qty, unit;
    String image,key,orgprice;
    String userName,userAddr,userPhone,userEmail;
    String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Checkout.preload(getApplicationContext());

        Intent i = getIntent();

        name = i.getStringExtra("name");
        image = i.getStringExtra("imgurl");
        price = i.getStringExtra("price");
        orgprice=price;
        desc = i.getStringExtra("desc");
        qty = i.getStringExtra("qty");
        unit = i.getStringExtra("unit");
        key=i.getStringExtra("key");


        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        cart = findViewById(R.id.cart_img);
        seeCart=findViewById(R.id.cart);
        img=findViewById(R.id.big_image);
        buy=findViewById(R.id.button);
        back = findViewById(R.id.back2);
        proQty = findViewById(R.id.qty);
        proUnit = findViewById(R.id.unit);
        minus=findViewById(R.id.minus_btn);
        plus=findViewById(R.id.plus_btn);

        proName.setText(name);
        proPrice.setText(price);
        proDesc.setText(desc);
        proQty.setText(qty);
        proUnit.setText(unit);
        //img.setImageResource(image);
        Glide.with(this)
                .load(image)
                .into(img);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt=Integer.valueOf(qty);
                if(qt>1 && Integer.valueOf(price)>1) {
                    qt=qt-1;
                    qty=String.valueOf(qt);
                    price = String.valueOf((Integer.valueOf(price)-Integer.valueOf(orgprice)));
                    proPrice.setText(price);
                    proQty.setText(qty);

                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt=Integer.valueOf(qty);
                if(Integer.valueOf(price)>1) {
                    qt = qt + 1;
                    qty = String.valueOf(qt);
                    price = String.valueOf((Integer.valueOf(price) + Integer.valueOf(orgprice)));
                    proPrice.setText(price);
                    proQty.setText(qty);

                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProductDetailsActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("CartData");
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                try {
                    if (!name.isEmpty() && !desc.isEmpty() && !price.isEmpty() && !unit.isEmpty() && !qty.isEmpty() && !image.isEmpty()) {
                        //creating object of cart data to set data in realtime database
                        CartData u = new CartData(name, desc, price, unit, qty, image);
                        reference.child(user.getUid()).child(key).setValue(u);

                        Toast.makeText(ProductDetailsActivity.this, "product added to cart", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ProductDetailsActivity.this, "product not added to cart", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ProductDetailsActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        seeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetailsActivity.this, SplashInsideActivity.class);
                intent.putExtra("from","cart");
                startActivity(intent);
            }
        });


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float amount=Float.valueOf(price)*100;

                startPayment(String.valueOf(amount));
            }
        });

    }

        public void startPayment(String amt) {
            Checkout checkout = new Checkout();

            checkout.setKeyID("rzp_test_DprFLoIlqS0QFM");

            checkout.setImage(R.drawable.cartbutton);


            final ProductDetailsActivity activity = this;

            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
            userUid=FirebaseAuth.getInstance().getCurrentUser().getUid();

            reference.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        User u = snapshot.getValue(User.class);
                        userAddr= u.getAddress();
                        userName=u.getName();
                        userPhone=u.getPhone();
                        userEmail=u.getEmail();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(activity, "error "+error, Toast.LENGTH_SHORT).show();
                }
            });

            try {
                JSONObject options = new JSONObject();

                options.put("name", userName+"");
                options.put("description", "Reference No. "+System.currentTimeMillis());
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
                //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                options.put("theme.color", "#3399cc");
                options.put("currency", "INR");
                options.put("amount", amt+"");//pass amount in currency subunits
                options.put("prefill.email", userEmail+"");
                options.put("prefill.contact",userPhone+"");
                JSONObject retryObj = new JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                checkout.open(activity, options);

            } catch(Exception e) {
                Toast.makeText(activity, "error in payment", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment of "+price+" successfull \n"+s, Toast.LENGTH_SHORT).show();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Orders");
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = time.format(formatter);
        OrderDetails order=new OrderDetails(String.valueOf(today),formattedTime,name,desc,price,qty,unit,image,userName,userPhone,userAddr);
        reference.child(userUid).child(key).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProductDetailsActivity.this, "oreder placed successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductDetailsActivity.this, "order not placed!!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Unsuccessfull "+s, Toast.LENGTH_SHORT).show();
    }
}