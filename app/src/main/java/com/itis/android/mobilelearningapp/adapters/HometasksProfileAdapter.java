package com.itis.android.mobilelearningapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.models.Subject;
import com.itis.android.mobilelearningapp.models.SubjectProgress;
import com.itis.android.mobilelearningapp.models.TitleChild;
import com.itis.android.mobilelearningapp.models.TitleParent;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;
import com.itis.android.mobilelearningapp.viewholders.TitleChildViewHolder;
import com.itis.android.mobilelearningapp.viewholders.TitleParentViewHolder;

import java.util.List;

public class HometasksProfileAdapter extends ExpandableRecyclerAdapter<TitleParentViewHolder, TitleChildViewHolder> {

    private LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public HometasksProfileAdapter(Context context, List<ParentObject> parentItemList, OnItemClickListener onItemClickListener) {
        super(context, parentItemList);
        layoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public TitleParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.item_hometasks_fr_profile, viewGroup, false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.child_hometasks_fr_profile, viewGroup, false);
        return new TitleChildViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindParentViewHolder(TitleParentViewHolder titleParentViewHolder, int i, Object o) {
        TitleParent titleParent = (TitleParent) o;
        Subject subject = titleParent.getSubject();
        titleParentViewHolder.textView.setText(subject.getName());
    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder titleChildViewHolder, int i, Object o) {
        TitleChild titleChild = (TitleChild) o;

        SubjectProgress subjectProgress = titleChild.getSubjectProgress();

        int progressHw = subjectProgress.getRate();
        int progressSubject = subjectProgress.getRateSubject();

        titleChildViewHolder.pbSubj.setProgress(progressSubject);
        titleChildViewHolder.rateSubject.setText(String.valueOf(progressSubject) + "%");
        titleChildViewHolder.pbHw.setProgress(progressHw);
        titleChildViewHolder.rateHw.setText(String.valueOf(progressHw) + "%");
    }
}
