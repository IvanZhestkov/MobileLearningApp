package com.itis.android.mobilelearningapp.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.FragmentHostActivity;

public class RestorePasswordFragment extends Fragment {

    private EditText edtResetPassField;
    private Button btnResetPass;
    private FirebaseAuth firebaseAuth;

    public static RestorePasswordFragment newInstance() {
        Bundle args = new Bundle();
        RestorePasswordFragment restorePassFragment = new RestorePasswordFragment();
        restorePassFragment.setArguments(args);
        return restorePassFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restore_password, container, false);
        initToolbar();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        edtResetPassField = view.findViewById(R.id.edt_reset_pass);
        btnResetPass = view.findViewById(R.id.btn_reset_pass);

        btnResetPass.setOnClickListener(view1 -> {

            String email = edtResetPassField.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                edtResetPassField.setError("Please enter your email");
            } else {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(),
                                getString(R.string.reset_password_complete_msg), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // todo вынесети в отдельный класс
    private void initToolbar() {
        FragmentHostActivity activity = (FragmentHostActivity) getActivity();
        Toolbar toolbar = activity.getToolbar();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText("");
        }
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        else
            getActivity().finish();
    }
}
