package com.leekien.shipfoodfinal.bo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @POST("orders/add")
    Call<ResponseBody> addOrder(@Body Order order);
    @POST("foodorder/add")
    Call<ResponseBody> addFoodOrder(@Body Foodorder foodorder);
    @GET("getAuthor.php")
    Call<ResponseBody[]> getAuthor(@Query("idAuthor") int idAuthor);

    @GET("getNewsById.php")
    Call<ResponseBody[]> getNewsById(@Query("idNews") int idNews);

    @FormUrlEncoded
    @POST("uploadcomment.php")
    Call<ResponseBody> postComment(@Field("idNews") String idRoom, @Field("content") String cmt);

}
