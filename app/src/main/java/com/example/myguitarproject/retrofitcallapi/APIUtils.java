package com.example.myguitarproject.retrofitcallapi;

public class APIUtils {
    public static final String BASE_URL = "http://192.168.1.218/MyGuitarProject/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
