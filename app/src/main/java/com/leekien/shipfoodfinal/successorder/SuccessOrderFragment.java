package com.leekien.shipfoodfinal.successorder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.OrderSuccessAdapter;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.statusorder.StatusOrderPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuccessOrderFragment extends Fragment implements SuccessOrderManager.View {
    SuccessOrderPresenter presenter;
    RecyclerView rcvOrder;
    TextView tvName,tvPhone,tvBirth;
    ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_history_ship, container, false);
        presenter= new SuccessOrderPresenter(this);
        tvName = view.findViewById(R.id.tvName);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvBirth = view.findViewById(R.id.tvBirth);
        rcvOrder = view.findViewById(R.id.rcvOrder);
        image = view.findViewById(R.id.image);
        tvName.setText(MainActivity.user.getName());
        tvPhone.setText(MainActivity.user.getPhone());
        tvBirth.setText(MainActivity.user.getBirthdate());
        Picasso.get().load(MainActivity.user.getUrlimage()).into(image);
        presenter.getListOrder();
        return view;
    }

    @Override
    public void showListOrder(List<Order> list) {
        OrderSuccessAdapter orderSuccessAdapter = new OrderSuccessAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvOrder.setLayoutManager(layoutManager);
        rcvOrder.setAdapter(orderSuccessAdapter);
    }
}
