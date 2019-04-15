package com.leekien.shipfoodfinal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.leekien.shipfoodfinal.adapter.SlideImageAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.IOnBackPressed;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.login.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
//    List<String> list = new ArrayList<String>();
//    List<TypeFood> typeFoodList = new ArrayList<>();
    static public User user;
    static public   List<Food> listFood =  new ArrayList<>();
    static public String latShop = "20.997791";
    static public String lonShop = "105.841122";
    static public boolean checkOrder =true;
    @Override
    protected void onStop() {
        super.onStop();
        listFood = new ArrayList<>();
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listFood);
        myEditor.putString("MyObject", json);
        myEditor.commit();
    }

    @Override
    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag("kiennk");
//        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
//            super.onBackPressed();
//        }
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1 || count ==2 ) {
            finish();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment fragment = new LoginFragment();

        fragmentTransaction.add(R.id.lnlayout, fragment);
        fragmentTransaction.addToBackStack("loginFragment");
        fragmentTransaction.commit();
//        ViewPager viewPager = findViewById(R.id.viewPager);
//        CircleIndicator circleIndicator = findViewById(R.id.circleIndicator);
//        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
//        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
//        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
//        SlideImageAdapter slideImageAdapter = new SlideImageAdapter(MainActivity.this,list);
//        viewPager.setAdapter(slideImageAdapter);
//        circleIndicator.setViewPager(viewPager);
        //set cứng data show đồ ăn
//        TypeFood typeFood1 = new TypeFood("Ăn vặt",);

    }
}
