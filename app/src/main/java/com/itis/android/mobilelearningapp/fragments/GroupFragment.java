package com.itis.android.mobilelearningapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.MainActivity;

import java.util.Objects;

public class GroupFragment extends Fragment {

    public static GroupFragment newInstance() {
        Bundle args = new Bundle();
        GroupFragment groupFragment = new GroupFragment();
        groupFragment.setArguments(args);
        return groupFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().hide();
    }
}
