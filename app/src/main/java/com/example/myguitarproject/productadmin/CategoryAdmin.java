package com.example.myguitarproject.productadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myguitarproject.R;

import java.io.Serializable;

public class CategoryAdmin {
    private int IdCategory;
    private String NameCategory;

    public CategoryAdmin(int idCategory, String nameCategory) {
        IdCategory = idCategory;
        NameCategory = nameCategory;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }

    public String getNameCategory() {
        return NameCategory;
    }

    public void setNameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }
}
