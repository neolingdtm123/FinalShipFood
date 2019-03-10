package com.leekien.shipfoodfinal.home;

import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeManager.Presenter, TypeFoodAdapter.onReturn , FoodAdapter.onReturn {
    HomeManager.View  view;
    HomeManager.Interactor interactor;

    public HomePresenter(HomeManager.View view) {
        this.view = view;
        this.interactor =  new HomeInteractor();
    }

    @Override
    public void getFood() {
        //fix cung data list food
        List<TypeFood> list = new ArrayList<>();
        List<Food> foodList1 = new ArrayList<>();
        List<Food> foodList2 = new ArrayList<>();
        List<Food> foodList3 = new ArrayList<>();
        Food food1 = new Food("Gà rán","https://media.foody.vn/res/g26/255855/prof/s1242x600/foody-mobile-13606765_17248147144-737-636040058754740101.jpg",25000);
        Food food2 = new Food("Gà rán","https://media.foody.vn/res/g26/255855/prof/s1242x600/foody-mobile-13606765_17248147144-737-636040058754740101.jpg",26000);
        Food food3 = new Food("Gà rán","https://media.foody.vn/res/g26/255855/prof/s1242x600/foody-mobile-13606765_17248147144-737-636040058754740101.jpg",27000);
        Food food4 = new Food("Gà rán","https://media.foody.vn/res/g26/255855/prof/s1242x600/foody-mobile-13606765_17248147144-737-636040058754740101.jpg",28000);
        Food food5 = new Food("Gà rán","https://media.foody.vn/res/g26/255855/prof/s1242x600/foody-mobile-13606765_17248147144-737-636040058754740101.jpg",29000);
        foodList1.add(food1);
        foodList1.add(food2);
        foodList1.add(food3);
        foodList1.add(food4);
        foodList1.add(food5);
        TypeFood typeFood1= new TypeFood("Đồ ăn",foodList1);
        Food foods1 = new Food("Trà sữa","https://images.foody.vn/res/g72/710938/prof/s480x300/foody-upload-api-foody-mobile-foody-mobile-heyt-jp-180521122514.jpg",30000);
        Food foods2 = new Food("Trà sữa","https://images.foody.vn/res/g72/710938/prof/s480x300/foody-upload-api-foody-mobile-foody-mobile-heyt-jp-180521122514.jpg",31000);
        Food foods3 = new Food("Trà sữa","https://images.foody.vn/res/g72/710938/prof/s480x300/foody-upload-api-foody-mobile-foody-mobile-heyt-jp-180521122514.jpg",32000);
        Food foods4 = new Food("Trà sữa","https://images.foody.vn/res/g72/710938/prof/s480x300/foody-upload-api-foody-mobile-foody-mobile-heyt-jp-180521122514.jpg",33000);
        Food foods5 = new Food("Trà sữa","https://images.foody.vn/res/g72/710938/prof/s480x300/foody-upload-api-foody-mobile-foody-mobile-heyt-jp-180521122514.jpg",34000);
        Food foods6 = new Food("Trà sữa","https://images.foody.vn/res/g72/710938/prof/s480x300/foody-upload-api-foody-mobile-foody-mobile-heyt-jp-180521122514.jpg",35000);
        foodList2.add(foods1);
        foodList2.add(foods2);
        foodList2.add(foods3);
        foodList2.add(foods4);
        foodList2.add(foods5);
        foodList2.add(foods6);
        TypeFood typeFood2 = new TypeFood("Trà sữa",foodList2);
        Food foodss1 = new Food("Bánh mỳ","https://images.foody.vn/res/g17/162330/prof/s640x400/foody-mobile-banhmi-jpg-753-636074600722621225.jpg",10000);
        Food foodss2 = new Food("Bánh mỳ","https://images.foody.vn/res/g17/162330/prof/s640x400/foody-mobile-banhmi-jpg-753-636074600722621225.jpg",11000);
        Food foodss3 = new Food("Bánh mỳ","https://images.foody.vn/res/g17/162330/prof/s640x400/foody-mobile-banhmi-jpg-753-636074600722621225.jpg",12000);
        foodList3.add(foodss1);
        foodList3.add(foodss2);
        foodList3.add(foodss3);
        TypeFood typeFood3 = new TypeFood("Bánh mỳ",foodList3);
        list.add(typeFood1);
        list.add(typeFood2);
        list.add(typeFood3);
        typeFood1.setCheck(true);
        view.showTypeFood(list,HomePresenter.this,HomePresenter.this);

    }



    @Override
    public void onReturn(TypeFood typeFood, int groupPosition) {
        view.showFood(typeFood.getFoodList(),HomePresenter.this);
    }

    @Override
    public void onReturn(Food food, int groupPosition) {
        view.nextFragment(food);
    }
}
