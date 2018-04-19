package com.itis.android.mobilelearningapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.fragments.GroupFragment;
import com.itis.android.mobilelearningapp.fragments.ProfileFragment;
import com.itis.android.mobilelearningapp.fragments.StudyFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private GroupFragment groupFragment;
    private StudyFragment studyFragment;
    private ProfileFragment profileFragment;

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainNav = findViewById(R.id.main_navigation);
        mMainFrame = findViewById(R.id.main_frame_layout);

        groupFragment = GroupFragment.newInstance();
        studyFragment = StudyFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        setFragment(studyFragment);

        mMainNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_group:
                    mMainNav.setItemBackgroundResource(R.color.primary);
                    setFragment(groupFragment);
                    return true;
                case R.id.nav_study:
                    mMainNav.setItemBackgroundResource(R.color.accent);
                    setFragment(studyFragment);
                    return true;
                case R.id.nav_profile:
                    mMainNav.setItemBackgroundResource(R.color.primary_dark);
                    setFragment(profileFragment);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
