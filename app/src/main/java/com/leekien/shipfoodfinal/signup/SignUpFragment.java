package com.leekien.shipfoodfinal.signup;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;

public class SignUpFragment extends Fragment implements SignUpManager.View{
    SignUpPresenter signUpPresenter;
    EditText edtName;
    ImageView imgCalendar;
    EditText edtCalendar;
    RadioButton radioNu;
    RadioButton radioNam;
    EditText edtLocation;
    ImageView imgCus;
    ImageView imgShip;
    EditText edtUserName;
    EditText edtPass;
    EditText edtConfirmPass;
    Button btnBack;
    Button btnSubmit;
    boolean checkType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sign_up, container, false);
        signUpPresenter = new SignUpPresenter(SignUpFragment.this);
        edtName = view.findViewById(R.id.edtName);
        edtCalendar = view.findViewById(R.id.edtCalendar);
        edtLocation = view.findViewById(R.id.edtLocation);
        edtUserName = view.findViewById(R.id.edtUserName);
        edtPass = view.findViewById(R.id.edtPass);
        edtConfirmPass = view.findViewById(R.id.edtConfirmPass);
        imgCalendar = view.findViewById(R.id.imgCalendar);
        imgCus = view.findViewById(R.id.imageCus);
        imgShip = view.findViewById(R.id.imageShip);
        radioNu = view.findViewById(R.id.radioNu);
        radioNam = view.findViewById(R.id.radioNam);
        btnBack = view.findViewById(R.id.btnBack);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                signUpPresenter.showTime(getContext());
            }
        });
        imgCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCus.setBackgroundResource(R.drawable.custom_icon);
                imgShip.setBackgroundResource(R.drawable.custom_type_food);
                checkType = true;
            }
        });
        imgShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgShip.setBackgroundResource(R.drawable.custom_icon);
                imgCus.setBackgroundResource(R.drawable.custom_type_food);
                checkType = true;
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.validate(edtCalendar.getText().toString(),edtLocation.getText().toString(),checkType,
                        edtUserName.getText().toString(),edtPass.getText().toString(),edtConfirmPass.getText().toString());
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }


    @Override
    public void validate(String type) {

         if("1".equals(type)){
             Toast.makeText(getContext(), "Bạn chưa nhập đủ các thông tin", Toast.LENGTH_LONG).show();
         }
        else if("2".equals(type)){
             Toast.makeText(getContext(), "Mật khẩu bạn nhập chưa trùng khớp", Toast.LENGTH_LONG).show();
        }
        else {
             Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
             getFragmentManager().popBackStack();
         }

    }

    @Override
    public void showTime(String date) {
        edtCalendar.setText(date);
    }
}
