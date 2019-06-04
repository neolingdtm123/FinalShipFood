package com.leekien.shipfoodfinal.rank;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.RankAdapter;
import com.leekien.shipfoodfinal.bo.Rank;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RankFragment extends Fragment implements RankAdapter.onReturn {
    View view1, view2;
    RecyclerView rcvRank;
    TextView tvPointRank, tvDate,tvNotify,tvName,tvNameRank,tvContent,tvDisCount;
    LinearLayout ln,ln1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rank_point, container, false);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        rcvRank = view.findViewById(R.id.rcvRank);
        tvPointRank = view.findViewById(R.id.tvPointRank);
        tvDate = view.findViewById(R.id.tvDate);
        tvNotify = view.findViewById(R.id.tvNotify);
        tvName = view.findViewById(R.id.tvName);
        tvNameRank = view.findViewById(R.id.tvNameRank);
        tvContent = view.findViewById(R.id.tvContent);
        tvDisCount = view.findViewById(R.id.tvDisCount);
        ln = view.findViewById(R.id.ln);
        ln1 = view.findViewById(R.id.ln1);
        initView();
        return view;
    }

    private void initView() {
        List<Rank> list = new ArrayList<>();
        Rank rank1 = new Rank("Chuẩn", false);
        Rank rank2 = new Rank("Bạc", false);
        Rank rank3 = new Rank("Vàng", false);
        list.add(rank1);
        list.add(rank2);
        list.add(rank3);
        tvName.setText(MainActivity.user.getName());
        tvPointRank.setText(MainActivity.point + " ĐIỂM");
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date lastDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvDate.setText(MainActivity.point + " điểm sẽ hết hạn vào ngày " + sdf.format(lastDayOfMonth));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthInDP = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        int widthMain =0;
        if(Integer.parseInt(MainActivity.point) <500){
            tvNameRank.setText("Thành viên Chuẩn");
            ln.setBackgroundResource(R.drawable.custom_cicle_bac);
            view1.setBackgroundResource(R.drawable.custom_view_bac);
            Double d = (Double.parseDouble(MainActivity.point)/500*(widthInDP-60));
            widthMain = d.intValue();
            tvNotify.setText("Tích lũy thêm "+ String.valueOf(500-Integer.valueOf(MainActivity.point))+" điểm để đạt thành viên Bạc");
            list.get(0).setCheck(true);
            tvContent.setText("ĐẶC QUYỀN CHO THÀNH VIÊN CHUẨN");
            ln1.setBackgroundResource(R.drawable.custom_cicle_chuan);
            tvDisCount.setText("Đơn hàng được giảm giá thêm 0%");

        }
        else if(Integer.parseInt(MainActivity.point)<1000){
            tvNameRank.setText("Thành viên Bạc");
            ln.setBackgroundResource(R.drawable.custom_cicle_vang);
            view1.setBackgroundResource(R.drawable.custom_view_vang);
            Double d = (Double.parseDouble(MainActivity.point)/1000*(widthInDP-60));
            widthMain = d.intValue();
            tvNotify.setText("Tích lũy thêm "+ String.valueOf(1000-Integer.valueOf(MainActivity.point))+" điểm để đạt thành viên Vàng");
            list.get(1).setCheck(true);
            tvContent.setText("ĐẶC QUYỀN CHO THÀNH VIÊN BẠC");
            ln1.setBackgroundResource(R.drawable.custom_cicle_bac);
            tvDisCount.setText("Đơn hàng được giảm giá thêm 5%");
        }
        else {
            tvNameRank.setText("Thành viên Vàng");
            list.get(2).setCheck(true);
            tvContent.setText("ĐẶC QUYỀN CHO THÀNH VIÊN VÀNG");
            ln1.setBackgroundResource(R.drawable.custom_cicle_vang);
            tvDisCount.setText("Đơn hàng được giảm giá thêm 10%");
        }
        ViewGroup.LayoutParams params = view1.getLayoutParams();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (widthMain * scale + 0.5f);
        params.width = pixels;
        view1.requestLayout();

        RankAdapter rankAdapter = new RankAdapter(list,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvRank.setLayoutManager(layoutManager);
        rcvRank.setAdapter(rankAdapter);

//        view1.setLayoutParams(new ViewGroup.LayoutParams(width/5, 1));

    }

    @Override
    public void onReturn(int i) {
        if(i==0){
            tvContent.setText("ĐẶC QUYỀN DÀNH CHO THÀNH VIÊN CHUẨN");
            ln1.setBackgroundResource(R.drawable.custom_cicle_chuan);
            tvDisCount.setText("Đơn hàng được giảm giá thêm 0%");
        }
        else if(i==1){
            tvContent.setText("ĐẶC QUYỀN DÀNH CHO THÀNH VIÊN BẠC");
            ln1.setBackgroundResource(R.drawable.custom_cicle_bac);
            tvDisCount.setText("Đơn hàng được giảm giá thêm 5%");
        }
        else {
            tvContent.setText("ĐẶC QUYỀN DÀNH CHO THÀNH VIÊN VÀNG");
            ln1.setBackgroundResource(R.drawable.custom_cicle_vang);
            tvDisCount.setText("Đơn hàng được giảm giá thêm 10%");
        }
    }
}
