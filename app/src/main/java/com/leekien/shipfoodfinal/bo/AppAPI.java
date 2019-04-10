package com.leekien.shipfoodfinal.bo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    @POST("user/register")
    Call<ResponseBody> addUser(@Body User user);
    @GET("user/getusername")
    Call<List<String>> getUserName();
    @GET("orders/getOrder/{id}")
    Call<Order> getOrder(@Path("id") int id);
    @POST("orders/add")
    Call<Integer> addOrder(@Body Order order);
    @POST("foodorder/add")
    Call<ResponseBody> addFoodOrder(@Body Foodorder foodorder);
    @GET("orders/getall/{id}")
    Call<List<Order>> getAllOrder(@Path("id") int id);
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

}
