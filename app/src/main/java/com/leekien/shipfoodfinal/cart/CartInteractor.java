package com.leekien.shipfoodfinal.cart;

import com.leekien.shipfoodfinal.asyntask.GetDirectionAsynTask;
import com.leekien.shipfoodfinal.asyntask.GetDistanceAsyn;
import com.leekien.shipfoodfinal.bo.onPostDistance;

public class CartInteractor implements CartManager.Interactor {
    @Override
    public void getDistance(String lat, String lon, String lat1, String lon1, onPostDistance onPostDistance) {
        GetDistanceAsyn getDirectionAsynTask = new GetDistanceAsyn(lat,lon,lat1,lon1,onPostDistance);
        getDirectionAsynTask.execute();
    }
}
