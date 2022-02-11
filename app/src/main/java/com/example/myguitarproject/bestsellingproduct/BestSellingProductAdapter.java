package com.example.myguitarproject.bestsellingproduct;

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

public class BestSellingProductAdapter extends RecyclerView.Adapter<BestSellingProductAdapter.BestSellingViewHolder> {
    //private List<BestSellingProduct> mListBestSellingProduct;
    private List<Product> mListProduct;
    private Context mContext;

    public BestSellingProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> list){
        //this.mListBestSellingProduct = list;
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BestSellingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best_selling_product, parent, false);
        return new BestSellingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellingViewHolder holder, int position) {
        //BestSellingProduct bestSellingProduct = mListBestSellingProduct.get(position);
        Product product = mListProduct.get(position);
//        if(bestSellingProduct==null){
//            return;
//        }
        if(product==null){
            return;
        }
//        holder.tvNameProductSold.setText(bestSellingProduct.getNameProduct());
//        holder.tvPriceProductSold.setText(bestSellingProduct.getPriceProduct());
//        holder.tvQuantilySold.setText(String.valueOf(bestSellingProduct.getQuantilySold()));
//        Glide.with(mContext).load(bestSellingProduct.getImageProduct()).into(holder.imgProductSold);
        holder.tvNameProductSold.setText(product.getNameProduct());
        holder.tvPriceProductSold.setText(product.getPriceProduct());
        holder.tvQuantilySold.setText(String.valueOf(product.getQuantilySold()));
        Glide.with(mContext).load(product.getImageProduct()).into(holder.imgProductSold);
        holder.cvBestSellingProduct.setOnClickListener(new View.OnClickListener() {
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
//        if(mListBestSellingProduct!=null){
//            return mListBestSellingProduct.size();
//        }
        if(mListProduct!=null){
            return mListProduct.size();
        }
        return 0;
    }

    public class BestSellingViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProductSold;
        private TextView tvNameProductSold, tvPriceProductSold, tvQuantilySold;
        private CardView cvBestSellingProduct;
        public BestSellingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductSold = itemView.findViewById(R.id.imgProductSold);
            tvNameProductSold = itemView.findViewById(R.id.tvNameProductSold);
            tvPriceProductSold = itemView.findViewById(R.id.tvPriceProductSold);
            tvQuantilySold = itemView.findViewById(R.id.tvQuantilySold);
            cvBestSellingProduct = itemView.findViewById(R.id.cvBestSellingProduct);
        }
    }
}
