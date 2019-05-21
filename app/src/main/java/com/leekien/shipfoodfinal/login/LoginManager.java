package com.leekien.shipfoodfinal.login;

import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class LoginManager {
    public interface View{
        void showInfoLogin(String code, String auth);
        void replace(String code, User user);
    }
    public interface Presenter{
        void getInfo(String username,String password,String token,String auth);
        void login(String username,String password);
    }
    public interface Interactor{
        void getInfo(Callback<User> callback,String username,String password,String token,String auth);
        void login(Callback<ResponseBody> callback, String username, String password);
    }
}
