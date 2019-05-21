package com.leekien.shipfoodfinal.changepass;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangePassInteractor implements ChangePassManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void updateInfo(int id, String pass, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.updatePass(id, pass,MainActivity.auth);
        call.enqueue(callback);
    }
}
