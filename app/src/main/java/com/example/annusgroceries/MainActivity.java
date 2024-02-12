package com.example.annusgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annusgroceries.Model.Category;
import com.example.annusgroceries.Model.DiscountedProducts;
import com.example.annusgroceries.adapter.CategoryAdapter;
import com.example.annusgroceries.adapter.DiscountedProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    ImageView imgsettings,seecart,seeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);

        imgsettings=findViewById(R.id.imageViewSettings);
        seecart=findViewById(R.id.imageViewCart);
        seeOrder=findViewById(R.id.imageViewOrder);


        imgsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });

        seecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SplashInsideActivity.class);
                intent.putExtra("from","cart");
                startActivity(intent);
            }
        });

        seeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SplashInsideActivity.class);
                intent.putExtra("from","order");
                startActivity(intent);
            }
        });

        // adding data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, R.drawable.discountberry));
        discountedProductsList.add(new DiscountedProducts(2, R.drawable.discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, R.drawable.discountmeat));

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_fruits));
        categoryList.add(new Category(2, R.drawable.ic_fish));
        categoryList.add(new Category(3, R.drawable.ic_meat));
        categoryList.add(new Category(4, R.drawable.ic_veggies));
        categoryList.add(new Category(5, R.drawable.ic_drink));
        categoryList.add(new Category(6, R.drawable.ic_egg));
        categoryList.add(new Category(7, R.drawable.ic_cookies));
        categoryList.add(new Category(8, R.drawable.ic_juce));


        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);

    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

}