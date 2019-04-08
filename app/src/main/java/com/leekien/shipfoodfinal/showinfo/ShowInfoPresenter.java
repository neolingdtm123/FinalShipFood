package com.leekien.shipfoodfinal.showinfo;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowInfoPresenter implements ShowInfoManager.Presenter {
    ShowInfoManager.Interactor interactor;
    ShowInfoManager.View view;

    public ShowInfoPresenter(ShowInfoManager.View view) {
        this.view = view;
        this.interactor = new ShowInfoInteractor();
    }

    @Override
    public void getInfo(String lat, String lon) {
        interactor.getListStep(lat, lon, new onPostSuccess() {
            @Override
            public void onPost(List<String> result) {
                view.directShop(result);
            }
        });

    }

    @Override
    public void accept(Order order) {
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
        interactor.updateOrder(order,callback);
    }
}
