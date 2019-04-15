package com.leekien.shipfoodfinal.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.home.HomeFragment;
import com.leekien.shipfoodfinal.shipper.ShipperFragment;
import com.leekien.shipfoodfinal.signup.SignUpFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginFragment extends Fragment implements LoginManager.View, View.OnClickListener {
    LoginPresenter presenter;
    EditText edtUserName;
    EditText edtpw;
    TextView textView;
    TextView tvSignUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        edtUserName = view.findViewById(R.id.edtUserName);
        edtpw = view.findViewById(R.id.edtPassWord);
        textView= view.findViewById(R.id.tvLogin);
        TextView tvSignUp= view.findViewById(R.id.tvSignUp);
        presenter = new LoginPresenter(LoginFragment.this);
        textView.setOnClickListener(this);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment = new SignUpFragment();
                replaceFragment(signUpFragment,"hash");
            }
        });
        return view;
    }

    @Override
    public void showInfoLogin(String code, User user) {
        if ("0".equals(code)) {
            Toast.makeText(getContext(), getString(R.string.success_login), Toast.LENGTH_LONG).show();
            MainActivity.listFood = new ArrayList<>();
            SharedPreferences myPreferences
                    = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor myEditor = myPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.listFood);
            myEditor.putString("MyObject", json);
            myEditor.commit();
            MainActivity.user = user;
            if("ship".equals(user.getType())){
                ShipperFragment shipperFragment = new ShipperFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", (Serializable) user);
                shipperFragment.setArguments(bundle);
                replaceFragment(shipperFragment,"sạdja");
            }
            else {
                HomeFragment homeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", (Serializable) user);
                homeFragment.setArguments(bundle);
                replaceFragment(homeFragment,"homeframent");
            }

        } else {
            CommonActivity.createAlertDialog(getActivity(),getString(R.string.wrong_login),getString(R.string.shipfood)).show();
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
