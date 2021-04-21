package com.example.uscfilms.model;

public class SingleSearchResult {
    String id;
    String backdrop_path;
    String type;
    String year;
    String title;
    String rating;

    public SingleSearchResult(String id, String backdrop_path, String type, String year, String title, String rating) {
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.type = type;
        this.year = year;
        this.title = title;
        this.rating = rating;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
