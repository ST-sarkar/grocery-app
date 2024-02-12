package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annusgroceries.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {
    EditText name,phone,address,email;
    Button submit,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name=findViewById(R.id.user_name);
        phone=findViewById(R.id.user_phone);
        address=findViewById(R.id.user_address);
        email=findViewById(R.id.user_email);
        submit=findViewById(R.id.submit);
        logout=findViewById(R.id.btn_logout);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm=name.getText().toString();
                String ph=phone.getText().toString();
                String addr=address.getText().toString();
                String em=email.getText().toString();

                if(!em.isEmpty() && !nm.isEmpty() && !ph.isEmpty() && !addr.isEmpty()){
                    User user=new User(em,nm,ph,addr);

                    reference.child(FirebaseAuth.getInstance().getUid()).setValue(user);

                    name.setText("");
                    phone.setText("");
                    email.setText("");
                    address.setText("");

                    Toast.makeText(SettingsActivity.this, "Information successfully Updated", Toast.LENGTH_SHORT).show();

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                // After logging out, redirect the user to your login activity
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}