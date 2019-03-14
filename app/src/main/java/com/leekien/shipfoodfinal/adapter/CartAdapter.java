package com.leekien.shipfoodfinal.adapter;

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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Food> foodList;

    public CartAdapter(List<Food> foodList) {
        this.foodList = foodList;
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
        Picasso.get().load(food.getUrlFood()).into(viewHolder.imageView);
        viewHolder.tvName.setText(food.getName());
        viewHolder.tvPrice.setText(food.getPrice()+"");
        viewHolder.textView.setText("1");
        viewHolder.imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num =Integer.parseInt(viewHolder.textView.getText().toString())+1;
                viewHolder.textView.setText( num+"");
                viewHolder.tvPrice.setText(food.getPrice() * num+" " +"đ");
            }
        });
        viewHolder.imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num =Integer.parseInt(viewHolder.textView.getText().toString())-1 ;
                viewHolder.textView.setText(num +"");
               viewHolder. tvPrice.setText(food.getPrice() * num+" " +"đ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,imgCong,imgTru;
        TextView tvName,tvPrice,textView;

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
        }
    }

}
