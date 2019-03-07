package com.leekien.shipfoodfinal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    List<Food> foodList;
    onReturn onReturn;

    public FoodAdapter(List<Food> foodList,onReturn onReturn) {
        this.foodList = foodList;
        this.onReturn = onReturn;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_iitem_food, viewGroup, false);
        return new FoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        final Food food = foodList.get(i);
        Picasso.get().load(food.getUrlFood()).into(viewHolder.imageView);
        viewHolder.textviewTitle.setText(food.getName());
        viewHolder.textviewPrice.setText(String.valueOf(food.getPrice()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(food,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textviewTitle;
        TextView textviewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
             textviewTitle = itemView.findViewById(R.id.textviewTitle);
             textviewPrice = itemView.findViewById(R.id.textviewPrice);
             imageView = itemView.findViewById(R.id.imageView);

        }
    }
    public interface onReturn {
        void onReturn(Food food, int groupPosition);
    }
}
