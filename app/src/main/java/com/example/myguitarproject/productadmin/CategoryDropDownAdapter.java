package com.example.myguitarproject.productadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myguitarproject.R;

import java.util.ArrayList;


public class CategoryDropDownAdapter extends ArrayAdapter<CategoryAdmin> {

    public CategoryDropDownAdapter(@NonNull Context context, ArrayList<CategoryAdmin> categoryAdmins) {
        super(context, 0, categoryAdmins);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    public View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_spinner, parent, false);
        }
        TextView tvIdSelected = convertView.findViewById(R.id.tvIdSelected);
        TextView tvNameSelected = convertView.findViewById(R.id.tvNameSelected);
        CategoryAdmin categoryAdmin = getItem(position);
        if (categoryAdmin != null) {
            tvIdSelected.setText(categoryAdmin.getIdCategory() + "");
            tvNameSelected.setText(categoryAdmin.getNameCategory());
        }
        return convertView;
    }
}
