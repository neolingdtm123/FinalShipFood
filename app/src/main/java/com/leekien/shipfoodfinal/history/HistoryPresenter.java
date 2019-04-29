package com.leekien.shipfoodfinal.history;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.leekien.shipfoodfinal.bo.Order;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryPresenter implements HistoryManager.Presenter {
    HistoryManager.Interactor interactor;
    HistoryManager.View view;
    int year = 0;
    int month = 0;
    int day = 0;

    public HistoryPresenter(HistoryManager.View view) {
        this.view = view;
        interactor = new HistoryInteractor();
    }

    @Override
    public void getOrderCus() {
        Callback<List<Order>> callback = new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    view.showHistory(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        };
        interactor.getOrderCus(callback);
    }

    @Override
    public void showTime(Context context, String date, final String check) {
        String[] dateParts = date.split("/");
        day = Integer.valueOf(dateParts[0]);
        month = Integer.valueOf(dateParts[1]);
        year = Integer.valueOf(dateParts[2]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                try {
                    view.showTime(i2, i1+1 , i, check);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month-1, day);
        datePickerDialog.show();
    }
}
