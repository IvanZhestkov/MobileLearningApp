package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;

public class StartActivity extends AppCompatActivity {

    private View container;

    private ViewPager sliderViewPager;
    private LinearLayout dotsLayout;

    private SliderAdapter sliderAdapter;

    private TextView[] dots;

    private Button nextBtn;
    private Button backBtn;

    private int currentPage;

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initFields();
        initClickListeners();

        sliderAdapter = new SliderAdapter(this);
        sliderViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;

            if (position == 0) {
                nextBtn.setEnabled(true);
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText(getResources().getString(R.string.next_btn));
                backBtn.setText("");
            } else if (position == dots.length - 1) {
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText(getResources().getString(R.string.start_btn));
                backBtn.setText(getResources().getString(R.string.back_btn));
            } else {
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText(getResources().getString(R.string.next_btn));
                backBtn.setText(getResources().getString(R.string.back_btn));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void addDotsIndicator(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorGrey));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorBlue));
        }
    }

    private void initFields() {
        container = findViewById(R.id.container_activity_start);
        sliderViewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.dots_layout);
        backBtn = findViewById(R.id.btn_back);
        nextBtn = findViewById(R.id.btn_next);
    }

    private void initClickListeners() {
        sliderViewPager.addOnPageChangeListener(viewListener);

        nextBtn.setOnClickListener(view -> {
            if (nextBtn.getText() == getResources().getString(R.string.start_btn)) {
                startActivity(SignInAndSignUpActivity.makeIntent(StartActivity.this));
            }
            sliderViewPager.setCurrentItem(currentPage + 1);
        });

        backBtn.setOnClickListener(view -> sliderViewPager.setCurrentItem(currentPage - 1));
    }
}

