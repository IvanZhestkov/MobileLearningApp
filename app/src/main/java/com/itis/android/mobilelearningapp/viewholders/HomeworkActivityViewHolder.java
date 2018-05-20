package com.itis.android.mobilelearningapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.utils.UserRole;

public class HomeworkActivityViewHolder extends RecyclerView.ViewHolder {

    public TextView hwTitle;

    public TextView hwDate;

    public HomeworkActivityViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);

        hwTitle = itemView.findViewById(R.id.tv_hw_title);

        hwDate = itemView.findViewById(R.id.tv_hw_date);

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
