package com.example.annusgroceries.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Model.uploadPost;
import com.example.annusgroceries.R;
import com.example.annusgroceries.adapter.DeleteAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteItemActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<uploadPost> allitemlist;
    List<String> userList;
    DeleteAdapter deleteAdapter;
    Spinner spinnertype;
    String type;
    TextView note;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        recyclerView = findViewById(R.id.del_recview);
        spinnertype=findViewById(R.id.spinner_type);

        show=findViewById(R.id.btn_show);
        note=findViewById(R.id.textView3);

        String items[]=new String[]{"Fruits","Fishes","Meets","Veggies","Vaverage","Eggs","Cookies","Juice"};
        spinnertype.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView< ? > parent, View view, int position, long id) {

                type=spinnertype.getSelectedItem().toString();
            }


            @Override
            public void onNothingSelected(AdapterView< ? > parent) {
                type="Fruits";
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(view.VISIBLE);
                note.setVisibility(view.VISIBLE);
                String targetType = type;

                allitemlist = new ArrayList<>();
                userList=new ArrayList<>();
                deleteAdapter = new DeleteAdapter(DeleteItemActivity.this, allitemlist,userList);
                recyclerView.setLayoutManager(new LinearLayoutManager(DeleteItemActivity.this));
                recyclerView.setAdapter(deleteAdapter);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference itemsRef = database.getReference().child("AllItems");

                itemsRef.orderByChild("type").equalTo(targetType).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                uploadPost model = snapshot.getValue(uploadPost.class);
                                if (model != null) {
                                    allitemlist.add(model);
                                    userList.add(snapshot.getKey());
                                }
                            }
                            deleteAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(DeleteItemActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DeleteItemActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}