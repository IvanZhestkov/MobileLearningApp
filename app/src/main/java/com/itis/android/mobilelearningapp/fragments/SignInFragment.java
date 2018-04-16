package com.itis.android.mobilelearningapp.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;

public class SignInFragment extends Fragment {

    private TextView btnSignUp;

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setArguments(args);
        return signInFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSignUp = view.findViewById(R.id.btn_to_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.card_flip_right_enter,
                                R.anim.card_flip_right_exit,
                                R.anim.card_flip_left_enter,
                                R.anim.card_flip_left_exit)
                        .replace(R.id.fragment_container, SignUpFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
