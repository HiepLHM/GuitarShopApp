package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edtNewPassword, edtConfirmNewPassword;
    private Button btnConfirmChangePass, btnCancel;
    private TextView tvErrorChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initConfirmChangePass();

        btnConfirmChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHomeActivity = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(gotoHomeActivity);
                finish();
            }
        });

    }

    private void changePassword() {
        List<User> userList = DataLocal.getInstance(this).localDAO().getListUserLocal();
        int idUser = userList.get(0).getIdUser();
        String newPass = edtNewPassword.getText().toString().trim();
        String confirmNewPass = edtConfirmNewPassword.getText().toString().trim();
        if (!newPass.equals(confirmNewPass)) {
            tvErrorChangePass.setText("Password incorrect");
            tvErrorChangePass.setVisibility(View.VISIBLE);
            return;
        }
        DataClient dataClientChangePass = APIUtils.getData();
        Call<String> callChangePass = dataClientChangePass.callChangePassword(idUser, newPass);
        callChangePass.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if (mess.equals("success")) {
                    Toast.makeText(ChangePasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    Intent gotoHome = new Intent(ChangePasswordActivity.this, UserProfile.class);
                    startActivity(gotoHome);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(ChangePasswordActivity.this, "Call api changepass failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initConfirmChangePass() {
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        btnConfirmChangePass = findViewById(R.id.btnConfirmChangePass);
        btnCancel = findViewById(R.id.btnCancel);
        tvErrorChangePass = findViewById(R.id.tvErrorChangePass);
    }
}