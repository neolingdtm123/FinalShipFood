package com.leekien.shipfoodfinal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeFoodAdapter extends RecyclerView.Adapter<TypeFoodAdapter.ViewHolder> {
    public TypeFoodAdapter(List<TypeFood> typeFoodList, onReturn onReturn) {
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
    public void onBindViewHolder(@NonNull final TypeFoodAdapter.ViewHolder viewHolder, final int position) {
        final TypeFood typeFood = typeFoodList.get(position);
        Picasso.get().load(typeFood.getUrlType()).into(viewHolder.imageView);
        viewHolder.textView.setText(typeFood.getTitle());
        if (typeFood.isCheck()) {
            if(position ==0 ){
                viewHolder.lnShow.setBackgroundResource(R.drawable.custom_cicle_green);
            }
            else if(position ==1){
                viewHolder.lnShow.setBackgroundResource(R.drawable.custom_cicle_blue);
            }
            else if(position ==2){
                viewHolder.lnShow.setBackgroundResource(R.drawable.custonm_cicle_red);
            }
            else if(position ==3){
                viewHolder.lnShow.setBackgroundResource(R.drawable.custom_cicle_yellow);
            }

        } else {
            viewHolder.lnShow.setBackgroundResource(R.drawable.custom_circle);
        }
        if(position ==0 ){
            viewHolder.imageView.setImageResource(R.drawable.ic_vectoranvat);
        }
        else if(position ==1){
            viewHolder.imageView.setBackgroundResource(R.drawable.ic_trasua);
        }
        else if(position ==2){
            viewHolder.imageView.setBackgroundResource(R.drawable.ic_vectorcomtrua);
        }
        else if(position ==3){
            viewHolder.imageView.setBackgroundResource(R.drawable.ic_vectorngoisao);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < typeFoodList.size(); i++) {
                    if (i == position) {
                        onReturn.onReturn(typeFood, i);
                        typeFood.setCheck(true);
                    } else {
                        typeFoodList.get(i).setCheck(false);
                    }
                }
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return typeFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnShow;
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
            lnShow = itemView.findViewById(R.id.lnShow);
        }
    }

    public interface onReturn {
        void onReturn(TypeFood typeFood, int groupPosition);
    }

}
