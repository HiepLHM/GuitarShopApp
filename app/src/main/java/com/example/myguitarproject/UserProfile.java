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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.RealPathUtils.RealPathUtil;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    private CircleImageView imgChangeAvatar;
    private Button btnChangeAvatar, btnChangePassword, btnChangePhoneNumber;
    private TextView tvChangeUsername, tvChangePassword, tvChangeEmail, tvChangePhoneNumber;
    private List<User> mListUser;
    private Uri mUri;
    private String realPath;

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if(data==null){
                        return;
                    }
                    Uri uri = data.getData();
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgChangeAvatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initChangeProfile();
        mListUser = new ArrayList<>();
        getUserProfile();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        btnChangePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoneNumber();
            }
        });

        imgChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar();
            }
        });

        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmChangeAvatar();
            }
        });
    }

    private void confirmChangeAvatar() {
        realPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(realPath);
        String filePath = file.getAbsolutePath();
        String[] listFile = filePath.split("\\.");
        filePath = listFile[0] + System.currentTimeMillis() +"." + listFile[1];
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/formdata"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filePath, requestBody);

        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.callAvatarUser(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                List<User> users = DataLocal.getInstance(UserProfile.this).localDAO().getListUserLocal();
                int id = users.get(0).getIdUser();
                DataClient dataClientChangeAvatar = APIUtils.getData();
                Call<String> callChangeAvatar = dataClientChangeAvatar.callChangeAvatar(id, APIUtils.BASE_URL + "image/avatar/"+ mess);
                callChangeAvatar.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        getUserProfile();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void changeAvatar() {
        checkVersionToOpenGallery();
    }

    private void checkVersionToOpenGallery() {
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            openGalerry();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGalerry();
        } else{
            String[] permistion = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permistion, REQUEST_CODE);
        }
    }

    private void openGalerry() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "select picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGalerry();
            }
        }
    }

    private void getUserProfile(){
        List<User> user = DataLocal.getInstance(this).localDAO().getListUserLocal();
        int id = user.get(0).getIdUser();
        DataClient dataClientGetUser = APIUtils.getData();
        Call<List<User>> callGetUser = dataClientGetUser.callUserProfile(id);
        callGetUser.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> users = (ArrayList<User>) response.body();
                mListUser.addAll(users);
                Glide.with(UserProfile.this).load(mListUser.get(0).getAvatar()).into(imgChangeAvatar);
                tvChangeUsername.setText(mListUser.get(0).getUsername());
                tvChangePassword.setText(mListUser.get(0).getPassword());
                tvChangeEmail.setText(mListUser.get(0).getEmail());
                tvChangePhoneNumber.setText(mListUser.get(0).getPhone_number());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserProfile();
    }

    private void changePhoneNumber() {
        Intent gotoChangePhoneNumber = new Intent(UserProfile.this, ChangePhoneNumber.class);
        startActivity(gotoChangePhoneNumber);
        finish();
    }

    private void changePassword() {
        Intent gotoConfirmActivity = new Intent(UserProfile.this, ConfirmActivity.class);
        startActivity(gotoConfirmActivity);
    }

    private void initChangeProfile() {
        imgChangeAvatar = findViewById(R.id.imgChangeAvatar);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePhoneNumber = findViewById(R.id.btnChangePhoneNumber);
        tvChangeUsername = findViewById(R.id.tvChangeUsername);
        tvChangePassword = findViewById(R.id.tvChangePassword);
        tvChangeEmail = findViewById(R.id.tvChangeEmail);
        tvChangePhoneNumber = findViewById(R.id.tvChangePhoneNumber);
    }
}