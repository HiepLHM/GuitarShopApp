package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneNumber extends AppCompatActivity {
    private EditText edtNewPhoneNumber;
    private TextView tvErrorChangePhoneNumber;
    private Button btnConfirmChangePhoneNumber, btnCancelPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);
        initChangePhoneNumber();

        btnConfirmChangePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoneNumber();
            }
        });

        btnCancelPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoUserProfile = new Intent(ChangePhoneNumber.this, UserProfile.class);
                startActivity(gotoUserProfile);
                finish();
            }
        });
    }

    private void changePhoneNumber() {
        String phoneNumber = edtNewPhoneNumber.getText().toString().trim();
        List<User> userList = DataLocal.getInstance(this).localDAO().getListUserLocal();
        int id = userList.get(0).getIdUser();
        if (TextUtils.isEmpty(phoneNumber)) {
            tvErrorChangePhoneNumber.setText("please insert phone number!");
            tvErrorChangePhoneNumber.setVisibility(View.VISIBLE);
            return;
        }
        DataClient dataClientChangePhoneNumber = APIUtils.getData();
        Call<String> callChangePhoneNumber = dataClientChangePhoneNumber.callChangePhoneNumber(id, phoneNumber);
        callChangePhoneNumber.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if (mess.equals("success")) {
                    Toast.makeText(ChangePhoneNumber.this, "Change Phone Number Success", Toast.LENGTH_SHORT).show();
                    Intent gotoUserProfile = new Intent(ChangePhoneNumber.this, UserProfile.class);
                    startActivity(gotoUserProfile);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void initChangePhoneNumber() {
        edtNewPhoneNumber = findViewById(R.id.edtNewPhoneNumber);
        tvErrorChangePhoneNumber = findViewById(R.id.tvErrorChangePhoneNumber);
        btnConfirmChangePhoneNumber = findViewById(R.id.btnConfirmChangePhoneNumber);
        btnCancelPhoneNumber = findViewById(R.id.btnCancelPhoneNumber);
    }
}