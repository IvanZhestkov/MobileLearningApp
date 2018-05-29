package com.itis.android.mobilelearningapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;

public class GroupFragmentViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;

    public TextView rate;

    public GroupFragmentViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);

        userName = itemView.findViewById(R.id.tv_user_name);
        rate = itemView.findViewById(R.id.tv_rate);

        itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(getAdapterPosition());
            }
        });
    }
}
