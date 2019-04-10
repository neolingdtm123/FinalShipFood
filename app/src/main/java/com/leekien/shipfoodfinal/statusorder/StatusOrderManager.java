package com.leekien.shipfoodfinal.statusorder;

import android.content.Context;

import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.StatusOrder;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class StatusOrderManager {
    public interface View {
        void showStatusOrder(List<StatusOrder> statusOrderList);
    }

    public interface Presenter {
        void getOrder(int idOrder);
    }

    public interface Interactor {
        void getOrder(Callback<Order> callback, int idOrder);
    }
}
