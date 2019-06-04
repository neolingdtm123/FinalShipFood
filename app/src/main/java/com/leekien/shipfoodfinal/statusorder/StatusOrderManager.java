package com.leekien.shipfoodfinal.statusorder;

import android.content.Context;

import com.leekien.shipfoodfinal.adapter.StatusOrderAdapter;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.StatusOrder;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class StatusOrderManager {
    public interface View {
        void showStatusOrder(List<StatusOrder> statusOrderList, Order order, StatusOrderAdapter.onReturn onReturn);
        void cancelSuccess(String checkType);
        void call(String phone);
    }

    public interface Presenter {
        void getOrder(int idOrder);
        void deleteOrder(Order order,String checkType);
        void updateUser();
    }

    public interface Interactor {
        void getOrder(Callback<Order> callback, int idOrder);
        void deleteOrder(Callback<ResponseBody> callback,Order order);
        void updateUser(Callback<ResponseBody> callback);
    }
}
