package com.leekien.shipfoodfinal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Rank;
import com.leekien.shipfoodfinal.bo.StatusOrder;

import java.util.List;

import butterknife.ButterKnife;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    List<Rank> rankList;
    onReturn onReturn;
    public RankAdapter(List<Rank> rankList,onReturn o) {
        this.rankList = rankList;
        this.onReturn =o;
    }


    @NonNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_rank_item, viewGroup, false);
        return new RankAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewHolder viewHolder, final int position) {
        final Rank rank = rankList.get(position);
        viewHolder.tvName.setText(rank.getName());
        if(!rank.isCheck()){
            viewHolder.ln.setBackgroundResource(R.drawable.custom_linear_gray);
        }
        else {
            viewHolder.ln.setBackgroundResource(R.drawable.custom_linear_normal);
        }
        if(position==0){
            viewHolder.lnRank.setBackgroundResource(R.drawable.custom_cicle_chuan);
        }
        else if(position==1){
            viewHolder.lnRank.setBackgroundResource(R.drawable.custom_cicle_bac);
        }
        else {
            viewHolder.lnRank.setBackgroundResource(R.drawable.custom_cicle_vang);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < rankList.size(); i++) {
                    if (i == position) {
                        rank.setCheck(true);
                        onReturn.onReturn(position);
                    } else {
                        rankList.get(i).setCheck(false);
                    }
                }
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        LinearLayout ln,lnRank;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            tvName = itemView.findViewById(R.id.tvName);
            ln = itemView.findViewById(R.id.ln);
            lnRank = itemView.findViewById(R.id.lnRank);
        }
    }
    public interface onReturn{
        void onReturn( int i);
    }

}
