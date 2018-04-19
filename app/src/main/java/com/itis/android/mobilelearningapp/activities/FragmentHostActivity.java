package com.itis.android.mobilelearningapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.itis.android.mobilelearningapp.R;

public abstract class FragmentHostActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        toolbar = findViewById(R.id.toolbar);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(getContainerId()) == null) {
            fm.beginTransaction()
                    .add(getContainerId(), getFragment())
                    .commit();
        }
    }

    protected abstract Fragment getFragment();

    protected int getLayoutResId() {
        return R.layout.activity_fragment_host;
    }

    private int getContainerId() {
        return R.id.fragment_container;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
