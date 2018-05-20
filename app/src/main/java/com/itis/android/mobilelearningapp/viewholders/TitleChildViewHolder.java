package com.itis.android.mobilelearningapp.viewholders;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.itis.android.mobilelearningapp.R;

public class TitleChildViewHolder extends ChildViewHolder {

    public TextView rate;

    public ProgressBar progressBar;

    public TitleChildViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar2);
        rate = itemView.findViewById(R.id.tv_rate);

        itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(getAdapterPosition());
            }
        });
    }
}
