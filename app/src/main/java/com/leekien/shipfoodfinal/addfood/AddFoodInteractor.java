package com.leekien.shipfoodfinal.addfood;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddFoodInteractor implements AddFoodManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void addFood(Callback<ResponseBody> callback, Food food) {
        Call<ResponseBody> call = appAPI.addFood(food, MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void updateFood(Callback<ResponseBody> callback, Food food) {
        Call<ResponseBody> call = appAPI.updateFood(food.getId(),food.getName(),String.valueOf(food.getPrice()),food.getUrlfood(),food.getDiscount(),MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void deleteFood(Callback<ResponseBody> callback, int id) {
        Call<ResponseBody> call = appAPI.deleteFood(id,MainActivity.auth);
        call.enqueue(callback);
    }
}
