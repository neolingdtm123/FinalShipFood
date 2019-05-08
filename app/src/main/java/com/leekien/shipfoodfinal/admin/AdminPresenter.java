package com.leekien.shipfoodfinal.admin;

import com.leekien.shipfoodfinal.adapter.AdminAdapter;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPresenter implements AdminManager.Presenter, AdminAdapter.onReturn {
    AdminManager.View view;
    AdminManager.Interactor interactor;

    public AdminPresenter(AdminManager.View view) {
        this.view = view;
        interactor = new AdminInteractor();
    }

    @Override
    public void getShip() {
        Callback<List<User>> callback = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    view.getShip(response.body(),AdminPresenter.this);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        };
        interactor.getShip(callback);
    }

    @Override
    public void getShop() {
        Callback<List<User>> callback = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    view.getShop(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        };
        interactor.getShop(callback);
    }

    @Override
    public void deleteUser(int id, final String check) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    view.success(check);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.deleteUser(id,callback);
    }

    @Override
    public void onReturn(User user, String check) {
        view.callback(user,check);
    }
}
