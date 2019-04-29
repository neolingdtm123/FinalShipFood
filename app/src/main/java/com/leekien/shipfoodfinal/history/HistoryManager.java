package com.leekien.shipfoodfinal.history;

import android.content.Context;

import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;

import java.text.ParseException;
import java.util.List;

import retrofit2.Callback;

public class HistoryManager {
    public interface View {
        void showHistory(List<Order> list);
        void showTime(int day,int month,int year,String check) throws ParseException;
    }

    public interface Presenter {
        void getOrderCus();
        void showTime(Context context,String fromDate,String check);
    }

    public interface Interactor {
        void getOrderCus( Callback<List<Order>> callback);
    }
}
