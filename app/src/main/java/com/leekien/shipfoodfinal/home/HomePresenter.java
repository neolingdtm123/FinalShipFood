package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeManager.Presenter, TypeFoodAdapter.onReturn, FoodAdapter.onReturn {
    HomeManager.View view;
    HomeManager.Interactor interactor;
    String type;

    public HomePresenter(HomeManager.View view) {
        this.view = view;
        this.interactor = new HomeInteractor();
    }

    @Override
    public void getFood(final User user, final int position) {
        type = user.getType();
        Callback<List<TypeFood>> callback = new Callback<List<TypeFood>>() {
            @Override
            public void onResponse(Call<List<TypeFood>> call, Response<List<TypeFood>> response) {
                List<TypeFood> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setCheck(true);
                    } else {
                        list.get(i).setCheck(false);
                    }

                }
                if ("admin".equals(user.getType())) {
                    Food food = new Food();
                    food.setName("Thêm mới");
                    food.setUrlfood("https://firebasestorage.googleapis.com/v0/b/" +
                            "finalshipfood.appspot.com/o/Group%202KIENNK.png?alt=media&token=256593b6-5266-4994-a4f2-830e0c4be810");
                    for (TypeFood typeFood : list) {
                        List<Food> list1 = new ArrayList<>();
                        list1.addAll(typeFood.getFoodList());
                        list1.add(food);
                        typeFood.setFoodList(list1);
                    }
                }
                view.showTypeFood(list, HomePresenter.this, HomePresenter.this, position);
            }

            @Override
            public void onFailure(Call<List<TypeFood>> call, Throwable t) {

            }
        };
        interactor.getFood(callback);


    }

    @Override
    public void getWaitOrder(int iduser) {
        Callback<Order> callback = new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    view.checkFragment(response.body());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                view.checkFragment(null);
            }
        };
        interactor.getWaitOrder(callback, iduser);

    }


    @Override
    public void onReturn(TypeFood typeFood, int groupPosition) {
        view.showFood(typeFood.getFoodList(), HomePresenter.this, groupPosition);
    }

    @Override
    public void onReturn(Food food, int groupPosition) {
        if ("Thêm mới".equals(food.getName())) {
            view.upLoadImage();
        } else {
            view.nextFragment(food);
        }

    }

}
