package com.example.myguitarproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.cart.Cart;
import com.example.myguitarproject.fragment.CartFragment;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.orderdetail.OrderAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private EditText edtFullname, edtPhoneNumber, edtAddress;
    private RecyclerView rcvPayment;
    private static TextView tvPricePayment;
    private Button btnPayment;
    private static List<Cart> mListCart;
    private List<Product> mListProduct;
    private Cart mCart;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initPayment();
        mListProduct = new ArrayList<>();
        mListCart = new ArrayList<>();
        orderAdapter = new OrderAdapter(PaymentActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPayment.setLayoutManager(linearLayoutManager);

        mCart = (Cart) getIntent().getExtras().get("mCart");
        if (mCart != null) {
            mListCart.add(mCart);
            orderAdapter.setData(mListCart);
            rcvPayment.setAdapter(orderAdapter);
            sumPriceOrder();
        } else {
            mListCart = (List<Cart>) getIntent().getExtras().get("mListCart");
            orderAdapter.setData(mListCart);
            rcvPayment.setAdapter(orderAdapter);
            sumPriceOrder();
        }

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerPaymentOrder();
            }
        });

    }

    private void handlerPaymentOrder() {
        String customerName = edtFullname.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        for (int i = 0; i < mListCart.size(); i++) {
            int idProduct = mListCart.get(i).getIdProduct();
            String nameProduct = mListCart.get(i).getNameProduct();
            String sumPrice = mListCart.get(i).getPriceProduct();
            int quantily = mListCart.get(i).getQuanlityProduct();
            Log.d("quantily", quantily + "");
            String imageProduct = mListCart.get(i).getImageProduct();
            int id_user = mListCart.get(i).getIdUser();
            int id_cart = mListCart.get(i).getIdCart();

            //get list product with idproduct.
            DataClient dataClientGetProduct = APIUtils.getData();
            Call<List<Product>> callProduct = dataClientGetProduct.callProductWithId(idProduct);
            callProduct.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body();
                    mListProduct.addAll(products);
                    int quantilySoldCurent = mListProduct.get(0).getQuantilySold();
                    int quantilySold = quantilySoldCurent + quantily;
                    DataClient dataClientUpdateQuantilySold = APIUtils.getData();
                    Call<String> callUpdateQuantilySold = dataClientUpdateQuantilySold.callUpdateQuantilySold(idProduct, quantilySold);
                    callUpdateQuantilySold.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String mess = response.body();
                            if (mess.equals("success")) {
                                Toast.makeText(PaymentActivity.this, "update quantily success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }


                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });

            //insert item product to detail_order.
            DataClient dataClientInsertOrder = APIUtils.getData();
            Call<String> callInsertOrder = dataClientInsertOrder.callInsertOrder(idProduct, nameProduct, sumPrice, quantily, imageProduct,
                    customerName, phoneNumber, address, id_user);
            callInsertOrder.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(PaymentActivity.this, "insert order success", Toast.LENGTH_SHORT).show();


                    //remove item product in cart product.
                    DataClient dataClientDeleteCartItem = APIUtils.getData();
                    Call<String> callDeleteCartItem = dataClientDeleteCartItem.callDeleteCartItem(id_cart);
                    callDeleteCartItem.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String mess = response.body();
                            if (mess.equals("success")) {
                                Toast.makeText(PaymentActivity.this, "remove cart item success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    alertSuccess();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

    }

    private void alertSuccess() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Payment Success!")
                .setMessage("Bạn đã thanh toán thành công!!!")
                .setPositiveButton("Đơn hàng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderStatus();
                    }
                })
                .setNegativeButton("Trang chủ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoMain();
                    }
                })
                .create()
                .show();
    }

    private void gotoMain() {
        Intent gotoMain = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(gotoMain);
        finish();
    }

    private void orderStatus() {
        Intent gotoOrderStatus = new Intent(this, OrderStatusActivity.class);
        startActivity(gotoOrderStatus);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void sumPriceOrder() {
        long price_order = 0;
        for (int i = 0; i < mListCart.size(); i++) {
            price_order += Long.parseLong(mListCart.get(i).getPriceProduct());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvPricePayment.setText(decimalFormat.format(price_order) + "vnd");
    }

    private void initPayment() {
        edtFullname = findViewById(R.id.edtFullname);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtAddress = findViewById(R.id.edtAddress);
        rcvPayment = findViewById(R.id.rcvPayment);
        tvPricePayment = findViewById(R.id.tvPricePayment);
        btnPayment = findViewById(R.id.btnPayment);
    }
}