package com.itis.android.mobilelearningapp.models;

import com.itis.android.mobilelearningapp.utils.UserRole;

import java.util.Comparator;

public class User {

    private String id;

    private String firstName;

    private String lastName;

    private String groupId;

    private UserRole role;

    private int rate;

    public User() {
    }

    public User(String id, String firstName, String lastName, String groupId, UserRole role, int rate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
        this.role = role;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public static final Comparator<User> COMPARE_BY_RATE = (user, user2) -> user.getRate() - user2.getRate();
}
