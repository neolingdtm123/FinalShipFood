package com.leekien.shipfoodfinal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeFoodAdapter extends RecyclerView.Adapter<TypeFoodAdapter.ViewHolder> {
    public TypeFoodAdapter(List<TypeFood> typeFoodList,onReturn onReturn) {
        this.typeFoodList = typeFoodList;
        this.onReturn = onReturn;
    }

    private List<TypeFood> typeFoodList;
    onReturn onReturn;
    @NonNull
    @Override
    public TypeFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_typefood, viewGroup, false);
        return new TypeFoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeFoodAdapter.ViewHolder viewHolder,final int i) {
        final TypeFood typeFood = typeFoodList.get(i);
        Picasso.get().load(typeFood.getUrlType()).into(viewHolder.imageView);
        viewHolder.textView.setText(typeFood.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onReturn.onReturn(typeFood,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeFoodList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.textview)
        TextView textView;


//        @BindView(R.id.tvAddress)
//        TextView tvAddress;
//        @BindView(R.id.rcItemSubscriber)
//        RecyclerView rcItemSubscriber;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }
    public interface onReturn {
        void onReturn(TypeFood typeFood, int groupPosition);
    }

}
