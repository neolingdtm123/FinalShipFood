package com.leekien.shipfoodfinal.cart;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.adapter.CartAdapter;
import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Foodorder;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.onPostDistance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class CartManager {
    interface View{
        void showDistance(String distance);
        void showRemoveList(CartAdapter.onReturn onReturn,int position);
        void showList(CartAdapter.onReturn onReturn);
        void showSuccess(int id);
    }
    interface Presenter{
        void getDistance(String latUser, String lonUser,String lat,String lon);
        void showList();
        void newOrder(Order order, List<Food> list);
        void newFoodOrder(Foodorder foodorder);
        void send(int id);
    }
    interface Interactor{
        void getDistance(String lat, String lon, String lat1, String lon1, onPostDistance onPostDistance);
        void newOrder(Order order, List<Food> list, Callback<Integer> callback);
        void newFoodOrder(Foodorder foodorder, Callback<ResponseBody> callback);
        void send(Callback<ResponseBody> callback,int id);
    }
}
