package com.leekien.shipfoodfinal.shipper;

import android.content.Context;

import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.signup.SignUpManager;

import java.util.ArrayList;
import java.util.List;

public class ShipperPresenter implements ShipperManager.Presenter, DonHangAdapter.onReturn {
    ShipperManager.View view;
    ShipperManager.Interactor interactor;

    public ShipperPresenter(ShipperManager.View view) {
        this.view = view;
        this.interactor = new ShipperInteractor();
    }

    @Override
    public void showListDonHang() {
        List<DonHang> list = new ArrayList<>();
        //fix cung data don hang
        DonHang donHang1 = new DonHang("12345","Đại học bách khoa","35000","Nguyễn Khắc Kiên"
        ,"0962188659","8.4");
        DonHang donHang2 = new DonHang("12345","Đại học thủy lợi","35000","Nguyễn Khắc Kiên"
                ,"0962188659","8.4");
        DonHang donHang3 = new DonHang("12345","Học viện tài chính","35000","Nguyễn Khắc Kiên"
                ,"0962188659","8.4");
        list.add(donHang1);
        list.add(donHang2);
        list.add(donHang3);
        view.showListDonHang(list,this);
    }

    @Override
    public void getLocation(String location) {

    }

    @Override
    public void onReturn(DonHang donHang, int groupPosition) {
        view.showLocation(donHang.getLocation());
    }

    @Override
    public void onReplace(DonHang donHang, int groupPosition) {
        view.replace(donHang);
    }
}
