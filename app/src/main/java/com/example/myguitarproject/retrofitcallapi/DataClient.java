package com.example.myguitarproject.retrofitcallapi;

import com.example.myguitarproject.advertisement.Advertisement;
import com.example.myguitarproject.cart.Cart;
import com.example.myguitarproject.listproduct.Category;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.orderstatus.OrderStatus;
import com.example.myguitarproject.user.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("saveAvatarToFolder.php")
    Call<String> callAvatarUser(@Part MultipartBody.Part file_image);

    @FormUrlEncoded
    @POST("RegisterUser.php")
    Call<String> callRegisterUser(@Field("username") String username,
                                  @Field("password") String pasword,
                                  @Field("email") String email,
                                  @Field("avatar") String avatar,
                                  @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("Login.php")
    Call<List<User>> callLogin(@Field("username") String username,
                               @Field("password") String password);


    @GET("getListAds.php")
    Call<List<Advertisement>> callAds();

    @GET("getListCategory.php")
    Call<List<Category>> callCategory();

    @GET("getDetailProduct.php")
    Call<List<Product>> callProduct();

    @FormUrlEncoded
    @POST("insertCart.php")
    Call<String> callInsertCart(@Field("id_product") int id_product,
                                @Field("name_product") String name_product,
                                @Field("price_product") String price_product,
                                @Field("image_product") String image_product,
                                @Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("getListCart.php")
    Call<List<Cart>> callListCart(@Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("insertDetailOrder.php")
    Call<String> callInsertOrder(@Field("id_product") int id_product,
                                 @Field("name_product") String name_product,
                                 @Field("sumPrice") String sumPrice,
                                 @Field("quantily") int quantily,
                                 @Field("image_product") String image_product,
                                 @Field("customer_name") String customer_name,
                                 @Field("phone_number") String phone_number,
                                 @Field("address") String address,
                                 @Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("removeCartItem.php")
    Call<String> callDeleteCartItem(@Field("id_cart") int id_cart);

    @FormUrlEncoded
    @POST("changePassword.php")
    Call<String> callChangePassword(@Field("id_user") int id_user,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("confirmPassword.php")
    Call<String> callConfirmPass(@Field("id_user") int id_user,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("changePhoneNumber.php")
    Call<String> callChangePhoneNumber(@Field("id_user") int id_user,
                                       @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("getUserProfileWithID.php")
    Call<List<User>> callUserProfile(@Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("changeAvatar.php")
    Call<String> callChangeAvatar(@Field("id_user") int id_user,
                                  @Field("avatar") String avatar);

    @GET("getListProductDiscount.php")
    Call<List<Product>> callListProductDisCount();

    @GET("getListProductWithDateTime.php")
    Call<List<Product>> callListNewProduct();

    @GET("getListProductBestSelling.php")
    Call<List<Product>> callListBestSellingProduct();

    @FormUrlEncoded
    @POST("getListProductWithIdProduct.php")
    Call<List<Product>> callProductWithId(@Field("id_product") int idProduct);

    @FormUrlEncoded
    @POST("updateQuantilySold.php")
    Call<String> callUpdateQuantilySold(@Field("id_product") int idProduct,
                                        @Field("quantily_sold") int quantilySold);

    @FormUrlEncoded
    @POST("getOrderStatus.php")
    Call<List<OrderStatus>> callOrderStatus(@Field("id_user") int idUser);

    @FormUrlEncoded
    @POST("searchProductWithName.php")
    Call<List<Product>> callSearchProduct(@Field("name_product") String nameProduct);
}
