package com.leekien.shipfoodfinal.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.leekien.shipfoodfinal.AppUtils;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.FoodAdapter;
import com.leekien.shipfoodfinal.adapter.SlideImageAdapter;
import com.leekien.shipfoodfinal.adapter.TypeFoodAdapter;
import com.leekien.shipfoodfinal.addfood.AddFoodFragment;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.IOnBackPressed;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.cart.CartFragment;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.customView.RobBoldText;
import com.leekien.shipfoodfinal.customView.RobEditText;
import com.leekien.shipfoodfinal.shipper.ShipperFragment;
import com.leekien.shipfoodfinal.signup.SignUpFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements HomeManager.View, View.OnClickListener, IOnBackPressed {
    List<String> list = new ArrayList<String>();
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    RecyclerView rcvFood;
    RobEditText mEdtSearch;
    ViewFlipper viewFlipper;
    View mLayoutHeader;
    RobBoldText tvCancel;
    RecyclerView rcvType;
    FloatingActionButton fab,fabGioHang,fabAccount;
    boolean check = false;
    TextView tvChu,tvShip,tvNumber;
    ImageView btnSearch;
    User user;
    View mLayoutSearch;
    int position =0;
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_customer, container, false);
        Bundle bundle = getArguments();
        if(!CommonActivity.isNullOrEmpty(bundle)){
            user = (User) bundle.getSerializable("user");
        }
        viewFlipper = view.findViewById(R.id.viewFlipper);
        btnSearch = view.findViewById(R.id.btn_search);
        tvCancel = view.findViewById(R.id.tv_cancel);
        mLayoutSearch = view.findViewById(R.id.layout_search);
        mLayoutHeader = view.findViewById(R.id.layout_header);
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleIndicator);
        mEdtSearch = view.findViewById(R.id.edt_search);
        rcvFood = view.findViewById(R.id.rcvFood);
        rcvType = view.findViewById(R.id.rcvType);
        fab= view.findViewById(R.id.fab);
        fabGioHang= view.findViewById(R.id.fabGioHang);
        fabAccount= view.findViewById(R.id.fabAccount);
        tvChu = view.findViewById(R.id.tvChu);
        tvNumber = view.findViewById(R.id.tvNumber);
        tvShip = view.findViewById(R.id.tvShip);
        HomePresenter presenter = new HomePresenter(HomeFragment.this);
        presenter.getFood(user);
        initData();
        if(!CommonActivity.isNullOrEmpty(MainActivity.listFood)){
            tvNumber.setVisibility(View.VISIBLE);
            tvNumber.setText(MainActivity.listFood.size()+"");
        }
        else {
            tvNumber.setVisibility(View.GONE);
        }
        if(!check){
            fabGioHang.setVisibility(View.GONE);
            fabAccount.setVisibility(View.GONE);
        }
        else{
            fabGioHang.setVisibility(View.VISIBLE);
            fabAccount.setVisibility(View.VISIBLE);
        }
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
        fabGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment cartFragment = new CartFragment();
                check = false;
                replaceFragment(cartFragment,"kiennk");
            }
        });
        tvShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShipperFragment shipperFragment = new ShipperFragment();
                replaceFragment(shipperFragment,"sạdja");
            }
        });
        tvCancel.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void showFood(List<Food> foodList,FoodAdapter.onReturn onReturn,int position1) {
        position = position1;
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
        List<Food> list = typeFoods.get(0).getFoodList();
        FoodAdapter foodAdapter = new FoodAdapter(list,onReturn1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        rcvFood.setLayoutManager(gridLayoutManager);
        rcvFood.setAdapter(foodAdapter);
    }

    @Override
    public void nextFragment(Food food) {
        if("user".equals(user.getType())){
            FragmentManager fm = getFragmentManager();
            DialogPriceFragment dialogPriceFragment = DialogPriceFragment.newInstance(food);
            dialogPriceFragment.show(fm, null);
        }

    }

    @Override
    public void upLoadImage() {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        AddFoodFragment addFoodFragment = new AddFoodFragment();
        addFoodFragment.setArguments(bundle);
        replaceFragment(addFoodFragment,"addFoodFragment");
    }


    @SuppressLint("RestrictedApi")
    public void initData() {
        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
        list.add("https://images.foody.vn/biz_banner/foody-9life-636879105106471465.jpg");
        list.add("https://images.foody.vn/biz_banner/foody-buffet%20ufo%20dac%20biet%20giam-636878968926402410.jpg");
        /**
         * cmt để dùng viewFlipper thay cho VIewPager
         */
        setUpViewFlipper(list);
       /* SlideImageAdapter slideImageAdapter = new SlideImageAdapter(getContext(), list);
        viewPager.setAdapter(slideImageAdapter);
        circleIndicator.setViewPager(viewPager);*/
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
    private void gotoSearch() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutHeader.setVisibility(View.GONE);
                mEdtSearch.requestFocus();
                AppUtils.showKeybord(getActivity(), mEdtSearch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        });
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    AppUtils.hideSoftKeyboard(getActivity(), mEdtSearch);
                }
                return false;
            }
        });
        mLayoutHeader.startAnimation(animation);
        mLayoutSearch.setVisibility(View.VISIBLE);
        mLayoutSearch.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));
    }
    private void gotoHeader() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutSearch.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        });
        mEdtSearch.setText("");

        mLayoutSearch.startAnimation(animation);
        mLayoutHeader.setVisibility(View.VISIBLE);
        mLayoutHeader.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
        AppUtils.hideSoftKeyboard(getActivity(), mEdtSearch);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_search:
                gotoSearch();
                break;
            case R.id.tv_cancel:
                gotoHeader();
                break;
        }
    }
    public void setUpViewFlipper(List<String> lstAds){
        for(int i = 0;i<lstAds.size();i++){
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            Picasso.get().load(lstAds.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getActivity(), R.anim.slide_in_right);
        viewFlipper.setOutAnimation(getActivity(),R.anim.slide_out_right);
    }

    @Override
    public boolean onBackPressed() {
        return false;

    }
}
