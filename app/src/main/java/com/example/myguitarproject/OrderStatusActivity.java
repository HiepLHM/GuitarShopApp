package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.orderstatus.OrderStatus;
import com.example.myguitarproject.orderstatus.OrderStatusAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusActivity extends AppCompatActivity {
    private RecyclerView rcvOrderStatus;
    private Button btnBack;
    private List<OrderStatus> mListOrderStatus;
    private OrderStatusAdapter orderStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        init();

        mListOrderStatus = new ArrayList<>();
        orderStatusAdapter = new OrderStatusAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvOrderStatus.setLayoutManager(linearLayoutManager);

        getData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(OrderStatusActivity.this, MainActivity.class);
                startActivity(gotoHome);
                finish();
            }
        });
    }

    private void getData() {
        List<User> users = DataLocal.getInstance(this).localDAO().getListUserLocal();
        int idUser = users.get(0).getIdUser();

        DataClient dataClient = APIUtils.getData();
        Call<List<OrderStatus>> call = dataClient.callOrderStatus(idUser);
        call.enqueue(new Callback<List<OrderStatus>>() {
            @Override
            public void onResponse(Call<List<OrderStatus>> call, Response<List<OrderStatus>> response) {
                ArrayList<OrderStatus> orderStatuses = (ArrayList<OrderStatus>) response.body();
                mListOrderStatus.addAll(orderStatuses);
                orderStatusAdapter.setData(mListOrderStatus);
                rcvOrderStatus.setAdapter(orderStatusAdapter);
            }

            @Override
            public void onFailure(Call<List<OrderStatus>> call, Throwable t) {

            }
        });
    }

    private void init() {
        rcvOrderStatus = findViewById(R.id.rcvOrderStatus);
        btnBack = findViewById(R.id.btnBackToHome);
    }
}