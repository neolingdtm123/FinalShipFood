package com.leekien.shipfoodfinal.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.AppUtils;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<Order> orderList;
    HistoryAdapter.onReturn onReturn;

    public HistoryAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_history_adapter, viewGroup, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder viewHolder, final int i) {
        final Order order = orderList.get(i);
        for(User user : order.getUserList()){
            if("shop".equals(user.getType())){
                viewHolder.tvShopName.setText(user.getName());
                viewHolder.tvShopLocation.setText(user.getLocation());
                Picasso.get().load(user.getUrlimage()).into(viewHolder.imageView);
            }
        }
        if(CommonActivity.isNullOrEmpty(order.getContentcancel())){
            viewHolder.ln.setVisibility(View.GONE);
        }
        else {
            viewHolder.ln.setVisibility(View.VISIBLE);
            viewHolder.tvReason.setText(order.getContentcancel());
        }
        viewHolder.tvTime.setText(order.getCreatetime());
        viewHolder.tvStatus.setText(order.getType());
        if("Đã hủy".equals(order.getType())){
            viewHolder.tvStatus.setTextColor(Color.parseColor("#FF0000"));
        }
        else viewHolder.tvStatus.setTextColor(Color.parseColor("#00BB00"));
        viewHolder.tvContent.setText(order.getCreatehour()+"-"+ AppUtils.formatMoney(Integer.valueOf(order.getPrice())+
                Integer.valueOf(order.getPricefood())+""));
        viewHolder.tvId.setText("Đơn #"+order.getId()+"");
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onReturn.onReturn(order,i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId,tvTime,tvContent,tvStatus,tvShopName,tvShopLocation,tvReason;
        ImageView imageView;
        LinearLayout ln;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvId = itemView.findViewById(R.id.tvId);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvShopLocation = itemView.findViewById(R.id.tvShopLocation);
            imageView = itemView.findViewById(R.id.imageView);
            tvReason = itemView.findViewById(R.id.tvReason);
            ln = itemView.findViewById(R.id.lnReason);
        }
    }
    public interface onReturn {
        void onReturn(Order order, int groupPosition);}

}
