package com.example.myguitarproject.cart;

import java.io.Serializable;

public class Cart implements Serializable {
    private int IdCart;
    private int IdProduct;
    private String NameProduct;
    private String PriceProduct;
    private String ImageProduct;
    private int QuanlityProduct;
    private int IdUser;

    public Cart(int idCart, int idProduct, String nameProduct, String priceProduct, String imageProduct, int idUser) {
        IdCart = idCart;
        IdProduct = idProduct;
        NameProduct = nameProduct;
        PriceProduct = priceProduct;
        ImageProduct = imageProduct;
        //QuanlityProduct = quanlityProduct;
        IdUser = idUser;
    }

    public int getIdCart() {
        return IdCart;
    }

    public void setIdCart(int idCart) {
        IdCart = idCart;
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

    public int getQuanlityProduct() {
        return QuanlityProduct;
    }

    public void setQuanlityProduct(int quanlityProduct) {
        QuanlityProduct = quanlityProduct;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }
}
