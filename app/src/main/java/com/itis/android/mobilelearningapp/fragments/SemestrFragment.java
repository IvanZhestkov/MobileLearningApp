package com.itis.android.mobilelearningapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.mobilelearningapp.R;

public class SemestrFragment extends Fragment {

    public static SemestrFragment newInstance() {
        Bundle args = new Bundle();
        SemestrFragment semestrFragment = new SemestrFragment();
        semestrFragment.setArguments(args);
        return semestrFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.semestr_fragment_profile, container, false);

        return view;
    }
}
