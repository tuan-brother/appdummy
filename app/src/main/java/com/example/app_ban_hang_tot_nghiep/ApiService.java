package com.example.app_ban_hang_tot_nghiep;

import com.example.app_ban_hang_tot_nghiep.model.Cart;
import com.example.app_ban_hang_tot_nghiep.model.CartData;
import com.example.app_ban_hang_tot_nghiep.model.Category;
import com.example.app_ban_hang_tot_nghiep.model.Product;
import com.example.app_ban_hang_tot_nghiep.model.Token;
import com.example.app_ban_hang_tot_nghiep.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/users/login")
    Call<Token> getAnswers(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/users/create")
    Call<UserInfo> createInf(@Field("email") String email, @Field("password") String password, @Field("repassword") String repassword);

    @GET("api/products/getall")
    Call<List<Product>> getListProducts();

    @GET("api/categories/getall")
    Call<List<Category>> getListCategory();

    @GET("api/products/category/{idCategory}")
    Call<List<Product>> getListProductWithCate(@Path("idCategory") String id);

    @FormUrlEncoded
    @POST("api/cart/add/{idProduct}")
    Call<Cart> addProductToCart(@Path("idProduct") String productId, @Field("token") String token, @Field("amount") int amount);

    @Headers("Content-Type: application/json")
    @GET("api/cart/getcart")
    Call<CartData> getCart(@Query(value = "token") String token);

    @GET("api/bills/get")
    Call<Cart> getBill(@Field("token") String token);
}
