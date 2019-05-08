package com.leekien.shipfoodfinal.statis;

import com.leekien.shipfoodfinal.bo.Order;

import java.util.List;

import retrofit2.Callback;

public class StatisManager {
    public interface View {
        void showSuccessOrder(List<Order> orderList);

    }

    public interface Presenter {
        void getSuccessOrder();
    }

    public interface Interactor {
        void getSuccessOrder(Callback<List<Order>> callback);
    }
}
