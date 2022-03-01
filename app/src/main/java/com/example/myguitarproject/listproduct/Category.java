package com.example.myguitarproject.listproduct;


import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private int IdCategory;
    private String NameCategory;
    private String ImageCategory;
    private List<Product> mListProduct;

    public Category(int idCategory, String nameCategory, String imageCategory, List<Product> product) {
        IdCategory = idCategory;
        NameCategory = nameCategory;
        ImageCategory = imageCategory;
        mListProduct = product;
    }

    public Category(int idCategory, String nameCategory, String imageCategory) {
        IdCategory = idCategory;
        NameCategory = nameCategory;
        ImageCategory = imageCategory;
    }

    public List<Product> getmListProduct() {
        return mListProduct;
    }

    public void setmListProduct(List<Product> mListProduct) {
        this.mListProduct = mListProduct;
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

    public String getImageCategory() {
        return ImageCategory;
    }

    public void setImageCategory(String imageCategory) {
        ImageCategory = imageCategory;
    }
}
