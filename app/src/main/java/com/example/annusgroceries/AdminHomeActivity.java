package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.annusgroceries.Admin.AdminAddActivity;
import com.example.annusgroceries.Admin.DeleteItemActivity;
import com.example.annusgroceries.Admin.EditPriceActivity;
import com.example.annusgroceries.Admin.ViewOrderActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {
    CardView cd1,cd2,cd3,cd4;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        cd1=findViewById(R.id.crd1);
        logout=findViewById(R.id.btn_logout);
        cd3=findViewById(R.id.crd3);
        cd4=findViewById(R.id.crd4);

        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, AdminAddActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminHomeActivity.this,LoginActivity.class));
                finish();
            }
        });

        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, DeleteItemActivity.class);
                startActivity(intent);
            }
        });
        cd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, ViewOrderActivity.class);
                startActivity(intent);
            }
        });

    }
}