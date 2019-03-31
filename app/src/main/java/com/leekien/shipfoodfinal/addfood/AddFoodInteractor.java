package com.leekien.shipfoodfinal.addfood;

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
        Call<ResponseBody> call = appAPI.addFood(food);
        call.enqueue(callback);
    }
}
