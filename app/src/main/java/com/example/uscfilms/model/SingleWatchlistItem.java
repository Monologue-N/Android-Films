package com.example.uscfilms.model;

public class SingleWatchlistItem {
    String id;
    String type;
    String poster_path;

    public SingleWatchlistItem(String id, String type, String poster_path) {
        this.id = id;
        this.type = type;
        this.poster_path = poster_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

}
