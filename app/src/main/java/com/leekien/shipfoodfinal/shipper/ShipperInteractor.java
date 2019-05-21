package com.leekien.shipfoodfinal.shipper;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.signup.SignUpManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ShipperInteractor implements ShipperManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getListOrder(Callback<List<Order>> callback) {
        Call<List<Order>> call = appAPI.getAllOrder(MainActivity.user.getId(),MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void getLocation(int id, Double lat, Double lon, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.updateLocation(id,String.valueOf(lat),String.valueOf(lon),MainActivity.auth);
        call.enqueue(callback);
    }
}
