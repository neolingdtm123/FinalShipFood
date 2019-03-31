package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeManager.Presenter, TypeFoodAdapter.onReturn, FoodAdapter.onReturn,FoodAdapter.onImageReturn {
    HomeManager.View view;
    HomeManager.Interactor interactor;
    String type;

    public HomePresenter(HomeManager.View view) {
        this.view = view;
        this.interactor = new HomeInteractor();
    }

    @Override
    public void getFood(final User user) {
        type = user.getType();
        Callback<List<TypeFood>> callback = new Callback<List<TypeFood>>() {
            @Override
            public void onResponse(Call<List<TypeFood>> call, Response<List<TypeFood>> response) {
                List<TypeFood> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
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
                view.showTypeFood(list, HomePresenter.this, HomePresenter.this,HomePresenter.this);
            }

            @Override
            public void onFailure(Call<List<TypeFood>> call, Throwable t) {

            }
        };
        interactor.getFood(callback);


    }


    @Override
    public void onReturn(TypeFood typeFood, int groupPosition) {
        view.showFood(typeFood.getFoodList(), HomePresenter.this,HomePresenter.this,groupPosition);
    }

    @Override
    public void onReturn(Food food, int groupPosition) {
        view.nextFragment(food);
    }

    @Override
    public void onImageReturn(Food food, int groupPosition) {
        if("Thêm mới".equals(food.getName())){
            view.upLoadImage();
        }
    }
}
