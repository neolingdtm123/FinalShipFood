package com.leekien.shipfoodfinal.signup;

import android.content.Context;

import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class SignUpManager {
    public interface View{
        void validate(String type);
        void showTime(String date);
        void showSuccess();

    }
    public interface Presenter{
        void showTime(Context context);
        void validate(String date,String location,String userName,String pass,String confirmPass,String name,String phone);
        boolean getData(User user);
    }
    public interface Interactor{
       void add(User user, Callback<ResponseBody> callback);
       void getUserName(Callback<List<String>> callback);
    }
}
