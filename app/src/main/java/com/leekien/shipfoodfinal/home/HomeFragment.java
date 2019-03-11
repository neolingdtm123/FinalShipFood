package com.leekien.shipfoodfinal.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.SlideImageAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.shipper.ShipperFragment;
import com.leekien.shipfoodfinal.signup.SignUpFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements HomeManager.View {
    List<String> list = new ArrayList<String>();
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    RecyclerView rcvFood;
    RecyclerView rcvType;
    FloatingActionButton fab,fabGioHang,fabAccount;
    boolean check = false;
    TextView tvChu,tvShip;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_customer, container, false);
        Bundle bundle = getArguments();
        if(!CommonActivity.isNullOrEmpty(bundle)){
            user = (User) bundle.getSerializable("user");
        }
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleIndicator);
        rcvFood = view.findViewById(R.id.rcvFood);
        rcvType = view.findViewById(R.id.rcvType);
        fab= view.findViewById(R.id.fab);
        fabGioHang= view.findViewById(R.id.fabGioHang);
        fabAccount= view.findViewById(R.id.fabAccount);
        tvChu = view.findViewById(R.id.tvChu);
        tvShip = view.findViewById(R.id.tvShip);
        HomePresenter presenter = new HomePresenter(HomeFragment.this);
        presenter.getFood();
        initData();
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                if(!check){
                    fabGioHang.setVisibility(View.VISIBLE);
                    fabAccount.setVisibility(View.VISIBLE);
                    check= true;
                }
                else {
                    fabGioHang.setVisibility(View.GONE);
                    fabAccount.setVisibility(View.GONE);
                    check= false;
                }
            }
        });
        tvShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShipperFragment shipperFragment = new ShipperFragment();
                replaceFragment(shipperFragment,"sạdja");
            }
        });
        //set cứng data show đồ ăn
//        TypeFood typeFood1 = new TypeFood("Ăn vặt",);
        return view;
    }

    @Override
    public void showFood(List<Food> foodList,FoodAdapter.onReturn onReturn) {
        FoodAdapter foodAdapter = new FoodAdapter(foodList,onReturn);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        rcvFood.setLayoutManager(gridLayoutManager);
        rcvFood.setAdapter(foodAdapter);
    }

    @Override
    public void showTypeFood(List<TypeFood> typeFoods,TypeFoodAdapter.onReturn onReturn,FoodAdapter.onReturn onReturn1) {
        TypeFoodAdapter typeFoodAdapter = new TypeFoodAdapter(typeFoods,onReturn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvType.setLayoutManager(layoutManager);
        rcvType.setAdapter(typeFoodAdapter);
        FoodAdapter foodAdapter = new FoodAdapter(typeFoods.get(0).getFoodList(),onReturn1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        rcvFood.setLayoutManager(gridLayoutManager);
        rcvFood.setAdapter(foodAdapter);
    }

    @Override
    public void nextFragment(Food food) {
        FragmentManager fm = getFragmentManager();
        DialogPriceFragment dialogPriceFragment = DialogPriceFragment.newInstance(food);
        dialogPriceFragment.show(fm, null);
    }

    @SuppressLint("RestrictedApi")
    public void initData() {
        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
        SlideImageAdapter slideImageAdapter = new SlideImageAdapter(getContext(), list);
        viewPager.setAdapter(slideImageAdapter);
        circleIndicator.setViewPager(viewPager);
        if("ship".equals(user.getType())){
            tvShip.setVisibility(View.VISIBLE);
        }
        if(check){
            fabGioHang.setVisibility(View.VISIBLE);
            fabAccount.setVisibility(View.VISIBLE);
            check= true;
        }
        else {
            fabGioHang.setVisibility(View.GONE);
            fabAccount.setVisibility(View.GONE);
            check= false;
        }
    }
    public void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.lnlayout, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
