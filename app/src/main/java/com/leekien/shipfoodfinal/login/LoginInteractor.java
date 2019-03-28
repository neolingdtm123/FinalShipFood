package com.leekien.shipfoodfinal.login;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginInteractor implements LoginManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getInfo(Callback<List<User>> callback) {
        Call<List<User>> call = appAPI.getUser();
        call.enqueue(callback);
    }
}
