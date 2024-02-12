package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashInsideActivity extends AppCompatActivity {
    LottieAnimationView lottie;
    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_inside);

        lottie=findViewById(R.id.lottfile);
        tv1=findViewById(R.id.tv_1);
        tv2=findViewById(R.id.tv_2);


        Intent intent=getIntent();
         String from=intent.getStringExtra("from");

         if (from.equals("cart")){
             lottie.setAnimation(R.raw.cart);
             tv1.setText("GoingTo");
             tv2.setText("Cart");
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {

                     startActivity(new Intent(SplashInsideActivity.this, CartActivity.class));
                     finish();
                 }
             },3000);

         } else if (from.equals("order")) {
             lottie.setAnimation(R.raw.orders);
             tv1.setText("GoingTo");
             tv2.setText("Orders");
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {

                     startActivity(new Intent(SplashInsideActivity.this, UserOrderActivity.class));
                     finish();
                 }
             },3000);

         }
    }
}