package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.iv_splash);
        textView = findViewById(R.id.tv_splash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);

        new Handler().postDelayed(() -> {
            startActivity(StartActivity.makeIntent(SplashActivity.this));
            finish();
        }, 2 * 1000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
