package com.leekien.shipfoodfinal.bo;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Map {
    public Map(String distance, ArrayList<LatLng> latLngs) {
        this.distance = distance;
        this.latLngs = latLngs;
    }

    String distance;
    ArrayList<LatLng> latLngs;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ArrayList<LatLng> getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(ArrayList<LatLng> latLngs) {
        this.latLngs = latLngs;
    }
}
