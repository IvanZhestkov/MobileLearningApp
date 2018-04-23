package com.itis.android.mobilelearningapp.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.FragmentHostActivity;
import com.itis.android.mobilelearningapp.activities.MainActivity;
import com.itis.android.mobilelearningapp.activities.StartActivity;
import com.itis.android.mobilelearningapp.utils.SoftKeyboard;

public class SignInFragment extends Fragment {

    //private TextInputLayout tiEmail, tiPassword;
    private EditText edtEmailField, edtPasswordField;
    private Button btnSignIn;
    private View container;
    private TextView btnLinkSignUp;
    private TextView btnLinkRestorePass;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;


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

        initToolbar();
        initFields(view);

        auth = FirebaseAuth.getInstance();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initTextListeners();
        initClickListeners();
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
        container = view.findViewById(R.id.linear_layout_container_sign_in);
        /*tiEmail = view.findViewById(R.id.ti_email);
        tiPassword = view.findViewById(R.id.ti_password);*/
        edtEmailField = view.findViewById(R.id.edt_email);
        edtPasswordField = view.findViewById(R.id.edt_password);
        btnLinkSignUp = view.findViewById(R.id.btn_link_to_sign_up);
        btnLinkRestorePass = view.findViewById(R.id.btn_link_to_reset_password);
        btnSignIn = view.findViewById(R.id.btn_to_sign_in);
    }

    /*private void initTextListeners() {
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
    }*/

    private void initClickListeners() {
        btnLinkSignUp.setOnClickListener(view1 -> getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, SignUpFragment.newInstance())
                .addToBackStack(null)
                .commit());

        btnLinkRestorePass.setOnClickListener(view -> getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, RestorePasswordFragment.newInstance())
                .addToBackStack(null)
                .commit());

        btnSignIn.setOnClickListener(v ->
                startLogIn());
    }

    private void startLogIn() {
        String email = edtEmailField.getText().toString();
        final String password = edtPasswordField.getText().toString();

        if (TextUtils.isEmpty(email)) {
            // todo validation
            edtEmailField.setError(getString(R.string.error_email));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            // todo validation
            edtPasswordField.setError(getString(R.string.error_pass));
            return;
        }

        if (password.length() < 6) {
            // todo validation
            edtPasswordField.setError(getString(R.string.error_pass_length));
            return;
        }

        progressDialog.setMessage("Signing In ...");
        progressDialog.show();
        SoftKeyboard.hide(container);

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        // there was an error
                        progressDialog.dismiss();
                        Snackbar.make(container, R.string.error_sign_in, Snackbar.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        startActivity(MainActivity.makeIntent(getActivity()));
                        getActivity().finish();
                    }
                });
    }

    //todo вынесети в отдельный класс
    private void initToolbar() {
        FragmentHostActivity activity = (FragmentHostActivity) getActivity();
        Toolbar toolbar = activity.getToolbar();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText("Войти");
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
