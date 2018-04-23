package com.itis.android.mobilelearningapp.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.MainActivity;

public class EditProfileFragment extends Fragment {

    private EditText edtFirstName;
    private EditText edtLastName;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        editProfileFragment.setArguments(args);
        return editProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        initFields(view);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        setHasOptionsMenu(true);
        initToolbar();
        return view;
    }

    private void initFields(View view) {
        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_edit_profile, menu);
        MenuItem itemEditName = menu.findItem(R.id.edit_name);
        MenuItem itemLogOut = menu.findItem(R.id.log_out);
        itemEditName.setVisible(false);
        itemLogOut.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.confirm:
                onBackPressed();
                updateUserName(user.getUid(), edtFirstName.getText().toString(), edtLastName.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUserName(String id, String firstName, String lastName) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        mDatabase.child("firstName").setValue(firstName);
        mDatabase.child("lastName").setValue(lastName);
    }

    private void initToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        Toolbar toolbar = activity.getToolbarProfile();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            TextView mTitle = toolbar.findViewById(R.id.toolbar_profile_title);
            mTitle.setText(R.string.edit_name);
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
