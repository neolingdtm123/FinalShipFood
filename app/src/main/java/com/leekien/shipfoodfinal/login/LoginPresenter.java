package com.leekien.shipfoodfinal.login;

import com.leekien.shipfoodfinal.bo.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void getInfo(final String username, final String password) {
        Callback<List<User>> callback = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for(User user:response.body()){
                    if(user.getUsername().equals(username)&& (user.getPassword().equals(password))){
                        view.showInfoLogin("0",user);
                    }
                    else {
                        view.showInfoLogin("1",null);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        };

        interactor.getInfo(callback);

    }
}
