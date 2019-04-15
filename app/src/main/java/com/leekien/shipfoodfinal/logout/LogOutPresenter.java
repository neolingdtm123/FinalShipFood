package com.leekien.shipfoodfinal.logout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.leekien.shipfoodfinal.bo.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogOutPresenter implements LogOutManager.Presenter {
    LogOutManager.View view;
    LogOutManager.Interactor interactor;

    public LogOutPresenter(LogOutManager.View view) {
        this.view = view;
        this.interactor = new LogOutInteractor();
    }

    @Override
    public void updateInfo(final User users) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    view.updateInfo();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.updateInfo(users,callback);
    }

    @Override
    public void showTime(Context context) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2 + "/" + (i1+1 )
                        + "/" + i;
                view.showTime(date);
            }
        },1990,1,1);
        datePickerDialog.show();
    }
}
