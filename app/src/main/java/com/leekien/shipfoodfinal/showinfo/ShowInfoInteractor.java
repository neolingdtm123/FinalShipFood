package com.leekien.shipfoodfinal.showinfo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.asyntask.GetDirectionAsynTask;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.ArrayList;
import java.util.List;

public class ShowInfoInteractor  implements ShowInfoManager.Interactor{
    private List<LatLng> listStep = new ArrayList<LatLng>();
    @SuppressLint("StaticFieldLeak")

    public String makeURL (String sourcelat, String sourcelng, String destlat, String destlng ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(sourcelat);
        urlString.append(",");
        urlString.append(sourcelng);
        urlString.append("&destination=");// to
        urlString.append(destlat);
        urlString.append(",");
        urlString.append(destlng);
        urlString.append("&key="+"AIzaSyBH8DVS13wnxPjs-MxPpwpq73WFxGMASRw");
//        urlString.append("&sensor=true");
        return urlString.toString();
    }

    @Override
    public void getListStep(String lat, String lon, onPostSuccess onPostSuccess) {
        GetDirectionAsynTask getDirectionAsynTask = new GetDirectionAsynTask(lat,lon,"20.997733","105.841280",onPostSuccess);
        getDirectionAsynTask.execute();
    }
}
