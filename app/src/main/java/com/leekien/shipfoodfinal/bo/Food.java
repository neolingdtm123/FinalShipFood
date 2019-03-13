package com.leekien.shipfoodfinal.bo;

import java.io.Serializable;

public class Food implements Serializable {
    int idFood;
    int idTypeFood;
    String name;
    String content;
    String urlFood;
    int price;

    public String getNumberDat() {
        return numberDat;
    }

    public void setNumberDat(String numberDat) {
        this.numberDat = numberDat;
    }

    String numberDat;
    public Food(String name, String urlFood, int price) {
        this.name = name;
        this.urlFood = urlFood;
        this.price = price;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
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

    public String getUrlFood() {
        return urlFood;
    }

    public void setUrlFood(String urlFood) {
        this.urlFood = urlFood;
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

    public int getNumberBuy() {
        return numberBuy;
    }

    public void setNumberBuy(int numberBuy) {
        this.numberBuy = numberBuy;
    }

    int number;
    int numberBuy;
}
