package com.example.myguitarproject.productadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.AddNewProduct;
import com.example.myguitarproject.EditProductActivity;
import com.example.myguitarproject.R;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductAdminAdapter extends RecyclerView.Adapter<ListProductAdminAdapter.ListProdcutAdminViewHolder> {
    private List<Product> mListProduct;
    private Context mContext;

    public ListProductAdminAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> mListProduct) {
        this.mListProduct = mListProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListProdcutAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product_admin, parent, false);
        return new ListProdcutAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProdcutAdminViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) {
            return;
        }
        Glide.with(mContext).load(product.getImageProduct()).into(holder.imgAdminProduct);
        holder.tvNameProductAdmin.setText(product.getNameProduct());
        holder.tvPriceAdmin.setText(product.getPriceProduct());
        int discount = product.getDiscount();
        holder.tvDiscountAdmin.setText(discount + "%");


        holder.btnEditAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("editProduct", product);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        holder.btnDeleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirm delete product!")
                        .setMessage("Are you sure?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //deleteProductWithId();
                                mListProduct.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                int idProduct = product.getIdProduct();
                                DataClient dataClient = APIUtils.getData();
                                Call<String> call = dataClient.callDeleteProductWithId(idProduct);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String mess = response.body();
                                        if (mess.equals("success")) {
                                            Toast.makeText(mContext, "delete Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .create()
                        .show();

            }
        });

    }


    @Override
    public int getItemCount() {
        if (mListProduct.size() != 0) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ListProdcutAdminViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAdminProduct;
        private TextView tvNameProductAdmin, tvPriceAdmin, tvDiscountAdmin;
        private Button btnDeleteAdmin, btnEditAdmin;

        public ListProdcutAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAdminProduct = itemView.findViewById(R.id.imgAdminProduct);
            tvNameProductAdmin = itemView.findViewById(R.id.tvNameProductAdmin);
            tvPriceAdmin = itemView.findViewById(R.id.tvPriceAdmin);
            tvDiscountAdmin = itemView.findViewById(R.id.tvDiscountAdmin);
            btnDeleteAdmin = itemView.findViewById(R.id.btnDeleteAdmin);
            btnEditAdmin = itemView.findViewById(R.id.btnEditAdmin);
        }
    }
}
