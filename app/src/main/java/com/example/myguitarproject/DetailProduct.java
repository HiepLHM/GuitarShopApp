package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity {
    private ImageView imgDetailProduct;
    private TextView tvNameDetailProduct, tvPriceDetailProduct, tvDesProduct;
    private Button btnAddToCart, btnBackToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initDetailProduct();
        Product product = (Product) getIntent().getExtras().get("object_product");
        Glide.with(this).load(product.getImageProduct()).into(imgDetailProduct);
        tvNameDetailProduct.setText(product.getNameProduct());
        tvPriceDetailProduct.setText(product.getPriceProduct());
        tvDesProduct.setText(product.getDescriptionProduct());
        tvDesProduct.setMovementMethod(new ScrollingMovementMethod());

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> userList = DataLocal.getInstance(DetailProduct.this).localDAO().getListUserLocal();
                int idProduct = product.getIdProduct();
                String nameProduct = tvNameDetailProduct.getText().toString().trim();
                String priceProdcut = tvPriceDetailProduct.getText().toString().trim();
                String imageProduct = product.getImageProduct();
                int idUser = userList.get(0).getIdUser();
                DataClient dataClient = APIUtils.getData();
                Call<String> callInsertCart = dataClient.callInsertCart(idProduct, nameProduct, priceProdcut,imageProduct, idUser);
                callInsertCart.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String messInsert = response.body();
                        if(messInsert.equals("success")){
                            Toast.makeText(DetailProduct.this, "insert success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProduct.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initDetailProduct(){
        imgDetailProduct = findViewById(R.id.imgDetailProduct);
        tvNameDetailProduct = findViewById(R.id.tvNameDetailProduct);
        tvPriceDetailProduct = findViewById(R.id.tvPriceDetailProduct);
        tvDesProduct = findViewById(R.id.tvDesProduct);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBackToMain = findViewById(R.id.btnBackToMain);
    }
}