package com.leekien.shipfoodfinal.signup;

import android.content.Context;

import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import retrofit2.Callback;

public class SignUpManager {
    public interface View{
        void validate(String type);
        void showTime(String date);
        void getData(List<Comment> list);

    }
    public interface Presenter{
        void showTime(Context context);
        void validate(String date,String location,boolean check,String userName,String pass,String confirmPass);
        void getData();
    }
    public interface Interactor{
       void show(  Callback<List<Comment>> callback );
       void add(Comment comment);

    }
}
