package com.example.annusgroceries.Admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.annusgroceries.Model.uploadPost;
import com.example.annusgroceries.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminAddActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    private Button pickImageButton;
    private Button mButtonUpload;
    private Uri img_url;
    private String type;
    private EditText name;
    private Spinner admintype;
    private EditText price;
    private EditText description;
    private  EditText unit;
    private  EditText qty;

    TextView img_name;
    uploadPost u;
    private String imageUrl;

    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    StorageReference storageReference=firebaseStorage.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
   // FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

   // DocumentReference documentReference;
   private ActivityResultLauncher<Intent> pickImageLauncher;
    private Uri selectedImageUri;


    FirebaseAuth auth;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        pickImageButton=findViewById(R.id.btn);
        mButtonUpload=findViewById(R.id.btn2);
        name=findViewById(R.id.admin_name);
        admintype=findViewById(R.id.admin_type);
        price=findViewById(R.id.admin_price);
        description=findViewById(R.id.admin_des);
        unit=findViewById(R.id.admin_unit);
        qty=findViewById(R.id.admin_qty);
        img_name=findViewById(R.id.img_name);



        String items[]=new String[]{"Fruits","Fishes","Meets","Veggies","Vaverage","Eggs","Cookies","Juice"};
        admintype.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));
        admintype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView< ? > parent, View view, int position, long id) {

                type=admintype.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView< ? > parent) {
                type="fruit";
            }
        });

        // Button to pick an image

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleActivityResult);

        pickImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();


            }
        });
    }


    private void handleActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                img_name.setText(selectedImageUri.toString());
                img_name.setVisibility(View.VISIBLE);

            }
        }
    }

    private void uploadImage() {
        // Create a unique filename for the image
        String imageFileName = FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + System.currentTimeMillis();

        // Create a reference to the image file in Firebase Storage
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageFileName);

        // Upload the image to Firebase Storage
        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully

                    // Get the download URL of the uploaded image
                    imageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                // Store the download URL in variable
                                imageUrl = uri.toString();
                               // Toast.makeText(this, "Item picked", Toast.LENGTH_SHORT).show();
                                uploadData();

                            })
                            .addOnFailureListener(exception -> {
                                // Handle the error
                                Toast.makeText(this, "error :"+exception, Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(exception -> {
                    // Handle the error
                    Toast.makeText(this, "error :"+exception, Toast.LENGTH_SHORT).show();
                });
    }


    private void uploadData() {
        String nameInput = name.getText().toString().trim();
        String descriptionInput = description.getText().toString().trim();
        String typeInput = type;
        String unitInput = unit.getText().toString().trim();
        String qtyInput = qty.getText().toString().trim();
        String priceInput = price.getText().toString().trim();

// Check if name is not empty
        if (!nameInput.isEmpty() && !descriptionInput.isEmpty() && !unitInput.isEmpty() && !qtyInput.isEmpty() && !priceInput.isEmpty()) {
            if(!imageUrl.isEmpty()) {

                u = new uploadPost(nameInput, descriptionInput, priceInput, unitInput, qtyInput, imageUrl, typeInput);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AllItems");
                //String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                String uploadId = databaseReference.push().getKey();
                databaseReference.child(uploadId).setValue(u);

                name.setText("");
                description.setText("");
                unit.setText("");
                qty.setText("");
                price.setText("");
                img_name.setText("");
                img_name.setVisibility(View.GONE);

                Toast.makeText(this, "product successfully uploaded", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AdminAddActivity.this, "image fetching error", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(AdminAddActivity.this, "All fields are must be required", Toast.LENGTH_SHORT).show();
        }
    }



}