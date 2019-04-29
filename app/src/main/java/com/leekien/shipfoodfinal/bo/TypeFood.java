package com.leekien.shipfoodfinal.bo;

import java.util.List;

public class TypeFood {
    int idTypeFood;
    String name;
    String urlType;
    boolean check = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public TypeFood(String title, List<Food> foodList,int id) {
        this.name = title;
        this.foodList = foodList;
        this.idTypeFood = id;
    }

    public TypeFood(int idTypeFood,String title, String urlType, List<Food> foodList) {
        this.name = title;
        this.urlType = urlType;
        this.foodList = foodList;
        this.idTypeFood = idTypeFood;
    }

    List<Food> foodList;

    public int getIdTypeFood() {
        return idTypeFood;
    }

    public void setIdTypeFood(int idTypeFood) {
        this.idTypeFood = idTypeFood;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
