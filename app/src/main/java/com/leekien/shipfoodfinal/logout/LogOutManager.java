package com.leekien.shipfoodfinal.logout;

import android.content.Context;

import com.leekien.shipfoodfinal.bo.User;


import okhttp3.ResponseBody;
import retrofit2.Callback;

public class LogOutManager {
    public interface View {
        void updateInfo();
        void showTime(String date);
    }

    public interface Presenter {
        void updateInfo(User users);
        void showTime(Context context);
    }

    public interface Interactor {
        void updateInfo(User users, Callback<ResponseBody> callback);
    }
}
