package com.example.myguitarproject.advertisement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.R;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder> {
    private List<Advertisement> mListAds;
    private Context mContext;

    public AdvertisementAdapter(List<Advertisement> mListAds, Context mContext) {
        this.mListAds = mListAds;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdvertisementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ads, parent, false);
        return new AdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementViewHolder holder, int position) {
        Advertisement advertisement = mListAds.get(position);
        if (advertisement == null) {
            return;
        }
        Glide.with(mContext).load(advertisement.getImageAds()).into(holder.imgAds);
    }

    @Override
    public int getItemCount() {
        if (mListAds != null) {
            return mListAds.size();
        }
        return 0;
    }

    public class AdvertisementViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAds;

        public AdvertisementViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAds = itemView.findViewById(R.id.imgAds);
        }
    }
}
