package com.example.myguitarproject.advertisement;

import java.io.Serializable;

public class Advertisement implements Serializable {
    private int IdAds;
    private String ImageAds;
    private String ContentAds;
    private int IdCategory;

    public Advertisement(int idAds, String imageAds, String contentAds, int idCategory) {
        IdAds = idAds;
        ImageAds = imageAds;
        ContentAds = contentAds;
        IdCategory = idCategory;
    }

    public int getIdAds() {
        return IdAds;
    }

    public void setIdAds(int idAds) {
        IdAds = idAds;
    }

    public String getImageAds() {
        return ImageAds;
    }

    public void setImageAds(String imageAds) {
        ImageAds = imageAds;
    }

    public String getContentAds() {
        return ContentAds;
    }

    public void setContentAds(String contentAds) {
        ContentAds = contentAds;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }
}
