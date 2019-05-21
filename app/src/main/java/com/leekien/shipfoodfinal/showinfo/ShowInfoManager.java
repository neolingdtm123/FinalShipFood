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

        void replace(Order order);

        void end();
    }

    public interface Presenter {
        void getInfo(String lat, String lon, String lat1, String lon1);

        void accept(Order order);

        void updatEnd(Order order);

        void send(int id);
    }

    public interface Interactor {
        void getListStep(String lat, String lon, String shoplat, String shoplon, onPostSuccess onPostSuccess);

        void updateOrder(Order order, Callback<ResponseBody> callback);

        void updateEnd(Order order, Callback<ResponseBody> callback);

        void send(int id, Callback<ResponseBody> callback);

    }
}
