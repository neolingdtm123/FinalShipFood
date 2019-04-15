package com.leekien.shipfoodfinal.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public class ReasonAdapter extends RecyclerView.Adapter<ReasonAdapter.ViewHolder> {
    List<Reason> reasons;
    onReturn onReturn;
    public ReasonAdapter(List<Reason> reasons,onReturn onReturn) {
        this.reasons = reasons;
        this.onReturn = onReturn;
    }


    @NonNull
    @Override
    public ReasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_cancel_item, viewGroup, false);
        return new ReasonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReasonAdapter.ViewHolder viewHolder, final int i) {
        final Reason reason = reasons.get(i);
        viewHolder.tvReason.setText(reason.getText());
        viewHolder.cbCancel.setChecked(reason.isCheck());
        viewHolder.cbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(reason.isCheck(),reason);
                reason.setCheck(!reason.isCheck());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reasons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvReason;
        CheckBox cbCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvReason = itemView.findViewById(R.id.tvReason);
            cbCancel = itemView.findViewById(R.id.cbCancel);
        }
    }
   public interface onReturn{
        void onReturn(boolean check,Reason reason);
    }

}
