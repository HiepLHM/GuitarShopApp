package com.example.myguitarproject.orderdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.R;
import com.example.myguitarproject.cart.Cart;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Cart> mListCart;
    private Context mContext;

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Cart> list){
        this.mListCart = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Cart cart = mListCart.get(position);
        //cart = (Cart) getIntent().getExtras().get("mCart");
        if(cart==null){
            return;
        }
        holder.tvNameProductOrder.setText(cart.getNameProduct());
        holder.tvPriceProductOrder.setText(cart.getPriceProduct());
        holder.tvQuantilyOrder.setText("x"+cart.getQuanlityProduct());
        Glide.with(mContext).load(cart.getImageProduct()).into(holder.imgOrder);
    }

    @Override
    public int getItemCount() {
        if(mListCart!=null){
            return mListCart.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameProductOrder, tvPriceProductOrder, tvQuantilyOrder;
        private ImageView imgOrder;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProductOrder = itemView.findViewById(R.id.tvNameProductOrder);
            tvPriceProductOrder = itemView.findViewById(R.id.tvPriceProductOrder);
            tvQuantilyOrder = itemView.findViewById(R.id.tvQuantilyOrder);
            imgOrder = itemView.findViewById(R.id.imgOrder);
        }
    }
}
