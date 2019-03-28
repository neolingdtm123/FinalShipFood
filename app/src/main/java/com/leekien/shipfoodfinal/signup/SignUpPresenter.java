package com.leekien.shipfoodfinal.signup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;

import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter implements SignUpManager.Presenter {
    SignUpManager.View view;
    SignUpManager.Interactor interactor;
    public SignUpPresenter(SignUpManager.View view) {
        this.view = view;
        this.interactor = new SignUpInteractor();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @Override
    public void validate(String date, String location, boolean check, String userName, String pass, String confirmPass) {

        if(CommonActivity.isNullOrEmpty(date)|| CommonActivity.isNullOrEmpty(userName)||CommonActivity.isNullOrEmpty(pass)
        ||CommonActivity.isNullOrEmpty(confirmPass)||CommonActivity.isNullOrEmpty(location)||!check){
            view.validate("1");
        }
        else if(!pass.equals( confirmPass)){
            view.validate("2");
        }
        else {
            view.validate("0");
        }
    }

    @Override
    public void getData() {
//        Callback<List<Comment>>  callback = new Callback<List<Comment>>() {
//            @Override
//            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
//                List<Comment> list = response.body();
//                view.getData(list);
//            }
//
//            @Override
//            public void onFailure(Call<List<Comment>> call, Throwable t) {
//
//            }
//        };
//        interactor.show(callback);
        Comment comment = new Comment(2,3,"kiên đẹp trai");
        interactor.add(comment);
    }


}
