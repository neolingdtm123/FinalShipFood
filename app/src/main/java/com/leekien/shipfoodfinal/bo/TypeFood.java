package com.leekien.shipfoodfinal.bo;

import java.util.List;

public class TypeFood {
    int idTypeFood;
    String title;
    String urlType;
    boolean check = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public TypeFood(String title, List<Food> foodList) {
        this.title = title;
        this.foodList = foodList;
    }

    public TypeFood(String title, String urlType, List<Food> foodList) {
        this.title = title;
        this.urlType = urlType;
        this.foodList = foodList;
    }

    List<Food> foodList;

    public int getIdTypeFood() {
        return idTypeFood;
    }

    public void setIdTypeFood(int idTypeFood) {
        this.idTypeFood = idTypeFood;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
}
