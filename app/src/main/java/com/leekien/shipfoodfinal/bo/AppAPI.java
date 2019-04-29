package com.leekien.shipfoodfinal.bo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppAPI {

    @GET("user/login")
    Call<List<User>> getUser();
    @GET("foods/all")
    Call<List<TypeFood>> getFood();
    @POST("foods/add")
    Call<ResponseBody> addFood(@Body Food food);
    @FormUrlEncoded
    @PUT("foods/update/{id}")
    Call<ResponseBody> updateFood(@Path("id") int id,@Field("name") String name,@Field("price")
            String price,@Field("url") String url,@Field("discount") String discount);
    @DELETE("foods/delete/{id}")
    Call<ResponseBody> deleteFood(@Path("id") int id);
    @POST("user/register")
    Call<ResponseBody> addUser(@Body User user);
    @GET("user/getusername")
    Call<List<String>> getUserName();
    @FormUrlEncoded
    @PUT("user/update/{id}")
    Call<ResponseBody> updateUser(@Path("id") int id,@Field("name") String name,@Field("phone")
            String phone,@Field("birthdate") String birthdate,@Field("location") String location);
    @FormUrlEncoded
    @PUT("user/updateLocation/{id}")
    Call<ResponseBody> updateLocation(@Path("id") int id,@Field("lat") String lat,@Field("lon")
            String lon);
    @GET("orders/getOrder/{id}")
    Call<Order> getOrder(@Path("id") int id);
    @POST("orders/add")
    Call<Integer> addOrder(@Body Order order);
    @POST("foodorder/add")
    Call<ResponseBody> addFoodOrder(@Body Foodorder foodorder);
    @GET("orders/getall/{id}")
    Call<List<Order>> getAllOrder(@Path("id") int id);
    @GET("orders/getOrderCus/{id}")
    Call<List<Order>> getOrderCus(@Path("id") int id);
    @FormUrlEncoded
    @PUT("orders/update/{id}")
    Call<ResponseBody> updateOrder(@Path("id") int id,@Field("type") String type,@Field("shiphour")
            String shiphour,@Field("iduser") int iduser,@Field("shiptime") String shiptime);
    @FormUrlEncoded
    @PUT("orders/updateend/{id}")
    Call<ResponseBody> updateEnd(@Path("id") int id,@Field("type") String type,@Field("endhour")
            String endhour,@Field("endtime") String endtime);
    @GET("orders/getSuccessOrder")
    Call<List<Order>> getSuccessOrder();
    @GET("orders/getWaitOrder/{id}")
    Call<Order> getWaitOrder(@Path("id") int id);
    @FormUrlEncoded
    @PUT("orders/updateSuccess/{id}")
    Call<ResponseBody> updateOrderSuccess(@Path("id") int id,@Field("type") String type,@Field("content") String content);
    @DELETE("orders/delete/{id}")
    Call<ResponseBody> deleteOrder(@Path("id") int id);

}
