package com.leekien.shipfoodfinal.admin;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AdminInteractor implements AdminManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getShip(Callback<List<User>> callback) {
        Call<List<User>> call = appAPI.getShip();
        call.enqueue(callback);
    }

    @Override
    public void getShop(Callback<List<User>> callback) {
        Call<List<User>> call = appAPI.getShop();
        call.enqueue(callback);

    }

    @Override
    public void deleteUser(int id, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.deleteUser(id);
        call.enqueue(callback);
    }
}
