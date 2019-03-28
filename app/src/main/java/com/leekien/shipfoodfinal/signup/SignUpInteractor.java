package com.leekien.shipfoodfinal.signup;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.NetworkController;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpInteractor implements SignUpManager.Interactor  {
    AppAPI appAPI = NetworkController.getInfoService();
    List<Comment > list = new ArrayList<Comment>();
    @Override
    public void show(  Callback<List<Comment>>  callback ) {
        Call<List<Comment>> call = appAPI.getNewsImage();
        call.enqueue(callback);

    }

    @Override
    public void add(Comment comment) {
       Call<ResponseBody> call= appAPI.add(comment);
        call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });
    }
}
