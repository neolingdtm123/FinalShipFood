package com.leekien.shipfoodfinal.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Reason;
import com.leekien.shipfoodfinal.bo.StatusOrder;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class StatusOrderAdapter extends RecyclerView.Adapter<StatusOrderAdapter.ViewHolder> {
    List<StatusOrder> statusOrderList;
    onReturn onReturn;
    public StatusOrderAdapter(List<StatusOrder> statusOrderList,onReturn onReturn) {
        this.statusOrderList = statusOrderList;
        this.onReturn= onReturn;
    }


    @NonNull
    @Override
    public StatusOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_status_order, viewGroup, false);
        return new StatusOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusOrderAdapter.ViewHolder viewHolder, final int i) {
        final StatusOrder statusOrder = statusOrderList.get(i);
        viewHolder.tvNumber.setText(i+1 +"");
        viewHolder.tvStatus.setText(statusOrder.getStatus());
        if(CommonActivity.isNullOrEmpty(statusOrder.getShipPhone())){
            viewHolder.imgCall.setVisibility(View.GONE);
        }
        else {
            viewHolder.imgCall.setVisibility(View.VISIBLE);
        }
        if(CommonActivity.isNullOrEmpty(statusOrder.getShipinfo())){
            viewHolder.tvShip.setVisibility(View.GONE);
        }
        else {
            viewHolder.tvShip.setVisibility(View.VISIBLE);
            viewHolder.tvShip.setText(statusOrder.getShipinfo() +" " + "đã nhận ship hàng");
        }
        if(CommonActivity.isNullOrEmpty(statusOrder.getTime())){
            viewHolder.tvTime.setVisibility(View.GONE);
        }
        else {
            viewHolder.tvTime.setVisibility(View.VISIBLE);
            viewHolder.tvTime.setText(statusOrder.getTime());
        }
        if(statusOrder.isCheck()){
            viewHolder.tvNumber.setBackgroundResource(R.drawable.custom_cicle_orange);
            viewHolder.tvStatus.setTextColor(Color.BLACK);
        }
        else {
            viewHolder.tvNumber.setBackgroundResource(R.drawable.custom_circle);
            viewHolder.tvStatus.setTextColor(Color.GRAY);
        }
        if(i ==2){
            viewHolder.view.setVisibility(View.GONE);
        }
        viewHolder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(statusOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber,tvStatus,tvShip,tvTime;
        View view;
        ImageView imgCall;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvShip = itemView.findViewById(R.id.tvShip);
            tvTime = itemView.findViewById(R.id.tvTime);
            view = itemView.findViewById(R.id.view);
            imgCall = itemView.findViewById(R.id.imgCall);
        }
    }
    public interface onReturn{
        void onReturn( StatusOrder statusOrder);
    }

}
