package com.leekien.shipfoodfinal.successorder;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SuccessOrderInteractor implements SuccessOrderManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getListOrder(Callback<List<Order>> callback) {
//        Call<List<Order>> call = appAPI.getSuccessOrder();
//        call.enqueue(callback);
    }
}
