package com.leekien.shipfoodfinal.addfood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.ReasonAdapter;
import com.leekien.shipfoodfinal.bo.Reason;
import com.leekien.shipfoodfinal.bo.onBackDialog;

import java.util.ArrayList;
import java.util.List;

public class DialogChooseAction extends  android.support.v4.app.DialogFragment {
    LinearLayout tvPhone,tvGallery;
    Button btnContinue;
    private onBackDialog listener;
    public void setListener(onBackDialog listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.layout_choose_action,null);
        btnContinue = dialog.findViewById(R.id.btnContinue);
        tvPhone = dialog.findViewById(R.id.tvPhone);
        tvGallery = dialog.findViewById(R.id.tvGallery);
        builder.setView(dialog);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.back("0");
                dismiss();
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.back("1");
                dismiss();
            }
        });

        return builder.create();
    }
}
