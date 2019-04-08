package com.leekien.shipfoodfinal.showinfo;

import com.google.android.gms.maps.model.LatLng;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.bo.onPostSuccess;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class ShowInfoManager {
    public interface View {
        void directShop(List<String> latLngs);
        void replace();
    }

    public interface Presenter {
        void getInfo(String lat, String lon);

        void accept(Order order);
    }

    public interface Interactor {
        void getListStep(String lat, String lon, onPostSuccess onPostSuccess);

        void updateOrder(Order order, Callback<ResponseBody> callback);
    }
}
