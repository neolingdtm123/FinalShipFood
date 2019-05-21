package com.leekien.shipfoodfinal.logout;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LogOutInteractor implements LogOutManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void updateInfo(User users, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.updateUser(users.getId(),users.getName(),users.getPhone(),users.getBirthdate(),users.getLocation(), MainActivity.auth);
        call.enqueue(callback);
    }
}
