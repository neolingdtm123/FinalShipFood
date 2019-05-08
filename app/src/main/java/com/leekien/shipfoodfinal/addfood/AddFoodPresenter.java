package com.leekien.shipfoodfinal.addfood;

import android.net.Uri;

import com.leekien.shipfoodfinal.bo.Food;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodPresenter implements AddFoodManager.Presenter {
    AddFoodManager.View view;
    AddFoodManager.Interactor interactor;
    String type;

    public AddFoodPresenter(AddFoodManager.View view) {
        this.view = view;
        this.interactor = new AddFoodInteractor();
    }


    @Override
    public void addFood(Food food) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    view.showSuccess("0");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.addFood(callback,food);
    }

    @Override
    public void updateFood(Food food) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    view.showSuccess("1");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.updateFood(callback,food);
    }

    @Override
    public void deleteFood(int id) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    view.showSuccess("2");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.deleteFood(callback,id);
    }
}
