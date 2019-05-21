package com.leekien.shipfoodfinal.statis;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class StatisInteractor implements StatisManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getSuccessOrder(Callback<List<Order>> callback) {
        Call<List<Order>> call = appAPI.getSuccessOrder(MainActivity.user.getId(),MainActivity.auth);
        call.enqueue(callback);
    }
}
