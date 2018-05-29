package com.itis.android.mobilelearningapp.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.fragments.HometasksFragment;
import com.itis.android.mobilelearningapp.fragments.SemestrFragment;
import com.itis.android.mobilelearningapp.fragments.ViewPagerAdapter;

public class ProfileFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        initToolbar();
        initFields(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(HometasksFragment.newInstance(), "Hometasks");
        viewPagerAdapter.addFragment(SemestrFragment.newInstance(), "Semestr");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    private void initToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    private void initFields(View view) {
        //tabLayout = view.findViewById(R.id.tb_layout);
        //viewPager = view.findViewById(R.id.view_pager_fragment_profile);
    }
}
