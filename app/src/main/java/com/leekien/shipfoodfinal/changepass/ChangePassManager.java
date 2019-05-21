package com.leekien.shipfoodfinal.changepass;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class ChangePassManager {
    public interface View {
        void updateInfo();
    }

    public interface Presenter {
        void updateInfo(int id, String pass);
    }

    public interface Interactor {
        void updateInfo(int id,String  pass, Callback<ResponseBody> callback);
    }
}
