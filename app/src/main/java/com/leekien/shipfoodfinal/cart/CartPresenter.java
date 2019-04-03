package com.leekien.shipfoodfinal.cart;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.adapter.CartAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Foodorder;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.onPostDistance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartManager.Presenter, CartAdapter.onReturn {
    CartManager.View view;

    CartManager.Interactor interactor;

    @Override
    public void getDistance(LatLng mCurrentLocation) {
        interactor.getDistance(mCurrentLocation.latitude + "", mCurrentLocation.longitude+"", "20.997733", "105.841280", new onPostDistance() {
            @Override
            public void onPost(String result) {
                view.showDistance(result);
            }
        });
    }

    @Override
    public void showList() {
        view.showList(this);
    }

    @Override
    public void newOrder(Order order, List<Food> list) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.newOrder(order,list,callback);
    }
    @Override
    public void newFoodOrder(Foodorder foodorder) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.newFoodOrder(foodorder,callback);
    }

    public CartPresenter(CartManager.View view) {
        this.view = view;
        this.interactor = new CartInteractor();
    }

    @Override
    public void onReturn(Food food,int position) {
        view.showRemoveList(this,position);
    }
}
