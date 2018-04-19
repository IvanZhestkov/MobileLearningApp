package com.itis.android.mobilelearningapp.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.itis.android.mobilelearningapp.fragments.SignInFragment;

public class SignInAndSignUpActivity extends FragmentHostActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, SignInAndSignUpActivity.class);
    }

    @Override
    protected Fragment getFragment() {
        return SignInFragment.newInstance();
    }
}
