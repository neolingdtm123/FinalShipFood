package com.leekien.shipfoodfinal.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;

import java.io.Serializable;

public class DialogPriceFragment extends DialogFragment {
    TextView textViewTitle;
    ImageView imageViewCong;
    ImageView imageViewTru;
    TextView textView;
    TextView tvPrice;
    Button btnContinue;
    Button btnCancel;
    private Bundle mBundle;
    Food food;
    public static DialogPriceFragment newInstance(Food food) {
        DialogPriceFragment dialog = new DialogPriceFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", (Serializable) food);
        dialog.setArguments(args);
        return dialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_dialog_fragment, container);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         food = (Food) getArguments().getSerializable("data");
        textView = view.findViewById(R.id.textview);
        tvPrice = view.findViewById(R.id.tvPrice);
        textViewTitle = view.findViewById(R.id.tvTitle);
        imageViewCong = view.findViewById(R.id.imgCong);
        imageViewTru = view.findViewById(R.id.imgTru);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnContinue = view.findViewById(R.id.btnContinue);
        textView.setText("1");
        textViewTitle.setText(food.getName());
        tvPrice.setText(food.getPrice()+"");
        imageViewCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num =Integer.parseInt(textView.getText().toString())+1;
                textView.setText( num+"");
                tvPrice.setText(food.getPrice() * num+"");
            }
        });
        imageViewTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num =Integer.parseInt(textView.getText().toString())-1 ;
                textView.setText(num +"");
                tvPrice.setText(food.getPrice() * num+"");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
