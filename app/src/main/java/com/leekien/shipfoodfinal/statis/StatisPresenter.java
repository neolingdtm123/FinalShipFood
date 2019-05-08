package com.leekien.shipfoodfinal.statis;

import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisPresenter implements StatisManager.Presenter {
    StatisManager.View view;
    StatisManager.Interactor interactor;

    public StatisPresenter(StatisManager.View view) {
        this.view = view;
        interactor = new StatisInteractor();
    }

    @Override
    public void getSuccessOrder() {
        Callback<List<Order>> callback = new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    view.showSuccessOrder(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        };
        interactor.getSuccessOrder(callback);
    }
}
