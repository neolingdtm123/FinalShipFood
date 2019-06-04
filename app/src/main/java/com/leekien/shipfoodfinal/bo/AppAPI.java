package com.leekien.shipfoodfinal.bo;

import com.leekien.shipfoodfinal.MainActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppAPI {
    @FormUrlEncoded
    @POST("send/{id}")
    Call<ResponseBody> push(@Path("id") int id,@Field("name") String name,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("username") String username,@Field("password") String password);
    @FormUrlEncoded
    @POST("user/login")
    Call<User> getUser(@Field("username") String username,@Field("password") String password,@Field("token") String token);
    @GET("user/getShip")
    Call<List<User>> getShip(@Header("Authorization") String authHeader);
    @GET("user/getShop")
    Call<List<User>> getShop(@Header("Authorization") String authHeader);
    @DELETE("user/delete/{id}")
    Call<ResponseBody> deleteUser(@Path("id") int id,@Header("Authorization") String authHeader);
    @GET("foods/all")
    Call<List<TypeFood>> getFood(@Header("Authorization") String authHeader);
    @GET("foods/allShop/{id}")
    Call<List<TypeFood>> getAllFoodShop(@Path("id") int id,@Header("Authorization") String authHeader);
    @POST("foods/add")
    Call<ResponseBody> addFood(@Body Food food,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("foods/update/{id}")
    Call<ResponseBody> updateFood(@Path("id") int id,@Field("name") String name,@Field("price")
            String price,@Field("url") String url,@Field("discount") String discount,@Header("Authorization") String authHeader);
    @DELETE("foods/delete/{id}")
    Call<ResponseBody> deleteFood(@Path("id") int id,@Header("Authorization") String authHeader);
    @POST("user/register")
    Call<ResponseBody> addUser(@Body User user,@Header("Authorization") String authHeader);
    @GET("user/getusername")
    Call<List<String>> getUserName(@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("user/updatePass/{id}")
    Call<ResponseBody> updatePass(@Path("id") int id,@Field("password") String password,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("user/update/{id}")
    Call<ResponseBody> updateUser(@Path("id") int id,@Field("name") String name,@Field("phone")
            String phone,@Field("birthdate") String birthdate,@Field("location") String location,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("user/updateLocation/{id}")
    Call<ResponseBody> updateLocation(@Path("id") int id,@Field("lat") String lat,@Field("lon")
            String lon,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("user/updatePoint/{id}")
    Call<ResponseBody> updatePoint(@Path("id") int id,@Field("point") String point,@Header("Authorization") String authHeader);
    @GET("orders/getOrder/{id}")
    Call<Order> getOrder(@Path("id") int id,@Header("Authorization") String authHeader);
    @POST("orders/add")
    Call<Integer> addOrder(@Body Order order,@Header("Authorization") String authHeader);
    @POST("foodorder/add")
    Call<ResponseBody> addFoodOrder(@Body Foodorder foodorder,@Header("Authorization") String authHeader);
    @GET("orders/getall/{id}")
    Call<List<Order>> getAllOrder(@Path("id") int id,@Header("Authorization") String authHeader);
    @GET("orders/getOrderCus/{id}")
    Call<List<Order>> getOrderCus(@Path("id") int id,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("orders/update/{id}")
    Call<ResponseBody> updateOrder(@Path("id") int id,@Field("type") String type,@Field("shiphour")
            String shiphour,@Field("iduser") int iduser,@Field("shiptime") String shiptime,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("orders/updateend/{id}")
    Call<ResponseBody> updateEnd(@Path("id") int id,@Field("type") String type,@Field("endhour")
            String endhour,@Field("endtime") String endtime,@Header("Authorization") String authHeader);
    @GET("orders/getSuccessOrder/{id}")
    Call<List<Order>> getSuccessOrder(@Path("id") int id, @Header("Authorization") String authHeader);
    @GET("orders/getWaitOrder/{id}")
    Call<Order> getWaitOrder(@Path("id") int id,@Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("orders/updateSuccess/{id}")
    Call<ResponseBody> updateOrderSuccess(@Path("id") int id,@Field("type") String type,@Field("content") String content,@Header("Authorization") String authHeader);

}
