package com.leekien.shipfoodfinal.asyntask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.ArrayList;
import java.util.List;

public class GetDirectionAsynTask extends AsyncTask<Void,Void,List<LatLng>> {
    String lat;
    String lon;
    String lat1;
    String lon1;
    List<LatLng> listStep = new ArrayList<>();
    onPostSuccess onPostSuccess ;
    public GetDirectionAsynTask(String lat, String lon,String lat1,String lon1,onPostSuccess onPostSuccess) {
        this.lat = lat;
        this.lon = lon;
        this.onPostSuccess = onPostSuccess;
        this.lat1= lat1;
        this.lon1= lon1;
    }

    @Override
    protected List<LatLng>  doInBackground(Void... Void) {
        String request = makeURL(lat,lon
                ,lat1,lon1);
        GetDirectionsTask task1 = new GetDirectionsTask(request);
        ArrayList<LatLng> list = task1.testDirection();
//                if(list == null){
//                    return null;
//                }
        for (LatLng latLng : list) {
            listStep.add(latLng);
        }
        return listStep;
    }

    @Override
    protected void onPostExecute(List<LatLng> latLngs) {
        super.onPostExecute(latLngs);
        onPostSuccess.onPost(latLngs);
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
        urlString.append("&key="+"AIzaSyBH8DVS13wnxPjs-MxPpwpq73WFxGMASRw");
//        urlString.append("&sensor=true");
        return urlString.toString();
    }
}
