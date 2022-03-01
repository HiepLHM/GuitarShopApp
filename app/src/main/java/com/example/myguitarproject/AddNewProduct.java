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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myguitarproject.RealPathUtils.RealPathUtil;
import com.example.myguitarproject.productadmin.CategoryAdmin;
import com.example.myguitarproject.productadmin.CategoryDropDownAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewProduct extends AppCompatActivity {
    private static final int MY_REQUEST_CODE_ADMIN = 10;
    private EditText edtNameProduct, edtPriceProduct, edtDescriptionProduct, edtdiscount;
    private Button btnSelectImg, btnAddProduct, btnBackToAdmin;
    private Spinner spinIdCategory;
    private CategoryDropDownAdapter categoryDropDownAdapter;

    static int idCategory;
    private Uri mUri;
    private ArrayList<CategoryAdmin> list;
    private ImageView imgNewProduct1;

    ActivityResultLauncher<Intent> mActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgNewProduct1.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        initAddProduct();
        list = new ArrayList<>();

        getCategorySpinner();

        btnSelectImg.setOnClickListener(v -> checkPermissionToOpenGalleryUpLoadImage());

        btnAddProduct.setOnClickListener(v -> addNewProduct());

        spinIdCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryDropDownAdapter.getItem(position).getIdCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnBackToAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(AddNewProduct.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void addNewProduct() {
        String realPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();
        String[] listfile = file_path.split("\\.");
        file_path = listfile[0] + System.currentTimeMillis() + "." + listfile[1];

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/formdata"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.callImageNewProduct(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                String imgSource = APIUtils.BASE_URL + "image/guitar/" + mess;
                String nameProduct = edtNameProduct.getText().toString().trim();
                String priceProduct = edtPriceProduct.getText().toString().trim();
                String description = edtDescriptionProduct.getText().toString().trim();
                String discount = edtdiscount.getText().toString().trim();
                DataClient dataClientAddProduct = APIUtils.getData();
                Call<String> callAddProduct = dataClientAddProduct.callInsertProduct(nameProduct, priceProduct, imgSource, description, discount, idCategory);
                callAddProduct.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String mess1 = response.body();
                        if (mess1.equals("success")) {
                            Toast.makeText(AddNewProduct.this, " add new product success", Toast.LENGTH_SHORT).show();
                        }
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

    private void checkPermissionToOpenGalleryUpLoadImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE_ADMIN);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE_ADMIN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityForResult.launch(Intent.createChooser(intent, "select image"));
    }

    private void getCategorySpinner() {
        DataClient dataClient = APIUtils.getData();
        Call<List<CategoryAdmin>> call = dataClient.callCategoryAdmin();
        call.enqueue(new Callback<List<CategoryAdmin>>() {
            @Override
            public void onResponse(Call<List<CategoryAdmin>> call, Response<List<CategoryAdmin>> response) {
                ArrayList<CategoryAdmin> categoryAdmins = (ArrayList<CategoryAdmin>) response.body();
                for (int i = 0; i < categoryAdmins.size(); i++) {
                    list.add(new CategoryAdmin(categoryAdmins.get(i).getIdCategory(), categoryAdmins.get(i).getNameCategory()));
                }
                categoryDropDownAdapter = new CategoryDropDownAdapter(AddNewProduct.this, list);
                spinIdCategory.setAdapter(categoryDropDownAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryAdmin>> call, Throwable t) {

            }
        });
    }

    private void initAddProduct() {
        spinIdCategory = findViewById(R.id.spinIdCategory);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        edtNameProduct = findViewById(R.id.edtNameProduct);
        edtPriceProduct = findViewById(R.id.edtPriceProduct);
        edtDescriptionProduct = findViewById(R.id.edtDescriptionProduct);
        edtdiscount = findViewById(R.id.edtdiscount);
        btnSelectImg = findViewById(R.id.btnSelectImg);
        imgNewProduct1 = findViewById(R.id.imgNewProduct1);
        btnBackToAdmin = findViewById(R.id.btnBackToAdmin);
    }
}