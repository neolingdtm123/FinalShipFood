package com.leekien.shipfoodfinal.login;

import com.leekien.shipfoodfinal.bo.User;

public class LoginManager {
    public interface View{
        void showInfoLogin(String code, User user);
    }
    public interface Presenter{
        void getInfo(String username,String password);
    }
    public interface Interactor{
        void getInfo();
    }
}
