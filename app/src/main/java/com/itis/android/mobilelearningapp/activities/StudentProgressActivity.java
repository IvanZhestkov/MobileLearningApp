package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.adapters.HometasksProfileAdapter;
import com.itis.android.mobilelearningapp.fragments.HometasksFragment;
import com.itis.android.mobilelearningapp.models.Subject;
import com.itis.android.mobilelearningapp.models.SubjectProgress;
import com.itis.android.mobilelearningapp.models.TitleChild;
import com.itis.android.mobilelearningapp.models.TitleParent;

import java.util.ArrayList;
import java.util.List;

import static com.itis.android.mobilelearningapp.utils.Constants.USER_ID;
import static com.itis.android.mobilelearningapp.utils.Constants.USER_NAME;

public class StudentProgressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<TitleParent> titleParents;

    private DatabaseReference mDatabaseSubjects;
    private DatabaseReference mDatabaseSubProgress;
    private DatabaseReference mDatabaseHomeworks;

    private Toolbar toolbar;

    public static Intent makeInflatedIntent(Context context, String userId, String userName) {
        Intent intent = makeIntent(context);
        intent.putExtra(USER_ID, userId);
        intent.putExtra(USER_NAME, userName);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, StudentProgressActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_progress);
        initToolbar();

        titleParents = new ArrayList<>();

        mDatabaseSubjects = FirebaseDatabase.getInstance().getReference("Subjects");
        mDatabaseSubProgress = FirebaseDatabase.getInstance().getReference("SubjectProgress");
        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Homeworks");

        recyclerView = findViewById(R.id.rv_student_progress_activity);
    }

    @Override
    protected void onStart() {
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

                recyclerView.setLayoutManager(new LinearLayoutManager(StudentProgressActivity.this));

                HometasksProfileAdapter adapter = new HometasksProfileAdapter(StudentProgressActivity.this, initData(), null);
                adapter.setParentClickableViewAnimationDefaultDuration();
                adapter.setParentAndIconExpandOnClick(true);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentProgressActivity.this, databaseError.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<ParentObject> initData() {
        String userId = getIntent().getStringExtra(USER_ID);
        List<ParentObject> parentObjects = new ArrayList<>();
        for (TitleParent titleParent : titleParents) {
            List<Object> childList = new ArrayList<>();
            mDatabaseSubProgress.child(MainActivity.groupId).child(titleParent.getSubject().getId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot subProgressSnapshot : dataSnapshot.getChildren()) {
                                SubjectProgress subjectProgress = subProgressSnapshot.getValue(SubjectProgress.class);
                                if (subjectProgress.getUserId().equals(userId)) {
                                    childList.add(new TitleChild(subjectProgress));
                                    titleParent.setChildObjectList(childList);
                                    break;
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

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_activity_student_progress);
        String userName = getIntent().getStringExtra(USER_NAME);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(userName);
        }
    }
}
