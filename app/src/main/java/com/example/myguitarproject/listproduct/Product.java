package com.example.myguitarproject.listproduct;

import java.io.Serializable;

public class Product implements Serializable {
    private int IdProduct;
    private String NameProduct;
    private String PriceProduct;
    private String ImageProduct;
    private String DescriptionProduct;
    private int Discount;
    private int QuantilySold;
    private int IdCategory;

    public Product(int idProduct, String nameProduct, String priceProduct, String imageProduct, String descriptionProduct, int idCategory) {
        IdProduct = idProduct;
        NameProduct = nameProduct;
        PriceProduct = priceProduct;
        ImageProduct = imageProduct;
        DescriptionProduct = descriptionProduct;
        IdCategory = idCategory;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getQuantilySold() {
        return QuantilySold;
    }

    public void setQuantilySold(int quantilySold) {
        QuantilySold = quantilySold;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return PriceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        PriceProduct = priceProduct;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }

    public String getDescriptionProduct() {
        return DescriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        DescriptionProduct = descriptionProduct;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }
}
