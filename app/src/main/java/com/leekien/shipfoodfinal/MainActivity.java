package com.leekien.shipfoodfinal;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leekien.shipfoodfinal.adapter.SlideImageAdapter;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.login.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    List<String> list = new ArrayList<String>();
    List<TypeFood> typeFoodList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment fragment = new LoginFragment();

        fragmentTransaction.add(R.id.lnlayout, fragment);

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