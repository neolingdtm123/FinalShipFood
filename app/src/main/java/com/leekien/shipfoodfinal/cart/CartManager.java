package com.leekien.shipfoodfinal.cart;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.adapter.CartAdapter;
import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.onPostDistance;

import java.util.List;

public class CartManager {
    interface View{
        void showDistance(String distance);
        void showRemoveList(CartAdapter.onReturn onReturn,int position);
        void showList(CartAdapter.onReturn onReturn);
    }
    interface Presenter{
        void getDistance(LatLng mCurrentLocation);
        void showList();
    }
    interface Interactor{
        void getDistance(String lat, String lon, String lat1, String lon1, onPostDistance onPostDistance);
    }
}
