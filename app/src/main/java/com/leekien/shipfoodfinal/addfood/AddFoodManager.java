package com.leekien.shipfoodfinal.addfood;

import android.net.Uri;

import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class AddFoodManager {
    interface View{
        void showSuccess();
    }
    interface Presenter{
        void addFood(Food food);
    }
    interface Interactor{
        void addFood(Callback<ResponseBody> callback,Food food);
    }
}
