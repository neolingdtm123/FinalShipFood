package com.leekien.shipfoodfinal.adapter;

import android.location.Address;
import android.location.Geocoder;
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
import com.leekien.shipfoodfinal.bo.Order;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    List<Order> orderList;
    DonHangAdapter.onReturn onReturn;

    public DonHangAdapter(List<Order> orderList, DonHangAdapter.onReturn onReturn) {
        this.orderList = orderList;
        this.onReturn = onReturn;
    }


    @NonNull
    @Override
    public DonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_show_detail_donhang, viewGroup, false);
        return new DonHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.ViewHolder viewHolder, final int i) {
        final Order order = orderList.get(i);
        viewHolder.tvDistance.setText(order.getDistance());
        viewHolder.tvStatus.setText(order.getType());
        viewHolder.tvLocation.setText(order.getShopAdress());
        viewHolder.tvId.setText("#"+order.getId()+"");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(order,i);
            }
        });
        viewHolder.imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReplace(order,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgShow;
        TextView tvId,tvDistance,tvLocation,tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvId = itemView.findViewById(R.id.tvId);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgShow = itemView.findViewById(R.id.imgShow);
        }
    }
    public interface onReturn {
        void onReturn(Order order, int groupPosition);
        void onReplace(Order order, int groupPosition);
    }

}
