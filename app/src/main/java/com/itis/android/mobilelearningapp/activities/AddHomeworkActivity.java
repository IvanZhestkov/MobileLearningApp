package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.fragments.MyDatePickerFragment;
import com.itis.android.mobilelearningapp.models.Homework;

public class AddHomeworkActivity extends AppCompatActivity {

    private static final String SUBJECT_ID = "subjectId";

    private TextInputLayout tiTitle, tiStartDate, tiEndDate, tiDesc;
    private EditText edtTitle, edtStartDate, edtEndDate, edtDesc;

    private DatabaseReference mDatabaseHomeworks;

    private Toolbar toolbar;

    public static Intent makeInflatedIntent(Context context, String subjectId) {
        Intent intent = makeIntent(context);
        intent.putExtra(SUBJECT_ID, subjectId);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddHomeworkActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        initToolbar();
        initFields();
        initTextListeners();

        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Homeworks");
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
                String subjectId = getIntent().getStringExtra(SUBJECT_ID);
                String title = edtTitle.getText().toString().trim();
                String startDate = edtStartDate.getText().toString().trim();
                String endDate = edtEndDate.getText().toString().trim();
                String desc = edtDesc.getText().toString().trim();

                if (!hasErrors(title, startDate, endDate, desc)) {
                    String homeworkId = mDatabaseHomeworks.push().getKey();
                    Homework homework = new Homework(homeworkId, title, desc, subjectId, startDate, endDate);
                    mDatabaseHomeworks.child(subjectId).child(homeworkId).setValue(homework);
                    onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean hasErrors(String title, String startDate, String endDate, String desc) {
        boolean isError = false;

        if (TextUtils.isEmpty(title)) {
            tiTitle.setError("Введите название");
            isError = true;
        }
        if (TextUtils.isEmpty(startDate)) {
            tiStartDate.setError("Введите начальную дату");
            isError = true;
        }
        if (TextUtils.isEmpty(endDate)) {
            tiEndDate.setError("Введите конечную дату");
            isError = true;
        }
        if (TextUtils.isEmpty(desc)) {
            tiDesc.setError("Введите описание");
            isError = true;
        }

        return isError;
    }

    private void initFields() {
        edtTitle = findViewById(R.id.edt_title);
        edtStartDate = findViewById(R.id.edt_start_date);
        edtEndDate = findViewById(R.id.edt_end_date);
        edtDesc = findViewById(R.id.edt_hw_description);
        tiTitle = findViewById(R.id.ti_title);
        tiStartDate = findViewById(R.id.ti_start_date);
        tiEndDate = findViewById(R.id.ti_end_date);
        tiDesc = findViewById(R.id.ti_hw_description);
    }

    private void initTextListeners() {
        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtTitle.setError(null);
            }
        });

        edtStartDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtStartDate.setError(null);
            }
        });

        edtEndDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtEndDate.setError(null);
            }
        });

        edtDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtDesc.setError(null);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_activity_add_homework);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Добавить ДЗ");
        }
    }

    public void showDatePicker(View view) {
        switch (view.getId()) {
            case R.id.edt_start_date:
                DialogFragment dialogFragment = MyDatePickerFragment.newInstance(R.id.edt_start_date);
                dialogFragment.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.edt_end_date:
                DialogFragment dialogFragment2 = MyDatePickerFragment.newInstance(R.id.edt_end_date);
                dialogFragment2.show(getSupportFragmentManager(), "date picker2");
                break;
        }
    }
}
