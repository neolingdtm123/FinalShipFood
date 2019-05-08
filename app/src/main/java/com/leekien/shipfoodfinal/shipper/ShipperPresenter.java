package com.leekien.shipfoodfinal.shipper;

import android.content.Context;

import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.signup.SignUpManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShipperPresenter implements ShipperManager.Presenter, DonHangAdapter.onReturn {
    ShipperManager.View view;
    ShipperManager.Interactor interactor;

    public ShipperPresenter(ShipperManager.View view) {
        this.view = view;
        this.interactor = new ShipperInteractor();
    }

    @Override
    public void showListDonHang() {
        Callback<List<Order>> callback = new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> list = new ArrayList<>();
                list = response.body();
                view.showListDonHang(list,ShipperPresenter.this);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        };
        interactor.getListOrder(callback);
    }

    @Override
    public void getLocation(int id,Double lat,Double lon) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.getLocation(id,lat,lon,callback);
    }

    @Override
    public void onReturn(Order order, int groupPosition) {
        view.directShip(order,order.getAddress(),order.getShopAdress());
    }

    @Override
    public void onReplace(Order order, int groupPosition) {
        view.replace(order);
    }
}
