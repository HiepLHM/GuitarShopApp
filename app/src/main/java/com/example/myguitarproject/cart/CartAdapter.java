package com.example.myguitarproject.cart;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.myguitarproject.PaymentActivity;
import com.example.myguitarproject.R;
import com.example.myguitarproject.fragment.CartFragment;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> mListCart;
    private Context mContext;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public CartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Cart> list) {
        this.mListCart = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = mListCart.get(position);
        if (cart == null) {
            return;
        }
        viewBinderHelper.bind(holder.swipeLayout, String.valueOf(cart.getIdCart()));

        holder.tvNameCart.setText(cart.getNameProduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long price = Long.parseLong(cart.getPriceProduct());
        holder.tvPriceCart.setText(decimalFormat.format(price) + " vnd");
        Glide.with(mContext).load(cart.getImageProduct()).into(holder.imgCart);
        int quantily_first = Integer.parseInt(holder.tvQuantilyCart.getText().toString().trim());
        cart.setQuanlityProduct(quantily_first);
        if (quantily_first <= 1) {
            holder.btnMinus.setVisibility(View.INVISIBLE);
        }
        long price_first = Long.parseLong(cart.getPriceProduct());
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantily_click = Integer.parseInt(holder.tvQuantilyCart.getText().toString().trim()) + 1;
                holder.tvQuantilyCart.setText(quantily_click + "");
                long price_click = price_first * quantily_click;
                cart.setPriceProduct(price_click + "");
                cart.setQuanlityProduct(quantily_click);
                holder.tvPriceCart.setText(decimalFormat.format(price_click) + " vnd");
                CartFragment.sumPrice();
                if (quantily_click >= 1) {
                    holder.btnMinus.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantily_click = Integer.parseInt(holder.tvQuantilyCart.getText().toString().trim()) - 1;
                holder.tvQuantilyCart.setText(quantily_click + "");
                long price_click = price_first * quantily_click;
                cart.setPriceProduct(price_click + "");
                cart.setQuanlityProduct(quantily_click);
                holder.tvPriceCart.setText(decimalFormat.format(price_click) + " vnd");
                CartFragment.sumPrice();
                if (quantily_click <= 1) {
                    holder.btnMinus.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.tvDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListCart.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                DataClient dataClient = APIUtils.getData();
                Call<String> callDeleteCartItem = dataClient.callDeleteCartItem(cart.getIdCart());
                callDeleteCartItem.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String mess = response.body();
                        if (mess.equals("success")) {
                            CartFragment.sumPrice();
                            Toast.makeText(mContext, "Delete Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        holder.tvPaymentCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPayment = new Intent(mContext, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mCart", cart);
                gotoPayment.putExtras(bundle);
                mContext.startActivity(gotoPayment);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListCart != null) {
            return mListCart.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCart;
        private TextView tvNameCart, tvPriceCart, tvQuantilyCart, tvDeleteCart, tvPaymentCart;
        private Button btnMinus, btnPlus;
        private SwipeRevealLayout swipeLayout;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.imgCart);
            tvNameCart = itemView.findViewById(R.id.tvNameCart);
            tvPriceCart = itemView.findViewById(R.id.tvPriceCart);
            tvQuantilyCart = itemView.findViewById(R.id.tvQuantilyCart);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            tvDeleteCart = itemView.findViewById(R.id.tvDeleteCart);
            tvPaymentCart = itemView.findViewById(R.id.tvPaymentCart);
        }
    }
}
