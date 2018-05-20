package com.itis.android.mobilelearningapp.models;

public class SubjectProgress {

    private String id;

    private String userId;

    private String subjectId;

    private int rate;

    public SubjectProgress() {
    }

    public SubjectProgress(String id, String userId, String subjectId, int rate) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
