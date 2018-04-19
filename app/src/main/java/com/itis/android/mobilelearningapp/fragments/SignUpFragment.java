package com.itis.android.mobilelearningapp.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.FragmentHostActivity;
import com.itis.android.mobilelearningapp.utils.SoftKeyboard;

public class SignUpFragment extends Fragment {

    private Spinner spinner;

    private TextInputLayout tiFirstName, tiLastName, tiEmail, tiPassword;
    private EditText edtFirstNameField, edtLastNameField, edtEmailField, edtPasswordField;
    private Button btnSignUp;
    private View container;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;

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

        initToolbar();
        initFields(view);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.group_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initClickListeners();
        initTextListeners();


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

    private void initFields(View view) {
        progressDialog = new ProgressDialog(getActivity());
        container = view.findViewById(R.id.linear_layout_container_sign_up);
        spinner = view.findViewById(R.id.spinner);
        tiFirstName = view.findViewById(R.id.ti_first_name);
        tiLastName = view.findViewById(R.id.ti_last_name);
        tiEmail = view.findViewById(R.id.ti_email2);
        tiPassword = view.findViewById(R.id.ti_password2);
        edtFirstNameField = view.findViewById(R.id.edt_first_name);
        edtLastNameField = view.findViewById(R.id.edt_last_name);
        edtEmailField = view.findViewById(R.id.edt_email2);
        edtPasswordField = view.findViewById(R.id.edt_password2);
        btnSignUp = view.findViewById(R.id.btn_to_sign_up);
    }

    private void initTextListeners() {
        edtEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tiEmail.setError(null);
            }
        });
        edtPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tiPassword.setError(null);
            }
        });
    }

    private void initClickListeners() {

        btnSignUp.setOnClickListener(v -> {
            startRegister();
        });
    }

    private void startRegister() {
        String email = edtEmailField.getText().toString().trim();
        String password = edtPasswordField.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            // todo validation
            tiEmail.setError(getString(R.string.error_email));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            // todo validation
            tiPassword.setError(getString(R.string.error_pass));
            return;
        }
        if (password.length() < 6) {
            // todo validation
            tiPassword.setError(getString(R.string.error_pass_length));
            return;
        }
        SoftKeyboard.hide(container);
        progressDialog.setMessage("Signing Up ...");
        progressDialog.show();
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Snackbar.make(container, "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), R.string.status_registration_succeeded, Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });
    }
    // todo вынесети в отдельный класс
    private void initToolbar() {
        FragmentHostActivity activity = (FragmentHostActivity) getActivity();
        Toolbar toolbar = activity.getToolbar();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText("Зарегистрироваться");
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
