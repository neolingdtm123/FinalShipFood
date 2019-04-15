package com.leekien.shipfoodfinal.logout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.User;

public class LogOutFragment extends Fragment implements View.OnClickListener,LogOutManager.View {
    EditText edtName,edtLocation,edtPhone,edtBirthDate;
    Button btnBack,btnSubmit;
    ImageView image;
    LogOutPresenter logOutPresenter;
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
        initView();
        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        image.setOnClickListener(this);
        return view;
    }

    private void initView() {
        edtName.setText(MainActivity.user.getName());
        edtPhone.setText(MainActivity.user.getPhone());
        edtBirthDate.setText(MainActivity.user.getBirthdate());
        edtLocation.setText(MainActivity.user.getLocation());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnBack:
                getFragmentManager().popBackStack("loginFragment",0);
                break;
            case R.id.image:
                setDate();
                break;
            case R.id.btnSubmit:
                update();
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
        Toast.makeText(getContext(),"Cập nhật thông tin thành công",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTime(String date) {
        edtBirthDate.setText(date);
    }
}
