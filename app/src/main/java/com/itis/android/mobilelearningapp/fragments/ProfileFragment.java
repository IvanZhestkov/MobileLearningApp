package com.itis.android.mobilelearningapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.MainActivity;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(args);
        return profileFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseUser user = mAuth.getCurrentUser();

        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
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

        initToolbar();

        return view;
    }

    private void setUserData(String userName) {
        MainActivity activity = (MainActivity) getActivity();
        Toolbar toolbar = activity.getToolbarProfile();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            TextView mTitle = toolbar.findViewById(R.id.toolbar_profile_title);
            mTitle.setText(userName);
        }
    }

    private void initToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }
}
