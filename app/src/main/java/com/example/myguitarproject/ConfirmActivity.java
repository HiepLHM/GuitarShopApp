package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ConfirmActivity extends AppCompatActivity {
    private EditText edtPasswordOld;
    private Button btnConfirm, btnCancelConfirm;
    private TextView tvErrorConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        initConfirm();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPass();
            }
        });

        btnCancelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHomeActivity = new Intent(ConfirmActivity.this, UserProfile.class);
                startActivity(gotoHomeActivity);
                finish();
            }
        });
    }

    private void confirmPass() {
        String password = edtPasswordOld.getText().toString().trim();
        List<User> userList = DataLocal.getInstance(this).localDAO().getListUserLocal();
        int id = userList.get(0).getIdUser();
        if(TextUtils.isEmpty(password)){
            tvErrorConfirm.setText("Insert Password");
            tvErrorConfirm.setVisibility(View.VISIBLE);
            return;
        }
        DataClient dataClientCallConfirmPass = APIUtils.getData();
        Call<String> callConfirmPass = dataClientCallConfirmPass.callConfirmPass(id, password);
        callConfirmPass.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if(mess.equals("correct")){
                    Intent gotoChangePassActivity = new Intent(ConfirmActivity.this, ChangePasswordActivity.class);
                    startActivity(gotoChangePassActivity);
                }
                if(mess.equals("failed")){
                    Toast.makeText(ConfirmActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                    tvErrorConfirm.setText("Password incorrect");
                    tvErrorConfirm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ConfirmActivity.this, "Call confirm failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initConfirm(){
        edtPasswordOld = findViewById(R.id.edtPasswordOld);
        btnConfirm = findViewById(R.id.btnConfirm);
        tvErrorConfirm = findViewById(R.id.tvErrorConfirm);
        btnCancelConfirm = findViewById(R.id.btnCancelConfirm);
    }
}