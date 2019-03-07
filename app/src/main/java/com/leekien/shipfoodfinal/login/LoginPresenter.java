package com.leekien.shipfoodfinal.login;

import com.leekien.shipfoodfinal.bo.User;

import java.util.ArrayList;
import java.util.List;

public class LoginPresenter implements LoginManager.Presenter{
    private LoginManager.View view;
    private LoginManager.Interactor interactor;
    //fix cứng data
    List<String> list= new ArrayList<String>();
    public LoginPresenter(LoginManager.View view) {
        this.view = view;
        interactor = new LoginInteractor();
    }

    @Override
    public void getInfo(String username, String password) {
        list.add("ship");
        list.add("chu");
        list.add("khach");
        User user = new User();
        if(list.contains(username)){
            if("ship".equals(username)){
                user.setType("ship");
            }
            else if("chu".equals(username)){
                user.setType("chu");
            }
            else if ("khach".equals(username)){
                user.setType("khach");
            }
            view.showInfoLogin("0",user);
        }
        else {
            view.showInfoLogin("1",null);
        }
    }
}
