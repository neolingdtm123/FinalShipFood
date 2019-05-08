package com.leekien.shipfoodfinal.history;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.HistoryAdapter;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryManager.View, View.OnClickListener {
    ImageView imgFromDate, imgTodate;
    TextView tvFromDate, tvTodate;
    String fromDate, toDate;
    RecyclerView rcvHistory;
    LinearLayout lnShow;
    HistoryPresenter historyPresenter;
    List<Order> orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_history, container, false);
        imgFromDate = view.findViewById(R.id.imgFromDate);
        imgTodate = view.findViewById(R.id.imgEndDate);
        tvFromDate = view.findViewById(R.id.tvfromDate);
        tvTodate = view.findViewById(R.id.tvEndDate);
        rcvHistory = view.findViewById(R.id.rcvHistory);
        lnShow = view.findViewById(R.id.lnShow);
        fromDate = "1/3/2019";
        tvFromDate.setText(fromDate);
        String pattern = "dd/MM/yyyy";
        Date today = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat(pattern);
        toDate = df.format(today);
        tvTodate.setText(toDate);
        historyPresenter = new HistoryPresenter(this);
        historyPresenter.getOrderCus();
        imgFromDate.setOnClickListener(this);
        imgTodate.setOnClickListener(this);
        return view;
    }

    @Override
    public void showHistory(List<Order> list) {
        orderList = new ArrayList<>();
        orderList.addAll(list);
        HistoryAdapter historyAdapter = new HistoryAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvHistory.setLayoutManager(layoutManager);
        rcvHistory.setAdapter(historyAdapter);
    }

    @Override
    public void showTime(int day, int month, int year, String check) throws ParseException {
        if ("0".equals(check)) {
            fromDate = day + "/" + month + "/" + year + "";
            tvFromDate.setText(fromDate);
            setDate();
        } else {
            toDate = day + "/" + month + "/" + year + "";
            tvTodate.setText(toDate);
            setDate();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgFromDate:
                historyPresenter.showTime(getContext(), fromDate, "0");
                break;
            case R.id.imgEndDate:
                historyPresenter.showTime(getContext(), toDate, "1");
                break;
        }
    }
    void setDate() throws ParseException {
        List<Order> orders = new ArrayList<>();
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
        Date date3 = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
        for (Order order : orderList) {
            String date = order.getCreatetime();
            Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            if (date2.after(date1) && date2.before(date3)) {
                orders.add(order);
            }
        }
        if (CommonActivity.isNullOrEmpty(orders)) {
            lnShow.setVisibility(View.VISIBLE);
            rcvHistory.setAdapter(null);
        } else {
            lnShow.setVisibility(View.GONE);
            HistoryAdapter historyAdapter = new HistoryAdapter(orders);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvHistory.setLayoutManager(layoutManager);
            rcvHistory.setAdapter(historyAdapter);
        }
    }
}
