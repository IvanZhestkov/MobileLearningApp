package com.itis.android.mobilelearningapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;

public class GroupFragmentViewHolder extends RecyclerView.ViewHolder {

    public TextView firstName;

    public TextView lastName;

    public GroupFragmentViewHolder(View itemView) {
        super(itemView);

        firstName = itemView.findViewById(R.id.tv_first_name);

        lastName = itemView.findViewById(R.id.tv_last_name);
    }
}
