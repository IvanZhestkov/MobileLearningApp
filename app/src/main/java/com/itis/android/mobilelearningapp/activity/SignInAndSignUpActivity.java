package com.itis.android.mobilelearningapp.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.fragments.FragmentHostActivity;
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
