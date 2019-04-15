package com.leekien.shipfoodfinal.successorder;

import com.leekien.shipfoodfinal.bo.Order;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;


public class SuccessOrderManager {
    public interface View {
        void showListOrder(List<Order> list);
    }

    public interface Presenter {
        void getListOrder();
    }

    public interface Interactor {
        void getListOrder(Callback<List<Order>> callback);
    }
}
