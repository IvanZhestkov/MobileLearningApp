package com.itis.android.mobilelearningapp.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.itis.android.mobilelearningapp.R;

public class TitleParentViewHolder extends ParentViewHolder {

    public TextView textView;

    public TitleParentViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv_parent_title_hfp);


    }
}
