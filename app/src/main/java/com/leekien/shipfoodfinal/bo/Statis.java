package com.leekien.shipfoodfinal.bo;

public class Statis {
    String month;
    String gia;

    public Statis(String month, String gia) {
        this.month = month;
        this.gia = gia;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
