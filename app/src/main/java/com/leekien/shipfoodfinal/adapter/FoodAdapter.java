package com.leekien.shipfoodfinal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leekien.shipfoodfinal.AppUtils;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    List<Food> foodList;
    onReturn onReturn;
    onEdit onEdit;

    public FoodAdapter(List<Food> foodList, onReturn onReturn,onEdit onEdit) {
        this.foodList = foodList;
        this.onReturn = onReturn;
        this.onEdit= onEdit;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_iitem_food, viewGroup, false);
        return new FoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Food food = foodList.get(i);
        Picasso.get().load(food.getUrlfood()).into(viewHolder.imageView);
        viewHolder.textviewTitle.setText(food.getName());
        if(!CommonActivity.isNullOrEmpty(MainActivity.user)){
            if("admin".equals(MainActivity.user.getType())){
                viewHolder.imgEdit.setVisibility(View.VISIBLE);
                viewHolder.tvNumberDat.setVisibility(View.GONE);
            }
            else {
                viewHolder.imgEdit.setVisibility(View.GONE);
                viewHolder.tvNumberDat.setVisibility(View.VISIBLE);
            }
        }
        if ("0".equals(food.getDiscount())) {
            viewHolder.tvDiscount.setVisibility(View.GONE);
        } else {
            viewHolder.tvDiscount.setText("-"+food.getDiscount()+"%");
        }
        if (CommonActivity.isNullOrEmpty(food.getPrice())) {
            viewHolder.textviewPrice.setVisibility(View.GONE);
        } else {
            viewHolder.textviewPrice.setText(AppUtils.formatMoney(String.valueOf(food.getPrice())));
        }
        if (CommonActivity.isNullOrEmpty(food.getNumberDat())) {
            viewHolder.tvNumberDat.setVisibility(View.GONE);
        } else {
            viewHolder.tvNumberDat.setText(food.getNumberDat() +" "+"phần");
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(food, i);
            }
        });
        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEdit.onEdit(food, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,imgEdit;
        TextView textviewTitle;
        TextView textviewPrice;
        TextView tvNumberDat,tvDiscount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            textviewTitle = itemView.findViewById(R.id.textviewTitle);
            textviewPrice = itemView.findViewById(R.id.textviewPrice);
            tvNumberDat = itemView.findViewById(R.id.tvNumberDat);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            imageView = itemView.findViewById(R.id.imageView);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }

    public interface onReturn {
        void onReturn(Food food, int groupPosition);
    }
    public interface onEdit {
        void onEdit(Food food, int groupPosition);
    }
}
