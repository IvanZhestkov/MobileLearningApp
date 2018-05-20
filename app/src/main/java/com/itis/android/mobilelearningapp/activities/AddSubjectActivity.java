package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.models.Subject;
import com.itis.android.mobilelearningapp.models.SubjectProgress;
import com.itis.android.mobilelearningapp.models.User;

public class AddSubjectActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextInputLayout tiSubjectName;
    private EditText edtSubjectName;

    private DatabaseReference mDatabaseSubjects;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseSubjectProgress;

    public static Intent makeInflatedIntent(Context context) {
        Intent intent = makeIntent(context);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddSubjectActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        initToolbar();
        initFields();
        initTextListeners();

        mDatabaseSubjects = FirebaseDatabase.getInstance().getReference("Subjects");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseSubjectProgress = FirebaseDatabase.getInstance().getReference("SubjectProgress");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_add_homework, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.confirm:
                String subjectName = edtSubjectName.getText().toString().trim();
                if (TextUtils.isEmpty(subjectName)) {
                    tiSubjectName.setError(getString(R.string.enter_name_subj));
                } else {
                    String subjectId = mDatabaseSubjects.push().getKey();
                    Subject subject = new Subject(subjectId, subjectName, MainActivity.groupId);
                    mDatabaseSubjects.child(subjectId).setValue(subject);

                    mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                String userId = user.getId();

                                if (user.getGroupId().equals(MainActivity.groupId)) {
                                    String subProgressId = mDatabaseSubjectProgress.push().getKey();
                                    SubjectProgress subjectProgress = new SubjectProgress(subProgressId, userId, subjectId, 0);
                                    mDatabaseSubjectProgress.child(MainActivity.groupId).child(subjectId)
                                            .child(subProgressId).setValue(subjectProgress);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initFields() {
        tiSubjectName = findViewById(R.id.ti_subject_name);
        edtSubjectName = findViewById(R.id.edt_subject_name);
    }

    private void initTextListeners() {
        edtSubjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tiSubjectName.setError(null);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_activity_add_subject);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Добавить предмет");
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
