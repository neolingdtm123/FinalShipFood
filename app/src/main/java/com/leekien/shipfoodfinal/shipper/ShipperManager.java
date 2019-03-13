package com.leekien.shipfoodfinal.shipper;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

public class ShipperManager {
    public interface View{
        void showListDonHang(List<DonHang> list, DonHangAdapter.onReturn onReturn);
        void showLocation(String location);
        void replace(DonHang donHang);
    }
    public interface Presenter{
        void showListDonHang();
        void getLocation(String location);
    }
    public interface Interactor{

    }
}
