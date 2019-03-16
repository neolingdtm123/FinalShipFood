package com.leekien.shipfoodfinal.bo;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Map {
    public Map(String distance, ArrayList<String> points) {
        this.distance = distance;
        this.points = points;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ArrayList<String> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<String> points) {
        this.points = points;
    }

    String distance;
    ArrayList<String> points;


}
