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
import com.leekien.shipfoodfinal.bo.Comment;
import com.leekien.shipfoodfinal.bo.NetworkController;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpFragment extends Fragment implements SignUpManager.View {
    SignUpPresenter signUpPresenter;

    EditText edtName;
    ImageView imgCalendar;
    EditText edtCalendar;
    RadioButton radioNu;
    RadioButton radioNam;
    EditText edtLocation;
    EditText edtUserName;
    EditText edtPass;
    EditText edtConfirmPass;
    EditText edtPhone;
    Button btnBack;
    Button btnSubmit;
    String check;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sign_up, container, false);
        Bundle bundle = getArguments();
        if(!CommonActivity.isNullOrEmpty(bundle)){
            check = bundle.getString("check");
        }
        signUpPresenter = new SignUpPresenter(SignUpFragment.this);
        edtName = view.findViewById(R.id.edtName);
        edtCalendar = view.findViewById(R.id.edtCalendar);
        edtLocation = view.findViewById(R.id.edtLocation);
        edtUserName = view.findViewById(R.id.edtUserName);
        edtPass = view.findViewById(R.id.edtPass);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtConfirmPass = view.findViewById(R.id.edtConfirmPass);
        imgCalendar = view.findViewById(R.id.imgCalendar);
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
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.validate(edtCalendar.getText().toString(), edtLocation.getText().toString(),
                        edtUserName.getText().toString(), edtPass.getText().toString(), edtConfirmPass.getText().toString(), edtName.getText().toString(), edtPhone.getText().toString());
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
        if ("3".equals(type)) {
            CommonActivity.createAlertDialog(getActivity(), getString(R.string.error_username), getString(R.string.shipfood)).show();
        } else if ("1".equals(type)) {
            CommonActivity.createAlertDialog(getActivity(), getString(R.string.null_value), getString(R.string.shipfood)).show();
        } else if ("2".equals(type)) {
            CommonActivity.createAlertDialog(getActivity(), getString(R.string.error_password), getString(R.string.shipfood)).show();
        } else {
            User user = new User();
            user.setUsername(edtUserName.getText().toString());
            user.setPassword(edtPass.getText().toString());
            if("0".equals(check)){
                user.setType("user");
            }
            else if("1".equals(check)){
                user.setType("shop");
            }
            else if("2".equals(check)){
                user.setType("ship");
            }
            user.setLocation(edtLocation.getText().toString());
            user.setName(edtName.getText().toString());
            user.setPhone(edtPhone.getText().toString());
            user.setBirthdate(edtCalendar.getText().toString());
            signUpPresenter.getData(user);
//             getFragmentManager().popBackStack();
        }

    }

    @Override
    public void showTime(String date) {
        edtCalendar.setText(date);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }
}
