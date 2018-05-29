package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.adapters.HomeworkActivityAdapter;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.utils.UserRole;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeworkActivity extends AppCompatActivity implements OnItemClickListener {

    public static final String SUBJECT_ID = "subjectId";
    public static final String SUBJECT_NAME = "subjectName";

    private TextView emptyText;

    private RecyclerView recyclerView;

    private List<Homework> homeworkList;

    private LinearLayoutManager layoutManager;

    private HomeworkActivityAdapter adapter;

    private DatabaseReference mDatabaseHomeworks;

    private Toolbar toolbar;

    public static Intent makeInflatedIntent(Context context, String subjectId, String subjectName) {
        Intent intent = makeIntent(context);
        intent.putExtra(SUBJECT_ID, subjectId);
        intent.putExtra(SUBJECT_NAME, subjectName);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, HomeworkActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        initToolbar();

        homeworkList = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_homework_activity);
        emptyText = findViewById(R.id.tv_no_homeworks);

        initRecycler();

        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Homeworks");

        checkIfEmpty();

        //updateList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String subjectId = getIntent().getStringExtra(SUBJECT_ID);

        mDatabaseHomeworks.child(subjectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                homeworkList.clear();

                for (DataSnapshot homeworkSnapshot : dataSnapshot.getChildren()) {
                    Homework homework = homeworkSnapshot.getValue(Homework.class);
                    if (homework != null) {
                        homeworkList.add(homework);
                    }
                }

                checkIfEmpty();

                initRecycler();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (MainActivity.userr.getRole() == UserRole.ROLE_ADMIN) {
            getMenuInflater().inflate(R.menu.menu_activity_homework, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_homework:
                String subjectId = getIntent().getStringExtra(SUBJECT_ID);
                startActivity(AddHomeworkActivity.makeInflatedIntent(this, subjectId));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                removeHomework(item.getGroupId());
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Homework homework = homeworkList.get(position);
        startActivity(DetailsHomeworkActivity.makeInflatedIntent(this, homework));
    }

    private void checkIfEmpty() {
        if (homeworkList.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
    }

    private void removeHomework(int position) {
        String subjectId = getIntent().getStringExtra(SUBJECT_ID);
        mDatabaseHomeworks.child(subjectId).child(homeworkList.get(position).getId()).removeValue();
    }

    private void initToolbar() {
        String subjectName = getIntent().getStringExtra(SUBJECT_NAME);
        toolbar = findViewById(R.id.toolbar_activity_homework);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("ДЗ" + "(" + subjectName + ")");
        }
    }

    private void initRecycler() {
        adapter = new HomeworkActivityAdapter(homeworkList, HomeworkActivity.this);
        layoutManager = new LinearLayoutManager(HomeworkActivity.this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
    }
}
