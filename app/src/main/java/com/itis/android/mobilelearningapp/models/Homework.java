package com.itis.android.mobilelearningapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Homework implements Parcelable {

    private String id;

    private String name;

    private String description;

    private String subjectId;

    private String startDate;

    private String endDate;

    public Homework() {
    }

    public Homework(String id, String name, String description, String subjectId, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subjectId = subjectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.subjectId);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }

    protected Homework(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.subjectId = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
    }

    public static final Parcelable.Creator<Homework> CREATOR = new Parcelable.Creator<Homework>() {
        @Override
        public Homework createFromParcel(Parcel source) {
            return new Homework(source);
        }

        @Override
        public Homework[] newArray(int size) {
            return new Homework[size];
        }
    };
}
