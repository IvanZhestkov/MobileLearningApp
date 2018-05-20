package com.itis.android.mobilelearningapp.fragments;

import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.FinishHomeworkActivity;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.adapters.HometasksProfileAdapter;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.models.Subject;
import com.itis.android.mobilelearningapp.models.SubjectProgress;
import com.itis.android.mobilelearningapp.models.TitleChild;
import com.itis.android.mobilelearningapp.models.TitleParent;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HometasksFragment extends Fragment implements OnItemClickListener {

    private RecyclerView recyclerView;

    private List<TitleParent> titleParents;
    private List<SubjectProgress> subjectProgresses;

    private DatabaseReference mDatabaseSubjects;
    private DatabaseReference mDatabaseSubProgress;
    private DatabaseReference mDatabaseHomeworks;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ((HometasksProfileAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    public static HometasksFragment newInstance() {
        Bundle args = new Bundle();
        HometasksFragment hometasksFragment = new HometasksFragment();
        hometasksFragment.setArguments(args);
        return hometasksFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hometasks_fragment_profile, container, false);

        titleParents = new ArrayList<>();
        subjectProgresses = new ArrayList<>();

        mDatabaseSubjects = FirebaseDatabase.getInstance().getReference("Subjects");
        mDatabaseSubProgress = FirebaseDatabase.getInstance().getReference("SubjectProgress");
        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Homeworks");

        recyclerView = view.findViewById(R.id.rv_hometasks);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabaseSubjects.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titleParents.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    Subject subject = subjectSnapshot.getValue(Subject.class);
                    if (subject != null && subject.getGroupId().equals(MainActivity.groupId)) {
                        TitleParent titleParent = new TitleParent(subject);
                        titleParents.add(titleParent);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                HometasksProfileAdapter adapter = new HometasksProfileAdapter(getActivity(), initData(), HometasksFragment.this);
                adapter.setParentClickableViewAnimationDefaultDuration();
                adapter.setParentAndIconExpandOnClick(true);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<ParentObject> initData() {
        List<ParentObject> parentObjects = new ArrayList<>();
        for (TitleParent titleParent : titleParents) {
            List<Object> childList = new ArrayList<>();
            mDatabaseSubProgress.child(MainActivity.groupId).child(titleParent.getSubject().getId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot subProgressSnapshot : dataSnapshot.getChildren()) {
                                SubjectProgress subjectProgress = subProgressSnapshot.getValue(SubjectProgress.class);
                                if (subjectProgress.getUserId().equals(MainActivity.userr.getId())) {
                                    childList.add(new TitleChild(subjectProgress));
                                    subjectProgresses.add(subjectProgress);
                                    titleParent.setChildObjectList(childList);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            parentObjects.add(titleParent);
        }
        return parentObjects;
    }

    @Override
    public void onClick(int position) {
        SubjectProgress subjectProgress = subjectProgresses.get(position);
        String subjectId = subjectProgress.getSubjectId();
        int rate = subjectProgress.getRate();

        mDatabaseHomeworks.child(subjectId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Subject ID:", subjectId);
                for (DataSnapshot homeworkSnapshot : dataSnapshot.getChildren()) {
                    Homework homework = homeworkSnapshot.getValue(Homework.class);
                    if (homework != null) {
                        String endDate = homework.getEndDate();
                        String timeStamp = new SimpleDateFormat("yyyy/M/dd").format(Calendar.getInstance().getTime());
                        if (endDate.compareTo(timeStamp) < 0) {
                            continue;
                        }
                        startActivity(FinishHomeworkActivity.makeInflatedIntent(getActivity(), homework, subjectId, rate));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
