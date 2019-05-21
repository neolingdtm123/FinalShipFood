package com.leekien.shipfoodfinal.cart;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.asyntask.GetDirectionAsynTask;
import com.leekien.shipfoodfinal.asyntask.GetDistanceAsyn;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Foodorder;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.onPostDistance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CartInteractor implements CartManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();

    @Override
    public void newOrder(Order order, List<Food> list, Callback<Integer> callback) {
        Call<Integer> call = appAPI.addOrder(order,MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void newFoodOrder(Foodorder foodorder, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.addFoodOrder(foodorder,MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void send(Callback<ResponseBody> callback,int id) {
        Call<ResponseBody> call = appAPI.push(id, "Đặt hàng",MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void getDistance(String lat, String lon, String lat1, String lon1, onPostDistance onPostDistance) {
        GetDistanceAsyn getDirectionAsynTask = new GetDistanceAsyn(lat,lon,lat1,lon1,onPostDistance);
        getDirectionAsynTask.execute();
    }
}
