package com.leekien.shipfoodfinal.statusorder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.ReasonAdapter;
import com.leekien.shipfoodfinal.adapter.StatusOrderAdapter;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Reason;
import com.leekien.shipfoodfinal.bo.onBackDialog;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by leekien on 9/21/2018.
 */

public class DialogReasonFragment extends android.support.v4.app.DialogFragment implements ReasonAdapter.onReturn {
    RecyclerView rcvCancel;
    TextView tvCancel;
    List<Reason> list = new ArrayList<>();
    String context;
    private onBackDialog listener;
    public void setListener(onBackDialog listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.layout_reason, null);
        rcvCancel = dialog.findViewById(R.id.rcvCancel);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        builder.setView(dialog);
        //add data reason
        List<Reason> reasonList = new ArrayList<>();
        Reason reason1 = new Reason(false, "Suy nghĩ lại về giá cả va các loại phí");
        Reason reason2 = new Reason(false, "Đặt lại đơn hàng khác");
        Reason reason3 = new Reason(false, "Tôi có việc bận nên không thể nhận hàng");
        Reason reason4 = new Reason(false, "Shipper xác nhận,giao đơn hàng chậm quá");
        reasonList.add(reason1);
        reasonList.add(reason2);
        reasonList.add(reason3);
        reasonList.add(reason4);
        ReasonAdapter reasonAdapter = new ReasonAdapter(reasonList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCancel.setLayoutManager(layoutManager);
        rcvCancel.setAdapter(reasonAdapter);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.back(context);
                dismiss();
            }
        });
        return builder.create();
    }


    @Override
    public void onReturn(boolean check, Reason reason) {
        context = "";
        if (!check) {
            list.add(reason);
        } else {
            for (int i = 0; i < list.size(); i++) {
                Reason reason1 = list.get(i);
                if (reason1.getText().equals(reason.getText())) {
                    list.remove(i);
                }
            }
        }
        for (Reason reason1 : list) {
            context += reason1.getText();
        }
    }
}