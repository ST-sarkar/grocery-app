package com.example.annusgroceries;

import static android.Manifest.permission.READ_MEDIA_IMAGES;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.Manifest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.annusgroceries.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    EditText name,phone,address,email;
    Button submit,logout;
    CircleImageView circleImageView;
    ActivityResultLauncher<Intent> activityResultLauncherForPickImage;
    private Bitmap selectedimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ResultLauncherForPickImage();

        name=findViewById(R.id.user_name);
        phone=findViewById(R.id.user_phone);
        address=findViewById(R.id.user_address);
        email=findViewById(R.id.user_email);
        submit=findViewById(R.id.submit);
        logout=findViewById(R.id.btn_logout);
        circleImageView=findViewById(R.id.circleImageView);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(SettingsActivity.this,Manifest.permission.READ_MEDIA_IMAGES)==PackageManager.PERMISSION_DENIED)
                {
                    ActivityCompat.requestPermissions(SettingsActivity.this,new String[]{Manifest.permission.READ_MEDIA_IMAGES},1);
                }else
                {
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncherForPickImage.launch(intent);

                }
            }
        });



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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncherForPickImage.launch(intent);
        }
    }

    private void ResultLauncherForPickImage() {
        activityResultLauncherForPickImage=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                int resultCode=o.getResultCode();
                Intent data=o.getData();
                if(resultCode==RESULT_OK && data!=null)
                {
                    try {
                        selectedimage= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                        selectedimage=makeSmallScaleImage(selectedimage,300);
                        selectedimage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
                        byte[] image=outputStream.toByteArray();

                        circleImageView.setImageBitmap(selectedimage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }

    private Bitmap makeSmallScaleImage(Bitmap image, int i) {
        int height=image.getHeight();
        int width=image.getWidth();

        float ratio=(float) width/(float) height;
        if(ratio>1)
        {
            width=i;
            height=(int)(width/ratio);
        }else
        {
            height=i;
            width=(int)(height*ratio);
        }

        return Bitmap.createScaledBitmap(image,width,height,true);
    }
}