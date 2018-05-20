package com.itis.android.mobilelearningapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.models.Subject;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;
import com.itis.android.mobilelearningapp.viewholders.StudyFragmentViewHolder;

import java.util.List;

public class StudyFragmentAdapter extends RecyclerView.Adapter<StudyFragmentViewHolder> {

    private List<Subject> subjects;

    private OnItemClickListener onItemClickListener;

    public StudyFragmentAdapter(@NonNull List<Subject> subjects, OnItemClickListener onItemClickListener) {
        this.subjects = subjects;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StudyFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_study, parent, false);
        return new StudyFragmentViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyFragmentViewHolder holder, int position) {
        Subject subject = subjects.get(position);

        holder.name.setText(subject.getName());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
