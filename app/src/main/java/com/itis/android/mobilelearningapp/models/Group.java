package com.itis.android.mobilelearningapp.models;

import java.util.List;

public class Group {

    private String id;

    private String name;

    public Group() {
    }

    public Group(String id, String name, List<User> users) {
        this.id = id;
        this.name = name;
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
}
