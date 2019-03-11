package com.leekien.shipfoodfinal.signup;

import android.content.Context;

import com.leekien.shipfoodfinal.bo.User;

public class SignUpManager {
    public interface View{
        void validate(String type);
        void showTime(String date);
    }
    public interface Presenter{
        void showTime(Context context);
        void validate(String date,String location,boolean check,String userName,String pass,String confirmPass);
    }
    public interface Interactor{

    }
}
