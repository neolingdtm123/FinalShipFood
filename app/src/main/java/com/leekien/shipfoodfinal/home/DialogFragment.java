package com.leekien.shipfoodfinal.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.onBackDialog;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.Calendar;

/**
 * Created by leekien on 9/21/2018.
 */

public class DialogFragment extends  android.support.v4.app.DialogFragment {
    TextView textViewTitle;
    ImageView imageViewCong;
    ImageView imageViewTru;
    TextView textView;
    TextView tvPrice;
    Button btnContinue;
    int num = 0;
    boolean check = false;
    private onBackDialog listener;
    private Food food;
    public void setListener(onBackDialog listener, Food food) {
        this.listener = listener;
        this.food = food;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final Calendar cal = Calendar.getInstance();
        View dialog = inflater.inflate(R.layout.layout_dialog_fragment,null);
        textView = dialog.findViewById(R.id.textview);
        tvPrice = dialog.findViewById(R.id.tvPrice);
        textViewTitle = dialog.findViewById(R.id.tvTitle);
        imageViewCong = dialog.findViewById(R.id.imgCong);
        imageViewTru = dialog.findViewById(R.id.imgTru);
        btnContinue = dialog.findViewById(R.id.btnContinue);
        textView.setText("1");
        textViewTitle.setText(food.getName());
        tvPrice.setText(food.getPrice() + " " + "đ");
        num = Integer.parseInt(textView.getText().toString());
        food.setPriceDat(food.getPrice() * num + "");
        imageViewCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = Integer.parseInt(textView.getText().toString()) + 1;
                textView.setText(num + "");
                tvPrice.setText(food.getPrice() * num + " " + "đ");
                food.setPriceDat(food.getPrice() * num + "");
            }
        });
        imageViewTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = Integer.parseInt(textView.getText().toString()) - 1;
                textView.setText(num + "");
                tvPrice.setText(food.getPrice() * num + " " + "đ");
                food.setPriceDat(food.getPrice() * num + "");
            }
        });


        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (num != 0) {
                            food.setNumberDat(num + "");
                            if (!CommonActivity.isNullOrEmpty(MainActivity.listFood)) {
                                for (int i = 0; i < MainActivity.listFood.size(); i++) {
                                    if (MainActivity.listFood.get(i).getId() == food.getId() && MainActivity.listFood.get(i).getIdTypeFood()
                                            == food.getIdTypeFood()) {
                                        MainActivity.listFood.set(i,food);
                                        check = true;
                                    }
                                }

                            }
                            if(!check){
                                MainActivity.listFood.add(food);
                            }
                        } else {
                            if (!CommonActivity.isNullOrEmpty(MainActivity.listFood)) {
                                for (int i = 0; i < MainActivity.listFood.size(); i++) {
                                    if (MainActivity.listFood.get(i).getId() == food.getId() && MainActivity.listFood.get(i).getIdTypeFood()
                                            == food.getIdTypeFood()) {
                                        MainActivity.listFood.remove(i);
                                    }
                                }
                            }
                        }
                        listener.back(null);
                    }
                })
                .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


}