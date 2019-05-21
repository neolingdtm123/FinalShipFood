package com.leekien.shipfoodfinal.showinfo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.asyntask.GetDirectionAsynTask;
import com.leekien.shipfoodfinal.bo.AppAPI;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ShowInfoInteractor  implements ShowInfoManager.Interactor{
    AppAPI appAPI = NetworkController.getInfoService();


    @Override
    public void getListStep(String lat, String lon,String shoplat,String shoplon, onPostSuccess onPostSuccess) {
        GetDirectionAsynTask getDirectionAsynTask = new GetDirectionAsynTask(lat,lon,shoplat,shoplon,onPostSuccess);
        getDirectionAsynTask.execute();
    }

    @Override
    public void updateOrder(Order order, Callback<ResponseBody> callback) {
        int a = order.getId();
        Call<ResponseBody> call = appAPI.updateOrder(a,order.getType(),order.getShiphour(),MainActivity.user.getId(),order.getShiptime(),MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void send(int id, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = appAPI.push(id,"Nhận hàng",MainActivity.auth);
        call.enqueue(callback);
    }

    @Override
    public void updateEnd(Order order, Callback<ResponseBody> callback) {
        int a = order.getId();
        Call<ResponseBody> call = appAPI.updateEnd(a,order.getType(),order.getEndhour(),order.getEndtime(),MainActivity.auth);
        call.enqueue(callback);
    }


}
