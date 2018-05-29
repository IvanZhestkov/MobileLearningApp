package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.models.SubjectProgress;

import static com.itis.android.mobilelearningapp.utils.Constants.HOMEWORK_ITEM;
import static com.itis.android.mobilelearningapp.utils.Constants.RATE;
import static com.itis.android.mobilelearningapp.utils.Constants.SUBJECT_ID;
import static com.itis.android.mobilelearningapp.utils.Constants.SUBJECT_NAME;

public class FinishHomeworkActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static final String COUNT_HWS = "countHws";

    private TextView tvTitle, tvStartDate, tvEndDate, tvDescription, tvRate;
    private SeekBar seekBar;
    private Button btnSave;
    private CheckBox checkBox;

    private Toolbar toolbar;

    private DatabaseReference mDatabaseHomeworks;
    private DatabaseReference mDatabaseSubProgress;

    public static Intent makeInflatedIntent(Context context, Homework homework,
                                            String subjectId, int rate, String subjectName, int count) {
        Intent intent = makeIntent(context);
        intent.putExtra(HOMEWORK_ITEM, homework);
        intent.putExtra(SUBJECT_ID, subjectId);
        intent.putExtra(RATE, rate);
        intent.putExtra(SUBJECT_NAME, subjectName);
        intent.putExtra(COUNT_HWS, count);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, FinishHomeworkActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_homework);

        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Homeworks");
        mDatabaseSubProgress = FirebaseDatabase.getInstance().getReference("SubjectProgress").child(MainActivity.groupId);

        initToolbar();
        initFields();

        updateUi();
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

    private void initFields() {
        tvTitle = findViewById(R.id.tv_title_details_hw);
        tvStartDate = findViewById(R.id.tv_start_date_details_hw);
        tvEndDate = findViewById(R.id.tv_end_date_details_hw);
        tvDescription = findViewById(R.id.tv_description_details_hw);
        seekBar = findViewById(R.id.seekBar);
        btnSave = findViewById(R.id.btn_save_data);
        tvRate = findViewById(R.id.tv_rate2);
        checkBox = findViewById(R.id.checkBox);
    }

    private void updateUi() {
        Homework homework = getIntent().getParcelableExtra(HOMEWORK_ITEM);
        int rate = getIntent().getIntExtra(RATE, -1);

        seekBar.setOnSeekBarChangeListener(this);
        btnSave.setOnClickListener(this);

        tvTitle.setText(homework.getName());
        tvStartDate.setText(homework.getStartDate());
        tvEndDate.setText(homework.getEndDate());
        tvDescription.setText(homework.getDescription());
        tvRate.setText(String.valueOf(rate) + "%");
        seekBar.setProgress(rate);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_activity_finish_homework);
        String subjectName = getIntent().getStringExtra(SUBJECT_NAME);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(subjectName);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        tvRate.setText(String.valueOf(seekBar.getProgress()) + "%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View view) {
        int countHws = getIntent().getIntExtra(COUNT_HWS, 1);
        String subjectId = getIntent().getStringExtra(SUBJECT_ID);
        mDatabaseSubProgress.child(subjectId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot subProgressSnapshot : dataSnapshot.getChildren()) {
                            SubjectProgress subjectProgress = subProgressSnapshot.getValue(SubjectProgress.class);
                            if (subjectProgress.getUserId().equals(MainActivity.userr.getId())) {
                                String rate = tvRate.getText().toString();
                                int ratee = Integer.parseInt(rate.substring(0, rate.length() - 1));
                                subjectProgress.setRate(ratee);
                                if (checkBox.isChecked()) {
                                    int result = (subjectProgress.getRateSubject() + ratee) / countHws;
                                    if (result <= 100) {
                                        subjectProgress.setRateSubject(result);
                                    }
                                }
                                mDatabaseSubProgress.child(subjectId)
                                        .child(subjectProgress.getId()).setValue(subjectProgress);
                                onBackPressed();
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
