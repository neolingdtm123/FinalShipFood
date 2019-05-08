package com.leekien.shipfoodfinal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    List<User> userList;
    onReturn onReturn;

    public AdminAdapter(List<User> userList, onReturn onReturn) {
        this.userList = userList;
        this.onReturn = onReturn;
    }


    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_admin_adapter, viewGroup, false);
        return new AdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminAdapter.ViewHolder viewHolder, final int i) {
        final User user = userList.get(i);
        Picasso.get().load(user.getUrlimage()).into(viewHolder.imageView);
        viewHolder.tvName.setText(user.getName());
        viewHolder.tvLocation.setText(user.getLocation());
        if (i == userList.size() - 1) {
            viewHolder.ln.setVisibility(View.GONE);
            viewHolder.imgXoa.setVisibility(View.GONE);
            viewHolder.imgThem.setVisibility(View.VISIBLE);
            Picasso.get().load(user.getUrlimage()).into(viewHolder.imgThem);
        }
        else {
            viewHolder.ln.setVisibility(View.VISIBLE);
            viewHolder.imgXoa.setVisibility(View.VISIBLE);
            viewHolder.imgThem.setVisibility(View.GONE);
        }
        viewHolder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(user, "1");
            }
        });
        viewHolder.imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(user, "0");
            }
        });
        viewHolder.imgThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(user, "2");
            }
        });
        viewHolder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReturn.onReturn(user, "3");
            }
        });
    }

    public interface onReturn {
        void onReturn(User user, String check);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, imgMessage, imgPhone,imgThem,imgXoa;
        TextView tvName, tvLocation;
        LinearLayout ln;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            imageView = itemView.findViewById(R.id.imageView);
            imgThem = itemView.findViewById(R.id.imgThem);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            imgMessage = itemView.findViewById(R.id.imgMessage);
            imgPhone = itemView.findViewById(R.id.imgPhone);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            ln = itemView.findViewById(R.id.ln);
        }
    }

}
