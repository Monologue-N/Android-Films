package com.example.uscfilms.model;

public class SingleCast {
    private String profile_path;
    private String name;
    private String id;

    public SingleCast(String profile_path, String name, String id) {
        this.profile_path = profile_path;
        this.name = name;
        this.id = id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
