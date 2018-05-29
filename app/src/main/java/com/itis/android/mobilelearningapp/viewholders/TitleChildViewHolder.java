package com.itis.android.mobilelearningapp.viewholders;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.itis.android.mobilelearningapp.R;

public class TitleChildViewHolder extends ChildViewHolder {

    public TextView rateSubject;
    public TextView rateHw;

    public ProgressBar pbHw;
    public ProgressBar pbSubj;

    public TitleChildViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        pbHw = itemView.findViewById(R.id.progressBar2);
        pbSubj = itemView.findViewById(R.id.pb_rate);
        rateHw = itemView.findViewById(R.id.tv_rate2);
        rateSubject = itemView.findViewById(R.id.tv_rate);

        itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(getAdapterPosition());
            }
        });
    }
}
