package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeInteractor implements HomeManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getFood(Callback<List<TypeFood>> callback) {
        Call<List<TypeFood>> call = appAPI.getFood();
        call.enqueue(callback);
    }

    @Override
    public void getWaitOrder(Callback<Order> callback, int iduser) {
        Call<Order> call = appAPI.getWaitOrder(iduser);
        call.enqueue(callback);
    }
}
