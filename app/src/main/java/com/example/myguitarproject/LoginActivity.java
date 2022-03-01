package com.example.myguitarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtPassword, edtUsername;
    private Button btnLogin, btnRegister;
    private TextView tvErrorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLogin();


        //handler register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRegister();
            }
        });

        //check user logout or login.
        List<User> users = DataLocal.getInstance(this).localDAO().getListUserLocal();

        if (users.size() != 0) {
            Intent gotoMainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(gotoMainActivity);
            finish();
        } else {
            //handler login button
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlerLogin();
                }
            });
        }


    }

    private void handlerLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            tvErrorLogin.setText("Vui Lòng nhập đủ thông tin!");
            tvErrorLogin.setVisibility(View.VISIBLE);
            return;
        }

        DataClient dataClient = APIUtils.getData();
        Call<List<User>> callLogin = dataClient.callLogin(username, password);
        callLogin.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> users = (ArrayList<User>) response.body();
                if (users.size() > 0) {
                    User user = users.get(0);
                    if (!checkUserExists(user)) {
                        DataLocal.getInstance(LoginActivity.this).localDAO().insertUserLocal(user);
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    tvErrorLogin.setText("Tài khoản không tồn tại!");
                    tvErrorLogin.setVisibility(View.GONE);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openActivityRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean checkUserExists(User user) {
        List<User> list = DataLocal.getInstance(LoginActivity.this).localDAO().checkUserExists(user.getUsername());
        return list != null && !list.isEmpty();
    }

    private void initLogin() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvErrorLogin = findViewById(R.id.tvErrorLogin);
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}