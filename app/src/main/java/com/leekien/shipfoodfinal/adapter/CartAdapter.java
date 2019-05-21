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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Food> foodList;
    onReturn onReturn;

    public CartAdapter(List<Food> foodList, onReturn onReturn) {
        this.foodList = foodList;
        this.onReturn = onReturn;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_cart_adapter, viewGroup, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder viewHolder, final int i) {
        final Food food = foodList.get(i);
        Picasso.get().load(food.getUrlfood()).into(viewHolder.imageView);
        if (!MainActivity.checkOrder) {
//            viewHolder.tvPrice.setVisibility(View.GONE);
//            viewHolder.textView.setVisibility(View.GONE);
            viewHolder.imgXoa.setVisibility(View.GONE);
        }
        int discount;
        if ("0".equals(food.getDiscount())) {
            viewHolder.tvDiscount.setVisibility(View.GONE);
            discount = 0;
        } else {
            discount = Integer.parseInt(food.getDiscount());
            viewHolder.tvDiscount.setVisibility(View.VISIBLE);
            viewHolder.tvDiscount.setText("-" + food.getDiscount() + "%");
        }
        viewHolder.tvName.setText(food.getName());
        viewHolder.tvPrice.setText(AppUtils.formatMoney(food.getPrice()*Integer.parseInt(food.getNumberDat()) * (100 - discount) / 100 + ""));

        viewHolder.textView.setText(food.getNumberDat() + " " + "phần");

        viewHolder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(food, i);
            }
        });
    }

    public interface onReturn {
        void onReturn(Food food, int position);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, imgCong, imgTru, imgXoa;
        TextView tvName, tvPrice, textView, tvDiscount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            imageView = itemView.findViewById(R.id.imgAnh);
            imgCong = itemView.findViewById(R.id.imgCong);
            imgTru = itemView.findViewById(R.id.imgTru);
            tvName = itemView.findViewById(R.id.tvName);
            textView = itemView.findViewById(R.id.textview);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
        }
    }

}
