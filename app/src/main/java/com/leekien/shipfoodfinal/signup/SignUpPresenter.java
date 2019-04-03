package com.leekien.shipfoodfinal.signup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;

import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter implements SignUpManager.Presenter {
    SignUpManager.View view;
    SignUpManager.Interactor interactor;
    boolean check = false;
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
    public void validate(String date, String location, final String userName, String pass, String confirmPass, String name, String phone) {
        check = false;
        if(CommonActivity.isNullOrEmpty(date)|| CommonActivity.isNullOrEmpty(userName)||CommonActivity.isNullOrEmpty(pass)
        ||CommonActivity.isNullOrEmpty(confirmPass)||CommonActivity.isNullOrEmpty(location)||CommonActivity.isNullOrEmpty(name)
        ||CommonActivity.isNullOrEmpty(phone)){
            view.validate("1");
        }
        else if(!pass.equals( confirmPass)){
            view.validate("2");
        }
        else {
            Callback<List<String>> callback = new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    for(String s : response.body()){
                        if(s.equals(userName)){
                            check = true;
                        }
                    }
                    if(!check){
                        view.validate("0");
                    }
                    else {
                        view.validate("3");
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {

                }
            };
            interactor.getUserName(callback);
        }
    }

    @Override
    public boolean getData(User user) {
        Callback<ResponseBody>  callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    view.showSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.add(user,callback);
        return false;
    }


}
