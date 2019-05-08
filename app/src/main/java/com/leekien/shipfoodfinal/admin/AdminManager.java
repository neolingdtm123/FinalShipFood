package com.leekien.shipfoodfinal.admin;

import com.leekien.shipfoodfinal.adapter.AdminAdapter;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class AdminManager {
    public interface View{
        void getShip(List<User> list, AdminAdapter.onReturn onReturn);
        void getShop(List<User> list);
        void callback(User user,String check);
        void success(String check);
    }
    public interface Presenter{
        void getShip();
        void getShop();
        void deleteUser(int id,String check);
    }
    public interface Interactor{
        void getShip(Callback<List<User>> callback);
        void getShop(Callback<List<User>> callback);
        void deleteUser(int id,Callback<ResponseBody> callback);
    }
}
