package com.leekien.shipfoodfinal.bo;

import java.io.Serializable;

public class Food implements Serializable {
    int id;
    int idTypeFood;
    int idshop;

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
    String nameshop;

    public String getNameshop() {
        return nameshop;
    }

    public void setNameshop(String nameshop) {
        this.nameshop = nameshop;
    }

    String name;
    String content;
    String urlfood;
    int price;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    String discount;
    public Food() {
    }

    public String getNumberDat() {
        return numberDat;
    }

    public void setNumberDat(String numberDat) {
        this.numberDat = numberDat;
    }

    String numberDat;
    String priceDat;

    public String getPriceDat() {
        return priceDat;
    }

    public void setPriceDat(String priceDat) {
        this.priceDat = priceDat;
    }

    public Food(String name, String urlfood, int price, int idTypeFood, int id) {
        this.name = name;
        this.urlfood = urlfood;
        this.price = price;
        this.idTypeFood = idTypeFood;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTypeFood() {
        return idTypeFood;
    }

    public void setIdTypeFood(int idTypeFood) {
        this.idTypeFood = idTypeFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlfood() {
        return urlfood;
    }

    public void setUrlfood(String urlfood) {
        this.urlfood = urlfood;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    int number;

}
