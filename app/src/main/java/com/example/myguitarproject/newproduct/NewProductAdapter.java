package com.example.myguitarproject.newproduct;

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
import com.example.myguitarproject.listproduct.Product;

import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.NewProductViewHolder>{
    private List<Product> mListNewProduct;
    private Context mContext;

    public NewProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> list){
        this.mListNewProduct = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_product, parent, false);
        return new NewProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductViewHolder holder, int position) {
        Product product = mListNewProduct.get(position);
        if(product==null){
            return;
        }
        Glide.with(mContext).load(product.getImageProduct()).into(holder.imgProductNew);
        holder.tvNameProductNew.setText(product.getNameProduct());
        holder.tvPriceProductRealNew.setText(product.getPriceProduct());
        holder.cvNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetailProduct = new Intent(mContext, DetailProduct.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_product", product);
                gotoDetailProduct.putExtras(bundle);
                mContext.startActivity(gotoDetailProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListNewProduct!=null){
            return mListNewProduct.size();
        }
        return 0;
    }

    public class NewProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProductNew;
        private TextView tvNameProductNew, tvPriceProductRealNew;
        private CardView cvNewProduct;
        public NewProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductNew =itemView.findViewById(R.id.imgProductNew);
            tvNameProductNew = itemView.findViewById(R.id.tvNameProductNew);
            tvPriceProductRealNew = itemView.findViewById(R.id.tvPriceProductRealNew);
            cvNewProduct =itemView.findViewById(R.id.cvNewProduct);
        }
    }
}
