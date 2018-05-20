package com.itis.android.mobilelearningapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.utils.UserRole;

public class StudyFragmentViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public StudyFragmentViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);

        name = itemView.findViewById(R.id.tv_name_subject);

        itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(getAdapterPosition());
            }
        });

        itemView.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
            if (MainActivity.userr.getRole() == UserRole.ROLE_ADMIN) {
                contextMenu.add(getAdapterPosition(), 0, 0, "Удалить");
            }
        });
    }
}
