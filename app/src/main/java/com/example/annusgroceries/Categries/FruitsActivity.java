package com.example.annusgroceries.Categries;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Model.uploadPost;
import com.example.annusgroceries.R;
import com.example.annusgroceries.adapter.AllCategoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FruitsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<uploadPost> datalist;
    List<String> keyList;
    AllCategoryAdapter allCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        recyclerView = findViewById(R.id.catRecycler);
        datalist = new ArrayList<>();
        keyList = new ArrayList<>();

        allCategoryAdapter = new AllCategoryAdapter(FruitsActivity.this, datalist,keyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(allCategoryAdapter);

        String targetType = getIntent().getStringExtra("type");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference itemsRef = database.getReference().child("AllItems");

        itemsRef.orderByChild("type").equalTo(targetType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        uploadPost model = snapshot.getValue(uploadPost.class);
                        if (model != null) {
                            datalist.add(model);
                            keyList.add(snapshot.getKey());
                        }
                    }
                    allCategoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FruitsActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FruitsActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}