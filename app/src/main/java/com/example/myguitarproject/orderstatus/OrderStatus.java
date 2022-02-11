package com.example.myguitarproject.orderstatus;

public class OrderStatus {
    private int IdOrder;
    private String NameProduct;
    private String SumPrice;
    private int Quantily;
    private String ImageProduct;
    private String CustomerName;
    private String PhoneNumber;
    private String Address;
    private Boolean Status;

    public OrderStatus(int idOrder, String nameProduct, String sumPrice, int quantily, String imageProduct, String customerName, String phoneNumber, String address, Boolean status) {
        IdOrder = idOrder;
        NameProduct = nameProduct;
        SumPrice = sumPrice;
        Quantily = quantily;
        ImageProduct = imageProduct;
        CustomerName = customerName;
        PhoneNumber = phoneNumber;
        Address = address;
        Status = status;
    }

    public int getIdOrder() {
        return IdOrder;
    }

    public void setIdOrder(int idOrder) {
        IdOrder = idOrder;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(String sumPrice) {
        SumPrice = sumPrice;
    }

    public int getQuantily() {
        return Quantily;
    }

    public void setQuantily(int quantily) {
        Quantily = quantily;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
