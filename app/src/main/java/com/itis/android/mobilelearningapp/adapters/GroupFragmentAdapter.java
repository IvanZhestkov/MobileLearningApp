package com.itis.android.mobilelearningapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.models.User;
import com.itis.android.mobilelearningapp.viewholders.GroupFragmentViewHolder;

import java.util.List;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentViewHolder> {

    private List<User> users;

    private Context context;

    public GroupFragmentAdapter(@NonNull List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_group, parent, false);
        return new GroupFragmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupFragmentViewHolder holder, int position) {
        User user = users.get(position);

        holder.firstName.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
