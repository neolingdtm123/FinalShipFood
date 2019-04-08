package com.leekien.shipfoodfinal.statusorder;

import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.StatusOrder;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusOrderPresenter implements StatusOrderManager.Presenter {
    StatusOrderManager.View view;
    StatusOrderManager.Interactor interactor;

    public StatusOrderPresenter(StatusOrderManager.View view) {
        this.view = view;
        this.interactor = new StatusOrderInteractor();
    }

    @Override
    public void getOrder(int idOrder) {
        Callback<Order> callback = new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                List<StatusOrder> list = new ArrayList<>();
                StatusOrder statusOrder1 = new StatusOrder();
                StatusOrder statusOrder2 = new StatusOrder();
                StatusOrder statusOrder3 = new StatusOrder();
                statusOrder1.setCheck(true);
                statusOrder1.setStatus("Đặt hàng");
                statusOrder1.setTime(order.getCreatehour());
                statusOrder2.setStatus("Đã nhận hàng");
                statusOrder3.setStatus("Đã hoàn thành");

                if(!CommonActivity.isNullOrEmpty(order.getEndtime())){
                    statusOrder3.setTime(order.getEndhour());
                }
                if("Đã nhận hàng".equals(order.getType())){
                    if(!CommonActivity.isNullOrEmpty(order.getShiphour())){
                        statusOrder2.setTime(order.getShiphour());
                    }
                    statusOrder2.setCheck(true);
                    for(User user : order.getUserList()){
                        if("ship".equals(user.getType())){
                            statusOrder2.setShipinfo(user.getName());
                        }
                    }
                }
                else if("Đã hoàn thành".equals(order.getType())){
                    statusOrder3.setCheck(true);
                    statusOrder2.setCheck(true);
                }
                list.add(statusOrder1);
                list.add(statusOrder2);
                list.add(statusOrder3);
                view.showStatusOrder(list);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        };
        interactor.getOrder(callback,idOrder);
    }
}