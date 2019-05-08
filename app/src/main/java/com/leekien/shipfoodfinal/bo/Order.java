package com.leekien.shipfoodfinal.bo;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    Integer id;
    String type;
    String createtime;
    String createhour;
    String currentlon;
    String currentlat;
    String price;

    public String getPricefood() {
        return pricefood;
    }

    public void setPricefood(String pricefood) {
        this.pricefood = pricefood;
    }

    String pricefood;
    String distance;
    String address;
    String addressship;

    public String getAddressship() {
        return addressship;
    }

    public void setAddressship(String addressship) {
        this.addressship = addressship;
    }

    public String getShopAdress() {
        return shopAdress;
    }

    public void setShopAdress(String shopAdress) {
        this.shopAdress = shopAdress;
    }

    String shopAdress;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCurrentlon() {
        return currentlon;
    }

    public void setCurrentlon(String currentlon) {
        this.currentlon = currentlon;
    }

    public String getCurrentlat() {
        return currentlat;
    }

    public void setCurrentlat(String currentlat) {
        this.currentlat = currentlat;
    }

    public String getCreatehour() {
        return createhour;
    }

    public void setCreatehour(String createhour) {
        this.createhour = createhour;
    }

    String endtime;
    String shiptime;
    String shiphour;
    String endhour;
    String contentcancel;

    public String getContentcancel() {
        return contentcancel;
    }

    public void setContentcancel(String contentcancel) {
        this.contentcancel = contentcancel;
    }

    public String getShiphour() {
        return shiphour;
    }

    public void setShiphour(String shiphour) {
        this.shiphour = shiphour;
    }

    public String getEndhour() {
        return endhour;
    }

    public void setEndhour(String endhour) {
        this.endhour = endhour;
    }

    public String getShiptime() {
        return shiptime;
    }

    public void setShiptime(String shiptime) {
        this.shiptime = shiptime;
    }

    List<Food> foodList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    List<User> userList;

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
}
