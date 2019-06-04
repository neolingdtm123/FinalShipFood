package com.leekien.shipfoodfinal.logout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.changepass.ChangePassFragment;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.rank.RankFragment;
import com.leekien.shipfoodfinal.statis.StatisFragment;

public class LogOutFragment extends Fragment implements View.OnClickListener, LogOutManager.View {
    EditText edtName, edtLocation, edtPhone, edtBirthDate;
    Button btnBack, btnSubmit;
    TextView tv,tvRank,tvPointRank;
    LinearLayout ln,lnStatis,lnShow;
    ImageView image,imgStatis,imgchangePass,imgShow;
    LogOutPresenter logOutPresenter;
    View view1,view2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_logout, container, false);
        logOutPresenter = new LogOutPresenter(this);
        edtName = view.findViewById(R.id.edtName);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtLocation = view.findViewById(R.id.edtLocation);
        edtBirthDate = view.findViewById(R.id.edtBirthDate);
        btnBack = view.findViewById(R.id.btnBack);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        image = view.findViewById(R.id.image);
        imgStatis = view.findViewById(R.id.imgStatis);
        lnStatis = view.findViewById(R.id.lnStatis);
        imgchangePass = view.findViewById(R.id.imgchangePass);
        ln = view.findViewById(R.id.ln);
        tv = view.findViewById(R.id.tv);
        lnShow = view.findViewById(R.id.lnShow);
        imgShow = view.findViewById(R.id.imgShow);
        tvRank = view.findViewById(R.id.tvRank);
        tvPointRank = view.findViewById(R.id.tvPointRank);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        initView();
        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        image.setOnClickListener(this);
        imgStatis.setOnClickListener(this);
        imgchangePass.setOnClickListener(this);
        imgShow.setOnClickListener(this);
        return view;
    }

    private void initView() {
        if(!CommonActivity.isNullOrEmpty(MainActivity.point)){
            if(Integer.parseInt(MainActivity.point) <500){
                tvRank.setText("Thành viên chuẩn");
                tvPointRank.setText("Còn "+String.valueOf(500-Integer.parseInt(MainActivity.point))+" điểm để nâng rank");

            }
            else if(Integer.parseInt(MainActivity.point)<1000){
                tvRank.setText("Thành viên bạc");
                tvPointRank.setText("Còn "+String.valueOf(1000-Integer.parseInt(MainActivity.point))+" điểm để nâng rank");
            }
            else {
                tvRank.setText("Thành viên vàng");
                tvPointRank.setVisibility(View.GONE);
            }
        }

        if (!CommonActivity.isNullOrEmpty(MainActivity.user)) {
            edtName.setText(MainActivity.user.getName());
            edtPhone.setText(MainActivity.user.getPhone());
            edtBirthDate.setText(MainActivity.user.getBirthdate());
            edtLocation.setText(MainActivity.user.getLocation());
        }
        if(!CommonActivity.isNullOrEmpty(MainActivity.user)&&"user".equals(MainActivity.user.getType())){
            lnShow.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        }
        else {
            lnShow.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }
        if(!CommonActivity.isNullOrEmpty(MainActivity.user)&&"shop".equals(MainActivity.user.getType())){
            lnStatis.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            ln.setVisibility(View.GONE);
        }
        else {
            lnStatis.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            ln.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnBack:
                getActivity().getSupportFragmentManager().popBackStack("loginFragment", 0);
                break;
            case R.id.image:
                setDate();
                break;
            case R.id.btnSubmit:
                update();
                break;
            case R.id.imgStatis:
                StatisFragment statisFragment = new StatisFragment();
                Bundle bundle = new Bundle();
                bundle.putString("check","1");
                statisFragment.setArguments(bundle);
                replaceFragment(statisFragment, "statisFragment");
                break;
            case R.id.imgchangePass:
                ChangePassFragment changePassFragment = new ChangePassFragment();
                replaceFragment(changePassFragment, "changePassFragment");
                break;
            case R.id.imgShow:
                RankFragment rankFragment = new RankFragment();
                replaceFragment(rankFragment, "rankFragment");
                break;
        }
    }

    private void update() {
        MainActivity.user.setBirthdate(edtBirthDate.getText().toString());
        MainActivity.user.setName(edtName.getText().toString());
        MainActivity.user.setPhone(edtPhone.getText().toString());
        MainActivity.user.setLocation(edtLocation.getText().toString());
        logOutPresenter.updateInfo(MainActivity.user);
    }

    private void setDate() {
        logOutPresenter.showTime(getContext());
    }

    @Override
    public void updateInfo() {
        Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTime(String date) {
        edtBirthDate.setText(date);
    }
    public void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.lnlayout, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
