package com.itis.android.mobilelearningapp.models;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itis.android.mobilelearningapp.activities.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TitleCreator {

    private static TitleCreator titleCreator;

    private List<TitleParent> titleParents;

    private DatabaseReference mDatabaseHomeworks;

    public TitleCreator(Context context) {
        titleParents = new ArrayList<>();
        mDatabaseHomeworks = FirebaseDatabase.getInstance().getReference("Subjects");

        mDatabaseHomeworks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titleParents.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    Subject subject = subjectSnapshot.getValue(Subject.class);
                    if (subject != null && subject.getGroupId().equals(MainActivity.groupId)) {
                        TitleParent titleParent = new TitleParent(subject);
                        titleParents.add(titleParent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static TitleCreator get(Context context) {
        if (titleCreator == null) {
            titleCreator = new TitleCreator(context);
        }
        return titleCreator;
    }

    public List<TitleParent> getAll() {
        return titleParents;
    }
}
