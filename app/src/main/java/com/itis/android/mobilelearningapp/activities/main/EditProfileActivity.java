package com.itis.android.mobilelearningapp.activities.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itis.android.mobilelearningapp.R;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtFirstName;
    private EditText edtLastName;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    private Toolbar toolbar;

    public static Intent makeInflatedIntent(Context context, String name) {
        Intent intent = makeIntent(context);
        intent.putExtra("userName", name);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EditProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initToolbar();
        initFields();
        setEditTextFields();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.confirm:
                if (!TextUtils.isEmpty(edtFirstName.getText().toString()) &&
                        !TextUtils.isEmpty(edtLastName.getText().toString())) {
                    onBackPressed();
                    updateUserName(user.getUid(), edtFirstName.getText().toString(), edtLastName.getText().toString());
                } else {
                    Toast.makeText(this, "All the fields must be filled in", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initFields() {
        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
    }

    private void setEditTextFields() {
        String name = getIntent().getStringExtra("userName");
        String firstName, lastName;
        if (name != null && !name.equals("")) {
            String[] s = name.split(" ");
            firstName = s[s.length - 2];
            lastName = s[s.length - 1];
        } else {
            firstName = "";
            lastName = "";
        }
        edtFirstName.setText(firstName);
        edtLastName.setText(lastName);
    }

    private void updateUserName(String id, String firstName, String lastName) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(id);
        mDatabase.child("firstName").setValue(firstName);
        mDatabase.child("lastName").setValue(lastName);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Изменить имя");
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
