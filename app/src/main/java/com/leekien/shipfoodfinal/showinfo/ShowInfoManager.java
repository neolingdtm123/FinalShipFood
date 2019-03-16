package com.leekien.shipfoodfinal.showinfo;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.List;

public class ShowInfoManager {
    public interface View{
        void directShop(List<String> latLngs);
    }
    public interface Presenter{
        void getInfo(String lat,String lon);
    }
    public interface Interactor{
      void  getListStep(String lat, String lon, onPostSuccess onPostSuccess);
    }
}
