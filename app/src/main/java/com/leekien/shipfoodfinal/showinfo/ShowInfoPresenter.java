package com.leekien.shipfoodfinal.showinfo;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.List;

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
            public void onPost(List<LatLng> result) {
                view.directShop(result);
            }
        });

    }
}
