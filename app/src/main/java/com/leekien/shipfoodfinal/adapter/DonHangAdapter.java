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

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    List<DonHang> donHangList;
    DonHangAdapter.onReturn onReturn;

    public DonHangAdapter(List<DonHang> donHangList, DonHangAdapter.onReturn onReturn) {
        this.donHangList = donHangList;
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
        final DonHang donHang = donHangList.get(i);
        viewHolder.tvDistance.setText(donHang.getDistance() + " " +"km");
        viewHolder.tvLocation.setText(donHang.getLocation());
        viewHolder.tvId.setText(donHang.getId());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(donHang,i);
            }
        });
        viewHolder.imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReplace(donHang,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgShow;
        TextView tvId,tvDistance,tvLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvId = itemView.findViewById(R.id.tvId);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            imgShow = itemView.findViewById(R.id.imgShow);
        }
    }
    public interface onReturn {
        void onReturn(DonHang donHang, int groupPosition);
        void onReplace(DonHang donHang, int groupPosition);
    }
}
