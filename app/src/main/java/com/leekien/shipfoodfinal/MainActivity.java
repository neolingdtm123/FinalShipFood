package com.leekien.shipfoodfinal;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    static public Intent mServiceIntent;
    private FusedLocationProviderClient client;
    //    List<String> list = new ArrayList<String>();
//    List<TypeFood> typeFoodList = new ArrayList<>();
    static public User user;
    static public User userShop;
    static public List<Food> listFood = new ArrayList<>();
    static public boolean checkOrder = true;
    static public boolean checkAddFood = false;
    static public double lat;
    static public double lon;
    static public int idshop =0;
    static public String auth;
    static public String point;

    @Override
    protected void onStop() {
        super.onStop();
        listFood = new ArrayList<>();
//        SharedPreferences myPreferences
//                = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor myEditor = myPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(listFood);
//        myEditor.putString("MyObject", json);
//        myEditor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag("kiennk");
//        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
//            super.onBackPressed();
//        }
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1 || count == 2) {
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

       getLocation();


    }



    public void getLocation() {
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    MainActivity.lat = location.getLatitude();
                    MainActivity.lon = location.getLongitude();
                }
            }
        });
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
