package com.leekien.shipfoodfinal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Order;

import java.util.List;

import butterknife.ButterKnife;

public class OrderSuccessAdapter extends RecyclerView.Adapter<OrderSuccessAdapter.ViewHolder> {
    List<Order> orderList;

    public OrderSuccessAdapter(List<Order> orderList) {
        this.orderList = orderList;

    }


    @NonNull
    @Override
    public OrderSuccessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_adapter_history_ship, viewGroup, false);
        return new OrderSuccessAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSuccessAdapter.ViewHolder viewHolder, final int i) {
        final Order order = orderList.get(i);
        viewHolder.tvShiptime.setText("Hoàn thành:"+ " "+order.getShiptime());
        viewHolder.tvPhone.setText(order.getUserList().get(0).getName());
        viewHolder.tvLocation.setText(order.getUserList().get(0).getLocation());
        viewHolder.tvId.setText("Đơn #"+order.getId()+"");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvId,tvName,tvLocation,tvPhone,tvShiptime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvId = itemView.findViewById(R.id.tvId);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvShiptime = itemView.findViewById(R.id.tvShiptime);
        }
    }


}
