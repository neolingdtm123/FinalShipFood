package com.leekien.shipfoodfinal.shipper;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;


public class ShipperManager {
    public interface View {
        void showListDonHang(List<Order> list, DonHangAdapter.onReturn onReturn);

        void directShip(Order order,String shipAddress,String shopAddress);

        void replace(Order order);
    }

    public interface Presenter {
        void showListDonHang();

        void getLocation(int id,Double lat, Double lon);
    }

    public interface Interactor {
        void getListOrder(Callback<List<Order>> callback);
        void getLocation(int id,Double lat, Double lon, Callback<ResponseBody> callback);
    }
}
