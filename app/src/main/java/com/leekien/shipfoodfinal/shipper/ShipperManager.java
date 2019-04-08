package com.leekien.shipfoodfinal.shipper;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import retrofit2.Callback;


public class ShipperManager {
    public interface View {
        void showListDonHang(List<Order> list, DonHangAdapter.onReturn onReturn);

        void directShip(String lat, String lon);

        void replace(Order order);
    }

    public interface Presenter {
        void showListDonHang();

        void getLocation(String location);
    }

    public interface Interactor {
        void getListOrder(Callback<List<Order>> callback);
    }
}
