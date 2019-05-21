package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.MainActivity;
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
        Call<List<TypeFood>> call = appAPI.getFood(MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void getFoodShop(Callback<List<TypeFood>> callback,int id) {
        Call<List<TypeFood>> call = appAPI.getAllFoodShop(id,MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void getWaitOrder(Callback<Order> callback, int iduser) {
        Call<Order> call = appAPI.getWaitOrder(iduser,MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void getListShop(Callback<List<User>> callback) {
        Call<List<User>> call = appAPI.getShop(MainActivity.auth);
        call.enqueue(callback);
    }
}
