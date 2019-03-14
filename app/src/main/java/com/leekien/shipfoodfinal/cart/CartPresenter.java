package com.leekien.shipfoodfinal.cart;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.onPostDistance;

public class CartPresenter implements CartManager.Presenter {
    CartManager.View view;
    CartManager.Interactor interactor;

    @Override
    public void getDistance(LatLng mCurrentLocation) {
        interactor.getDistance(mCurrentLocation.latitude + "", mCurrentLocation.longitude+"", "20.997733", "105.841280", new onPostDistance() {
            @Override
            public void onPost(String result) {

            }
        });
    }

    public CartPresenter(CartManager.View view) {
        this.view = view;
        this.interactor = new CartInteractor();
    }
}
