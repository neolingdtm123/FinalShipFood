package com.leekien.shipfoodfinal.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.home.HomeFragment;

public class LoginFragment extends Fragment implements LoginManager.View, View.OnClickListener {
    LoginPresenter presenter;
    EditText edtUserName;
    EditText edtpw;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        edtUserName = view.findViewById(R.id.edtUserName);
        edtpw = view.findViewById(R.id.edtPassWord);
        textView= view.findViewById(R.id.tvLogin);
        presenter = new LoginPresenter(LoginFragment.this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getInfo(edtUserName.getText().toString(),edtpw.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void showInfoLogin(String code, User user) {
        if ("0".equals(code)) {
            Toast.makeText(getContext(), "Đăng nhập thàng công", Toast.LENGTH_LONG).show();
            HomeFragment homeFragment = new HomeFragment();
            replaceFragment(homeFragment,"hash");
        } else {
            Toast.makeText(getContext(), "Bạn đã nhập sai tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLogin: {
                presenter.getInfo(edtUserName.getText().toString(),edtpw.getText().toString());
                break;
            }
        }
    }
}
