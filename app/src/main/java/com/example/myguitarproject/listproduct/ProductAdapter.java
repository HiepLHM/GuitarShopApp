package com.example.myguitarproject.listproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.DetailProduct;
import com.example.myguitarproject.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private List<Product> mListProduct;
    private Context mContext;

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Product> list){
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product==null){
            return;
        }
        Glide.with(mContext).load(product.getImageProduct()).into(holder.imgProduct);
        holder.tvNameProduct.setText(product.getNameProduct());
        holder.tvPriceProduct.setText(product.getPriceProduct());
        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetailProduct(product);
            }
        });
    }

    private void callDetailProduct(Product product) {
        Intent intent = new Intent(mContext, DetailProduct.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product",product);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(mListProduct!=null){
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView tvNameProduct, tvPriceProduct;
        private CardView cvProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            cvProduct = itemView.findViewById(R.id.cvProduct);
        }
    }
}
