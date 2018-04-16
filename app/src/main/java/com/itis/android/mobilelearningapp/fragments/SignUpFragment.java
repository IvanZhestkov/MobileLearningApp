package com.itis.android.mobilelearningapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itis.android.mobilelearningapp.R;

public class SignUpFragment extends Fragment {

    private Spinner spinner;

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.setArguments(args);
        return signUpFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.group_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
