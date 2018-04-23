package com.itis.android.mobilelearningapp.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.fragments.EditProfileFragment;
import com.itis.android.mobilelearningapp.fragments.GroupFragment;
import com.itis.android.mobilelearningapp.fragments.ProfileFragment;
import com.itis.android.mobilelearningapp.fragments.StudyFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private GroupFragment groupFragment;
    private StudyFragment studyFragment;
    private ProfileFragment profileFragment;

    private Toolbar toolbarProfile;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

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

        initToolbar();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(StartActivity.makeIntent(this));
            return;
        }


        mMainNav = findViewById(R.id.main_navigation);
        mMainFrame = findViewById(R.id.main_fragment_container);

        groupFragment = GroupFragment.newInstance();
        studyFragment = StudyFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        setFragment(groupFragment);

        mMainNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_group:
                    setFragment(groupFragment);
                    return true;
                case R.id.nav_study:
                    setFragment(studyFragment);
                    return true;
                case R.id.nav_profile:
                    setFragment(profileFragment);
                    return true;
                default:
                    return false;
            }
        });
    }

    //todo change color menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_name:
                getFragmentManager()
                        .beginTransaction()
                        .setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        //todo throw off title toolbar
                        .replace(R.id.main_fragment_container, EditProfileFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.log_out:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        mAuth.signOut();
        firebaseUser = null;
        startActivity(StartActivity.makeIntent(this));
        finish();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initToolbar() {
        toolbarProfile = findViewById(R.id.toolbar_fragment_profile);
        setSupportActionBar(toolbarProfile);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }
    }

    public Toolbar getToolbarProfile() {
        return toolbarProfile;
    }
}
