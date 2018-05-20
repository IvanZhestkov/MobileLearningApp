package com.itis.android.mobilelearningapp.models;

import android.widget.ProgressBar;

public class TitleChild {

    private SubjectProgress subjectProgress;

    public TitleChild(SubjectProgress subjectProgress) {
        this.subjectProgress = subjectProgress;
    }

    public SubjectProgress getSubjectProgress() {
        return subjectProgress;
    }

    public void setSubjectProgress(SubjectProgress subjectProgress) {
        this.subjectProgress = subjectProgress;
    }
}
