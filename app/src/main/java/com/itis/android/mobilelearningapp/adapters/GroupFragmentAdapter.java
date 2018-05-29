package com.itis.android.mobilelearningapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.models.User;
import com.itis.android.mobilelearningapp.viewholders.GroupFragmentViewHolder;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;

import java.util.List;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentViewHolder> {

    private List<User> users;

    private OnItemClickListener onItemClickListener;

    public GroupFragmentAdapter(@NonNull List<User> users, OnItemClickListener onItemClickListener) {
        this.users = users;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GroupFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_group, parent, false);
        return new GroupFragmentViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupFragmentViewHolder holder, int position) {
        User user = users.get(position);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = firstName + " " + lastName;

        holder.userName.setText(userName);
        holder.rate.setText(user.getRate() + "");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
