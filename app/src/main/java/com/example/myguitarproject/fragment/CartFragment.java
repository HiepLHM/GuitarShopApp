package com.example.myguitarproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.PaymentActivity;
import com.example.myguitarproject.R;
import com.example.myguitarproject.cart.Cart;
import com.example.myguitarproject.cart.CartAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    private RecyclerView rcvCart;
    private static TextView tvSumPrice;
    private Button btnPayment;
    private static List<Cart> mListCart;
    private CartAdapter cartAdapter;
    private View view;

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // receiver data.
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initCart();
        mListCart = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvCart.setLayoutManager(linearLayoutManager);


        callListCart();
        sumPrice();
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPayment = new Intent(getContext(), PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mListCart", (Serializable) mListCart);
                gotoPayment.putExtras(bundle);
                mActivityResultLauncher.launch(gotoPayment);
            }
        });


        return view;
    }

    private void callListCart() {
        List<User> users = DataLocal.getInstance(getContext()).localDAO().getListUserLocal();
        DataClient dataClient = APIUtils.getData();
        Call<List<Cart>> callListCart = dataClient.callListCart(users.get(0).getIdUser());
        callListCart.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                ArrayList<Cart> carts = (ArrayList<Cart>) response.body();
                mListCart.addAll(carts);
                cartAdapter.setData(mListCart);
                rcvCart.setAdapter(cartAdapter);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sumPrice();
    }


    public static void sumPrice() {
        long sumPrice = 0;
        for (int i = 0; i < mListCart.size(); i++) {
            sumPrice += Long.parseLong(mListCart.get(i).getPriceProduct());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvSumPrice.setText(decimalFormat.format(sumPrice) + " vnd");
    }

    private void initCart() {
        rcvCart = view.findViewById(R.id.rcvCart);
        btnPayment = view.findViewById(R.id.btnPayment);
        tvSumPrice = view.findViewById(R.id.tvSumPrice);
    }
}