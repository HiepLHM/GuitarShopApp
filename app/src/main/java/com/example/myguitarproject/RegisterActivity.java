package com.example.myguitarproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguitarproject.RealPathUtils.RealPathUtil;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private CircleImageView imgAvatar;
    private TextView tvErrorRegister;
    private EditText edtRegisterUsername, edtRegisterPassword, edtRegisterEmail, edtRegisterPhoneNumber;
    private Button btnRegisterUser;
    private Uri mUri;
    private String realPath;

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if(data == null){
                        return;
                    }
                    Uri uri = data.getData();
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgAvatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initRegister();

        //handler set Image avatar user.
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerImageAvatar();
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerRegisterUser();
            }
        });
    }

    private void handlerRegisterUser() {
        realPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();
        String[] listfile = file_path.split("\\.");
        file_path = listfile[0] + System.currentTimeMillis() + "." + listfile[1];

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/formdata"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        DataClient dataClient = APIUtils.getData();
        Call<String> callAvatarUser = dataClient.callAvatarUser(body);
        callAvatarUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                String username = edtRegisterUsername.getText().toString().trim();
                String password = edtRegisterPassword.getText().toString().trim();
                String email = edtRegisterEmail.getText().toString().trim();
                String phone_number = edtRegisterPhoneNumber.getText().toString().trim();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone_number)){
                    tvErrorRegister.setText("Vui lòng điền đầy đủ thông tin!!!");
                    tvErrorRegister.setVisibility(View.VISIBLE);
                    return;
                }
                DataClient dataClientRegister = APIUtils.getData();
                Call<String> callRegister = dataClientRegister.callRegisterUser(username, password, email, APIUtils.BASE_URL+ "image/avatar/" + mess, phone_number);
                callRegister.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String messRegister = response.body();
                        if(messRegister.equals("failed")){
                            tvErrorRegister.setText("Tài Khoản đã tồn tại!");
                            tvErrorRegister.setVisibility(View.VISIBLE);
                            return;
                        }
                        if(messRegister.equals("success")){
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Lỗi ở Register", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi insert avatar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //check android version to open galley image.
    private void checkVersionToOpenGallery(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else{
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "select picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void handlerImageAvatar() {
        checkVersionToOpenGallery();
    }

    private void initRegister(){
        imgAvatar               = findViewById(R.id.imgAvatar);
        edtRegisterUsername     = findViewById(R.id.edtRegisterUsername);
        edtRegisterPassword     = findViewById(R.id.edtRegisterPassword);
        edtRegisterEmail        = findViewById(R.id.edtRegisterEmail);
        edtRegisterPhoneNumber  = findViewById(R.id.edtRegisterPhoneNumber);
        btnRegisterUser         = findViewById(R.id.btnRegisterUser);
        tvErrorRegister         = findViewById(R.id.tvErrorRegister);
    }
}