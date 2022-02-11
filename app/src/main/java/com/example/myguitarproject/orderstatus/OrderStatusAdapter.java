package com.example.myguitarproject.orderstatus;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.R;

import java.util.List;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.OrderStatusViewHolder> {
    private List<OrderStatus> mListOrderStatus;
    private Context mContext;

    public OrderStatusAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<OrderStatus> list){
        this.mListOrderStatus = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        return new OrderStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        OrderStatus orderStatus = mListOrderStatus.get(position);
        if(orderStatus==null){
            return;
        }
        holder.tvNameProductOrderStatus.setText(orderStatus.getNameProduct());
        holder.tvPriceProductOrderStatus.setText(orderStatus.getSumPrice());
        holder.tvQuantilyOrderStatus.setText(String.valueOf(orderStatus.getQuantily()));
        Glide.with(mContext).load(orderStatus.getImageProduct()).into(holder.imgOrderStatus);

        Boolean status = orderStatus.getStatus();
        if(status){
            holder.tvOrderStatus.setText("Đã giao hàng thành công");
            holder.tvOrderStatus.setBackgroundColor(Color.GREEN);
        } else {
            holder.tvOrderStatus.setText("Đang giao hàng");
            holder.tvOrderStatus.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        if(mListOrderStatus!=null){
            return mListOrderStatus.size();
        }
        return 0;
    }

    public class OrderStatusViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgOrderStatus;
        private TextView tvNameProductOrderStatus, tvPriceProductOrderStatus, tvQuantilyOrderStatus, tvOrderStatus;
        public OrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrderStatus = itemView.findViewById(R.id.imgOrderStatus);
            tvNameProductOrderStatus = itemView.findViewById(R.id.tvNameProductOrderStatus);
            tvPriceProductOrderStatus = itemView.findViewById(R.id.tvPriceProductOrderStatus);
            tvQuantilyOrderStatus = itemView.findViewById(R.id.tvQuantilyOrderStatus);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
        }
    }
}
