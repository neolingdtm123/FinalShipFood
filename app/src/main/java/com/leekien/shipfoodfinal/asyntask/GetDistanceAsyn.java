package com.leekien.shipfoodfinal.asyntask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.Map;
import com.leekien.shipfoodfinal.bo.onPostDistance;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.ArrayList;
import java.util.List;

public class GetDistanceAsyn extends AsyncTask<Void,Void,String> {
    String lat;
    String lon;
    String lat1;
    String lon1;
    List<LatLng> listStep = new ArrayList<>();
    com.leekien.shipfoodfinal.bo.onPostSuccess onPostSuccess ;
    com.leekien.shipfoodfinal.bo.onPostDistance onPostDistance;
    public GetDistanceAsyn (String lat, String lon,String lat1,String lon1,onPostDistance onPostDistance) {
        this.lat = lat;
        this.lon = lon;
        this.onPostDistance = onPostDistance;
        this.lat1= lat1;
        this.lon1= lon1;
    }

    @Override
    protected String  doInBackground(Void... Void) {
        String request = makeURL(lat,lon
                ,lat1,lon1);
        GetDirectionsTask task1 = new GetDirectionsTask(request);
        Map map = task1.testDirection();
//                if(list == null){
//                    return null;
//                }

        return map.getDistance();
    }

    @Override
    protected void onPostExecute(String distance) {
        super.onPostExecute(distance);
        onPostDistance.onPost(distance);
    }


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
        urlString.append("&mode=");
        urlString.append("driving");
        urlString.append("&key="+"AIzaSyBH8DVS13wnxPjs-MxPpwpq73WFxGMASRw");
//        urlString.append("&sensor=true");
        return urlString.toString();
    }
}
