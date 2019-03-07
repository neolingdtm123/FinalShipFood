package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;

import java.util.List;

public class HomeManager {
    interface View{
        void showFood(List<Food> list,FoodAdapter.onReturn onReturn);
        void showTypeFood(List<TypeFood> list, TypeFoodAdapter.onReturn onReturn,FoodAdapter.onReturn onReturn1);
        void nextFragment(Food food );
    }
    interface Presenter{
        void getFood();

    }
    interface Interactor{

    }
}
