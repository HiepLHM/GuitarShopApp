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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.RealPathUtils.RealPathUtil;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.productadmin.CategoryAdmin;
import com.example.myguitarproject.productadmin.CategoryDropDownAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 20;
    private EditText edtEditDiscount, edtEditDescriptionProduct, edtEditPriceProduct, edtEditNameProduct;
    private Button btnEditBackToAdmin, btnEditProduct, btnEditSelectImg;
    private ImageView imgEditProduct;
    private Spinner spinEditIdCategory;
    private Product product;
    private Uri mUri;
    private ArrayList<CategoryAdmin> list;
    private CategoryDropDownAdapter categoryDropDownAdapter;
    private int idCategory;

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
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
                        imgEditProduct.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        initEditProduct();

        list = new ArrayList<>();

        product = (Product) getIntent().getExtras().get("editProduct");
        edtEditNameProduct.setText(product.getNameProduct());
        edtEditPriceProduct.setText(product.getPriceProduct());
        edtEditDiscount.setText(product.getDiscount() + "");
        edtEditDescriptionProduct.setText(product.getDescriptionProduct());
        Glide.with(this).load(product.getImageProduct()).into(imgEditProduct);


        getListCategory();

        btnEditSelectImg.setOnClickListener(v -> {
            handlerEditImageProduct();
        });

        btnEditProduct.setOnClickListener(v -> {
            handlerEditProduct();
        });
        btnEditBackToAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(EditProductActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });


    }

    private void getListCategory() {
        DataClient dataClient = APIUtils.getData();
        Call<List<CategoryAdmin>> call = dataClient.callCategoryAdmin();
        call.enqueue(new Callback<List<CategoryAdmin>>() {
            @Override
            public void onResponse(Call<List<CategoryAdmin>> call, Response<List<CategoryAdmin>> response) {
                ArrayList<CategoryAdmin> categoryAdmins = (ArrayList<CategoryAdmin>) response.body();
                for (int i = 0; i < categoryAdmins.size(); i++) {
                    list.add(new CategoryAdmin(categoryAdmins.get(i).getIdCategory(), categoryAdmins.get(i).getNameCategory()));
                }
                categoryDropDownAdapter = new CategoryDropDownAdapter(EditProductActivity.this, list);
                spinEditIdCategory.setAdapter(categoryDropDownAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryAdmin>> call, Throwable t) {

            }
        });
    }

    private void handlerEditProduct() {
        int idProduct = product.getIdProduct();
        String nameProduct = edtEditNameProduct.getText().toString().trim();
        String priceProduct = edtEditPriceProduct.getText().toString().trim();
        String description = edtEditDescriptionProduct.getText().toString().trim();
        int discount = Integer.parseInt(edtEditDiscount.getText().toString().trim());

        spinEditIdCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryDropDownAdapter.getItem(position).getIdCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String realPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(realPath);
        String filePath = file.getAbsolutePath();
        String[] fileReal = filePath.split("\\.");
        String fileRealPath = fileReal[0] + System.currentTimeMillis() + "." + fileReal[1];
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/formdata"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", fileRealPath, requestBody);
        DataClient dataClient = APIUtils.getData();
        Call<String> callChangeImage = dataClient.callImageNewProduct(body);
        callChangeImage.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                String image = APIUtils.BASE_URL + "image/guitar/" + mess;
                DataClient dataClientEditProduct = APIUtils.getData();
                Call<String> callEditProduct = dataClientEditProduct.callUpdateProduct(idProduct, nameProduct, priceProduct, image, description, discount, idCategory);
                callEditProduct.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String mess = response.body();
                        if (mess.equals("success")) {
                            Toast.makeText(EditProductActivity.this, "update success", Toast.LENGTH_SHORT).show();
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

    private void handlerEditImageProduct() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "select image"));
    }

    private void initEditProduct() {
        edtEditDescriptionProduct = findViewById(R.id.edtEditDescriptionProduct);
        edtEditDiscount = findViewById(R.id.edtEditDiscount);
        edtEditPriceProduct = findViewById(R.id.edtEditPriceProduct);
        edtEditNameProduct = findViewById(R.id.edtEditNameProduct);
        btnEditBackToAdmin = findViewById(R.id.btnEditBackToAdmin);
        btnEditProduct = findViewById(R.id.btnEditProduct);
        btnEditSelectImg = findViewById(R.id.btnEditSelectImg);
        imgEditProduct = findViewById(R.id.imgEditProduct);
        spinEditIdCategory = findViewById(R.id.spinEditIdCategory);
    }
}