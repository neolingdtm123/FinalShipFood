package com.leekien.shipfoodfinal.login;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginInteractor implements LoginManager.Interactor {
    AppAPI appAPIs = NetworkController.getInfoServices();
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getInfo(Callback<User> callback,String username,String password,String token,String auth) {
        Call<User> call = appAPI.getUser(username,password,token);
        call.enqueue(callback);
    }

    @Override
    public void login(Callback<ResponseBody> callback, String username, String password) {
        Call<ResponseBody> call = appAPIs.login(username,password);
        call.enqueue(callback);
    }
}
