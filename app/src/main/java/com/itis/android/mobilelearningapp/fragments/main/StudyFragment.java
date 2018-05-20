package com.itis.android.mobilelearningapp.fragments.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.AddSubjectActivity;
import com.itis.android.mobilelearningapp.activities.HomeworkActivity;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.adapters.StudyFragmentAdapter;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.models.Subject;
import com.itis.android.mobilelearningapp.utils.UserRole;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class StudyFragment extends Fragment implements OnItemClickListener {

    private TextView emptyText;

    private FloatingActionButton btnAddSubject;

    private RecyclerView recyclerView;

    private List<Subject> subjects;

    private LinearLayoutManager layoutManager;

    private StudyFragmentAdapter adapter;

    private DatabaseReference mDatabaseSubjects;
    private DatabaseReference mDatabaseHomeworks;
    private DatabaseReference mDatabaseSubProgress;

    public static StudyFragment newInstance() {
        Bundle args = new Bundle();
        StudyFragment studyFragment = new StudyFragment();
        studyFragment.setArguments(args);
        return studyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);

        initToolbar();
        initFields(view);
        initClickListeners();

        subjects = new ArrayList<>();

        mDatabaseSubjects = FirebaseDatabase.getInstance().getReference("Subjects");
        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Homeworks");
        mDatabaseSubProgress = FirebaseDatabase.getInstance().getReference("SubjectProgress");

        //checkIfEmpty();

        if (MainActivity.userr.getRole() == UserRole.ROLE_USER) {
            btnAddSubject.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabaseSubjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjects.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    Subject subject = subjectSnapshot.getValue(Subject.class);
                    if (subject != null && subject.getGroupId().equals(MainActivity.groupId)) {
                        subjects.add(subject);
                    }
                }

                //checkIfEmpty();

                layoutManager = new LinearLayoutManager(getActivity());
                adapter = new StudyFragmentAdapter(subjects, StudyFragment.this);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                removeSubject(item.getGroupId());
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Subject subject = subjects.get(position);
        startActivity(HomeworkActivity.makeInflatedIntent(getActivity(), subject.getId(), subject.getName()));
    }

    private void initFields(View view) {
        recyclerView = view.findViewById(R.id.rv_study);
        btnAddSubject = view.findViewById(R.id.fab_add_subject);
        emptyText = view.findViewById(R.id.tv_no_subjects);
    }

    private void initClickListeners() {
        btnAddSubject.setOnClickListener(view -> addSubject());
    }

    private void removeSubject(int position) {
        String subjectId = subjects.get(position).getId();
        mDatabaseSubjects.child(subjectId).removeValue();
        mDatabaseHomeworks.child(subjectId).removeValue();
        mDatabaseSubProgress.child(MainActivity.groupId).child(subjectId).removeValue();
    }

    private void addSubject() {
        startActivity(AddSubjectActivity.makeInflatedIntent(getActivity()));
    }

    private void checkIfEmpty() {
        if (subjects.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().hide();
    }
}
