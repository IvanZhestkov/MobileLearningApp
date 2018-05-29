package com.itis.android.mobilelearningapp.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.R;
import com.itis.android.mobilelearningapp.activities.StudentProgressActivity;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;
import com.itis.android.mobilelearningapp.adapters.GroupFragmentAdapter;
import com.itis.android.mobilelearningapp.models.User;
import com.itis.android.mobilelearningapp.viewholders.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupFragment extends Fragment implements OnItemClickListener {

    private RecyclerView recyclerView;

    private ProgressBar progressBar;

    private List<User> users;

    private LinearLayoutManager layoutManager;

    private GroupFragmentAdapter adapter;

    private DatabaseReference mDatabaseUsers;

    public static GroupFragment newInstance() {
        Bundle args = new Bundle();
        GroupFragment groupFragment = new GroupFragment();
        groupFragment.setArguments(args);
        return groupFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        initToolbar();
        initFields(view);

        users = new ArrayList<>();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        checkIfEmpty();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user.getGroupId().equals(MainActivity.groupId)) {
                        users.add(user);
                    }
                }
                Log.d("usersList", users.size() + "");
                checkIfEmpty();

                Collections.sort(users, Collections.reverseOrder(User.COMPARE_BY_RATE));

                layoutManager = new LinearLayoutManager(getActivity());
                adapter = new GroupFragmentAdapter(users, GroupFragment.this);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFields(View view) {
        recyclerView = view.findViewById(R.id.rv_group);
        progressBar = view.findViewById(R.id.pg_fragment_study);
    }

    private void initToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().hide();
    }

    private void checkIfEmpty() {
        if (users.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(int position) {
        User user = users.get(position);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = firstName + " " + lastName;

        startActivity(StudentProgressActivity.makeInflatedIntent(getActivity(), user.getId(), userName));
    }
}
