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
    @GET("orders/getall")
    Call<List<Order>> getAllOrder();
    @FormUrlEncoded
    @PUT("orders/update/{id}")
    Call<ResponseBody> updateOrder(@Path("id") int id,@Field("type") String type,@Field("shiphour")
            String shiphour,@Field("iduser") int iduser,@Field("shiptime") String shiptime);
//    @PUT("orders/update/{id}")
//    Call<ResponseBody> updateOrder(@Path("id") int id,@Body Order order);

    @FormUrlEncoded
    @POST("uploadcomment.php")
    Call<ResponseBody> postComment(@Field("idNews") String idRoom, @Field("content") String cmt);

}
