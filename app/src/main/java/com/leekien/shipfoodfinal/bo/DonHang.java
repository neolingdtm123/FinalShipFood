package com.leekien.shipfoodfinal.bo;

import java.io.Serializable;

public class DonHang implements Serializable {
    String id;
    String location;
    String price;
    String cusName;
    String numberPhone;
    String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public DonHang(String id, String location, String price, String cusName, String numberPhone, String distance) {
        this.id = id;
        this.location = location;
        this.price = price;
        this.cusName = cusName;
        this.numberPhone = numberPhone;
        this.distance = distance;
    }
}
