package com.itis.android.mobilelearningapp.adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.viewholders.HomeworkActivityViewHolder;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeworkActivityAdapter extends RecyclerView.Adapter<HomeworkActivityViewHolder> {

    private List<Homework> homeworkList;

    private OnItemClickListener onItemClickListener;

    public HomeworkActivityAdapter(List<Homework> homeworkList, OnItemClickListener onItemClickListener) {
        this.homeworkList = homeworkList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomeworkActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homework, parent, false);
        return new HomeworkActivityViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkActivityViewHolder holder, int position) {
        Homework homework = homeworkList.get(position);

        String endDate = homework.getEndDate();
        String currentTime = new SimpleDateFormat("yyyy/M/dd").format(Calendar.getInstance().getTime());
        Log.d("dates compare1", endDate.compareTo(currentTime) + "");
        Log.d("current date1", currentTime);
        Log.d("end date1", endDate);
        if (endDate.compareTo(currentTime) < 0) {
            Log.d("dates compare", endDate.compareTo(currentTime) + "");
            Log.d("current date", currentTime);
            Log.d("end date", endDate);
            holder.hwDate.setPaintFlags(holder.hwDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.hwTitle.setPaintFlags(holder.hwTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.hwDate.setText(homework.getStartDate());
        holder.hwTitle.setText(homework.getName());
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }
}
