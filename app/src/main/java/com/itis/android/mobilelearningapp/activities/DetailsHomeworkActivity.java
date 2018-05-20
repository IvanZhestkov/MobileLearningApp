package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.models.Homework;
import com.itis.android.mobilelearningapp.utils.Constants;

import static com.itis.android.mobilelearningapp.utils.Constants.HOMEWORK_ITEM;

public class DetailsHomeworkActivity extends AppCompatActivity {

    private TextView tvTitle, tvStartDate, tvEndDate, tvDescription;

    private Toolbar toolbar;

    public static Intent makeInflatedIntent(Context context, Homework homework) {
        Intent intent = makeIntent(context);
        intent.putExtra(HOMEWORK_ITEM, homework);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, DetailsHomeworkActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_homework);

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

    private void updateUi() {
        Homework homework = getIntent().getParcelableExtra(HOMEWORK_ITEM);

        tvDescription.setText(homework.getDescription());
        tvTitle.setText(homework.getName());
        tvStartDate.setText(homework.getStartDate());
        tvEndDate.setText(homework.getEndDate());
    }

    private void initFields() {
        tvTitle = findViewById(R.id.tv_title_details_hw);
        tvStartDate = findViewById(R.id.tv_start_date_details_hw);
        tvEndDate = findViewById(R.id.tv_end_date_details_hw);
        tvDescription = findViewById(R.id.tv_description_details_hw);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_activity_details_homework);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Details");
        }
    }
}
