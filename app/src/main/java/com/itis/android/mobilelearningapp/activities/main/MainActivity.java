package com.itis.android.mobilelearningapp.activities.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.instruction.StartActivity;
import com.itis.android.mobilelearningapp.fragments.main.GroupFragment;
import com.itis.android.mobilelearningapp.fragments.main.ProfileFragment;
import com.itis.android.mobilelearningapp.fragments.main.StudyFragment;
import com.itis.android.mobilelearningapp.models.User;

public class MainActivity extends AppCompatActivity {

    public static String groupId;
    public static User userr;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private GroupFragment groupFragment;
    private StudyFragment studyFragment;
    private ProfileFragment profileFragment;

    private Toolbar toolbarProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser user;

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
        initFields();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //todo added good checking for user
        if (user == null) {
            // Not signed in, launch the Sign In activity
            startActivity(StartActivity.makeIntent(this));
            return;
        }

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstName = (String) dataSnapshot.child("firstName").getValue();
                String lastName = (String) dataSnapshot.child("lastName").getValue();
                setUserData(firstName + " " + lastName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user != null) {

            mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        Log.d("USER_ID", user.getGroupId());
                        groupId = user.getGroupId();
                        userr = user;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
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
                TextView textView = toolbarProfile.findViewById(R.id.toolbar_title);
                String name = textView.getText().toString();
                Log.d("name = ", name);
                startActivity(EditProfileActivity.makeInflatedIntent(this, name));
                return true;
            case R.id.log_out:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initFields() {
        mMainNav = findViewById(R.id.main_navigation);
        mMainFrame = findViewById(R.id.main_fragment_container);
    }

    private void setNavigation() {
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

    private void setUserData(String userName) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            TextView mTitle = toolbarProfile.findViewById(R.id.toolbar_title);
            mTitle.setText(userName);
        }
    }

    private void logOut() {
        mAuth.signOut();
        user = null;
        startActivity(StartActivity.makeIntent(this));
        finish();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initToolbar() {
        toolbarProfile = findViewById(R.id.toolbar);
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
