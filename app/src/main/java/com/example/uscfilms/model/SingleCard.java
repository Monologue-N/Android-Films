package com.example.uscfilms.model;

public class SingleCard {
    private String id;
    private String backdrop_path;
    private String title;
    private String type;

    public SingleCard() {
    }

    public SingleCard(String id, String backdrop_path, String title, String type) {
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
