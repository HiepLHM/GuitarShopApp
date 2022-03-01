package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.productadmin.ListProductAdminAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView rcvListProductAdmin;
    private Button btnAddProductActivity, btnStatistic, btnBackToHome;
    private ListProductAdminAdapter listProductAdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initAdmin();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListProductAdmin.setLayoutManager(linearLayoutManager);

        listProductAdminAdapter = new ListProductAdminAdapter(this);

        getListProduct();

        btnAddProductActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddNewProduct.class);
                startActivity(intent);
                finish();
            }
        });
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getListProduct() {
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> call = dataClient.callProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> products = (ArrayList<Product>) response.body();
                listProductAdminAdapter.setData(products);
                rcvListProductAdmin.setAdapter(listProductAdminAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void initAdmin() {
        rcvListProductAdmin = findViewById(R.id.rcvListProductAdmin);
        btnAddProductActivity = findViewById(R.id.btnAddProductActivity);
        btnStatistic = findViewById(R.id.btnStatistic);
        btnBackToHome = findViewById(R.id.btnBackToHome);
    }
}