package com.leekien.shipfoodfinal.statusorder;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.common.CommonActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class StatusOrderInteractor implements StatusOrderManager.Interactor {
    AppAPI appAPI = NetworkController.getInfoService();
    @Override
    public void getOrder(Callback<Order> callback, int idOrder) {
        Call<Order> call = appAPI.getOrder(idOrder, MainActivity.auth);
        call.enqueue(callback);
    }
    @Override
    public void deleteOrder(Callback<ResponseBody> callback, Order order) {
        String content;
        if(CommonActivity.isNullOrEmpty(order.getContentcancel())){
            content ="";
        }
        else {
            content=order.getContentcancel();
        }
        Call<ResponseBody> call = appAPI.updateOrderSuccess(order.getId(),order.getType(),content,MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void updateUser(Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.updatePoint(MainActivity.user.getId(),MainActivity.point, MainActivity.auth);
        call.enqueue(callback);
    }
}
