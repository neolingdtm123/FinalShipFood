package com.leekien.shipfoodfinal.shipper;

import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.signup.SignUpManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ShipperInteractor implements ShipperManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getListOrder(Callback<List<Order>> callback) {
        Call<List<Order>> call = appAPI.getAllOrder();
        call.enqueue(callback);
    }
}
