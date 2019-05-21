package com.leekien.shipfoodfinal.signup;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;

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
    @Override
    public void add(User user, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.addUser(user, MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void getUserName(Callback<List<String>> callback) {
        Call<List<String>> call = appAPI.getUserName(MainActivity.auth);
        call.enqueue(callback);
    }

}
