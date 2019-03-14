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
    @Override
    public void getListStep(String lat, String lon, onPostSuccess onPostSuccess) {
        GetDirectionAsynTask getDirectionAsynTask = new GetDirectionAsynTask(lat,lon,"20.997733","105.841280",onPostSuccess);
        getDirectionAsynTask.execute();
    }
}
