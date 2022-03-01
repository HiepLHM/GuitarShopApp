package com.example.myguitarproject.searchproduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.R;
import com.example.myguitarproject.listproduct.Product;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Product> mListProduct;
    private Context mContext;

    public SearchAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> list) {
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_product, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) {
            return;
        }
        Glide.with(mContext).load(product.getImageProduct()).into(holder.imgSearch);
        holder.tvNameProductSearch.setText(product.getNameProduct());
        holder.tvPriceProductSearch.setText(product.getPriceProduct() + "vnd");
        holder.tvDiscount.setText(product.getDiscount() + "%");
        holder.tvQuantilySold.setText(product.getQuantilySold() + "");
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViewSearch;
        private ImageView imgSearch;
        private TextView tvNameProductSearch, tvPriceProductSearch, tvDiscount, tvPriceProductSearchDiscount, tvQuantilySold;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewSearch = itemView.findViewById(R.id.cardViewSearch);
            imgSearch = itemView.findViewById(R.id.imgSearch);
            tvNameProductSearch = itemView.findViewById(R.id.tvNameProductSearch);
            tvPriceProductSearch = itemView.findViewById(R.id.tvPriceProductSearch);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvPriceProductSearchDiscount = itemView.findViewById(R.id.tvPriceProductSearchDiscount);
            tvQuantilySold = itemView.findViewById(R.id.tvQuantilySold);
        }
    }
}
