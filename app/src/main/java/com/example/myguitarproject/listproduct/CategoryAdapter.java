package com.example.myguitarproject.listproduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myguitarproject.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> mListCategory;
    private Context mContext;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_hozirontal, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if(category==null){
            return;
        }
        holder.tvTitle.setText(category.getNameCategory());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvListProduct.setLayoutManager(linearLayoutManager);

        ProductAdapter productAdapter = new ProductAdapter(mContext);
        productAdapter.setData(category.getmListProduct());
        holder.rcvListProduct.setAdapter(productAdapter);

    }

    @Override
    public int getItemCount() {
        if(mListCategory!=null){
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private RecyclerView rcvListProduct;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rcvListProduct = itemView.findViewById(R.id.rcvListProduct);
        }
    }
}
