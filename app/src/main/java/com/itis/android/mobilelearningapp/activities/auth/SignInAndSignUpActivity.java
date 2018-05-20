package com.itis.android.mobilelearningapp.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.itis.android.mobilelearningapp.activities.FragmentHostActivity;
import com.itis.android.mobilelearningapp.fragments.auth.SignInFragment;

public class SignInAndSignUpActivity extends FragmentHostActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, SignInAndSignUpActivity.class);
    }

    @Override
    protected Fragment getFragment() {
        return SignInFragment.newInstance();
    }
}
