package com.example.myguitarproject.productDiscount;

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

import java.text.DecimalFormat;
import java.util.List;

public class DisCountAdapter extends RecyclerView.Adapter<DisCountAdapter.DisCountViewHolder> {
    private List<Product> mlistProductDiscount;
    private Context mContext;

    public DisCountAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> list) {
        this.mlistProductDiscount = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DisCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discount, parent, false);
        return new DisCountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisCountViewHolder holder, int position) {
        Product productDiscount = mlistProductDiscount.get(position);
        if (productDiscount == null) {
            return;
        }
        Glide.with(mContext).load(productDiscount.getImageProduct()).into(holder.imgProductDiscount);
        holder.tvDiscount.setText(String.valueOf(productDiscount.getDiscount()) + "%");
        holder.tvNameProductDiscount.setText(productDiscount.getNameProduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long price = Long.parseLong(productDiscount.getPriceProduct().trim());
        holder.tvPriceProductReal.setText(decimalFormat.format(price) + " vnd");
        long discount = Long.parseLong(String.valueOf(productDiscount.getDiscount()));
        long priceDiscount = price - (discount * price / 100);
        holder.tvPriceProductDiscount.setText(decimalFormat.format(priceDiscount) + " vnd");
        holder.cvDiscountProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetailProduct = new Intent(mContext, DetailProduct.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_product", productDiscount);
                gotoDetailProduct.putExtras(bundle);
                mContext.startActivity(gotoDetailProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mlistProductDiscount != null) {
            return mlistProductDiscount.size();
        }
        return 0;
    }

    public class DisCountViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductDiscount;
        private TextView tvDiscount, tvNameProductDiscount, tvPriceProductReal, tvPriceProductDiscount;
        private CardView cvDiscountProduct;

        public DisCountViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductDiscount = itemView.findViewById(R.id.imgProductDiscount);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvNameProductDiscount = itemView.findViewById(R.id.tvNameProductDiscount);
            tvPriceProductReal = itemView.findViewById(R.id.tvPriceProductReal);
            tvPriceProductDiscount = itemView.findViewById(R.id.tvPriceProductDiscount);
            cvDiscountProduct = itemView.findViewById(R.id.cvDiscountProduct);
        }
    }
}
