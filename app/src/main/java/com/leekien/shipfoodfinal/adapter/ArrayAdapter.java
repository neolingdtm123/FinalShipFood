package com.leekien.shipfoodfinal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.util.List;

public class ArrayAdapter extends android.widget.ArrayAdapter {

    public ArrayAdapter( Context context1, List<User> list) {
        super(context1, 0, list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_array_adapter,parent,false);
        }
        TextView tvName = convertView.findViewById(R.id.tvName);
        User user = (User) getItem(position);
        if(!CommonActivity.isNullOrEmpty(user)){
            tvName.setText(user.getName());
        }
        return  convertView;
    }
}
