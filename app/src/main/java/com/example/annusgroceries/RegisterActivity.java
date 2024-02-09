package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.annusgroceries.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button signUp;
    EditText name,email,password,address,phone;
    TextView signIn;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        address=findViewById(R.id.address_reg);
        phone=findViewById(R.id.phone_reg);
        signIn = findViewById(R.id.sign_in);



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();

            }
        });
    }

    private void createUser() {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPhone = phone.getText().toString();
        String userPassword = password.getText().toString();
        String useraddress = address.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(useraddress)){
            Toast.makeText(this, "Address is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPhone.length() <10){
            Toast.makeText(this, "phone number length must be 10 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6){
            Toast.makeText(this, "Password Length must be greater then 6 letter", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create User
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            User userModel = new User(userEmail,userName,userPhone,useraddress);
                            String id = task.getResult().getUser().getUid();
                            //databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(id);
                            database.getReference().child("Users").child(id).setValue(userModel);
                            // databaseReference.setValue(userModel);

                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}