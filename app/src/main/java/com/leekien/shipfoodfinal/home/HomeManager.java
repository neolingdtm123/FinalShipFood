package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;

import java.util.List;

import retrofit2.Callback;

public class HomeManager {
    interface View{
        void showFood(List<Food> list,FoodAdapter.onReturn onReturn,int position);
        void showTypeFood(List<TypeFood> list, TypeFoodAdapter.onReturn onReturn,FoodAdapter.onReturn onReturn1,FoodAdapter.onEdit onEdit,int position);
        void nextFragment(Food food );
        void edit(Food food );
        void upLoadImage();
        void checkFragment(Order order);
        void initSpinner(List<User> list);
    }
    interface Presenter{
        void getFood(User user,int position);
        void getListShop();
        void getFoodShop(User user,int position);
        void getWaitOrder(int iduser);

    }
    interface Interactor{
        void getFood(Callback<List<TypeFood>> callback);
        void getFoodShop(Callback<List<TypeFood>> callback,int idshop);
        void getWaitOrder(Callback<Order> callback,int iduser);
        void getListShop(Callback<List<User>> callback);
    }
}
