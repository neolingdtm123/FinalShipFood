package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import retrofit2.Callback;

public class HomeManager {
    interface View{
        void showFood(List<Food> list,FoodAdapter.onReturn onReturn,FoodAdapter.onImageReturn onImageReturn,int position);
        void showTypeFood(List<TypeFood> list, TypeFoodAdapter.onReturn onReturn,FoodAdapter.onReturn onReturn1,FoodAdapter.onImageReturn onImageReturn);
        void nextFragment(Food food );
        void upLoadImage();
    }
    interface Presenter{
        void getFood(User user);

    }
    interface Interactor{
        void getFood(Callback<List<TypeFood>> callback);
    }
}
