package com.leekien.shipfoodfinal.successorder;

import com.leekien.shipfoodfinal.bo.Order;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessOrderPresenter implements  SuccessOrderManager.Presenter {
    SuccessOrderManager.View view;

    public SuccessOrderPresenter(SuccessOrderManager.View view) {
        this.view = view;
        this.interactor = new SuccessOrderInteractor();
    }

    SuccessOrderManager.Interactor interactor;

    @Override
    public void getListOrder() {
        Callback<List<Order>> callback = new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                view.showListOrder(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        };
        interactor.getListOrder(callback);

    }
}
