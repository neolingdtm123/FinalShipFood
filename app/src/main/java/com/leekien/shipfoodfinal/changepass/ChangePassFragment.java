package com.leekien.shipfoodfinal.changepass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.common.CommonActivity;

public class ChangePassFragment extends Fragment implements ChangePassManager.View  {
    EditText edtPass,edtConfirmPass;
    Button btnContinue;
    ChangePassPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_change_pass, container, false);
        presenter= new ChangePassPresenter(this);
        edtPass = view.findViewById(R.id.edtPass);
        edtConfirmPass = view.findViewById(R.id.edtConfirmPass);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtPass.getText().toString().equals(edtConfirmPass.getText().toString())){
                    CommonActivity.createAlertDialog(getActivity(),getString(R.string.error_password),getString(R.string.shipfood)).show();
                }
                else {
                    presenter.updateInfo(MainActivity.user.getId(),edtPass.getText().toString());
                }
            }
        });
        return view;
    }
    @Override
    public void updateInfo() {
        Toast.makeText(getContext(),"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
