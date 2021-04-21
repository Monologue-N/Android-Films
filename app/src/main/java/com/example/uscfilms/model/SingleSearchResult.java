package com.example.uscfilms.model;

public class SingleSearchResult {
    String backdrop_path;
    String type_year;
    String title;
    String rating;

    public SingleSearchResult(String backdrop_path, String type_year, String title, String rating) {
        this.backdrop_path = backdrop_path;
        this.type_year = type_year;
        this.title = title;
        this.rating = rating;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getType_year() {
        return type_year;
    }

    public void setType_year(String type_year) {
        this.type_year = type_year;
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
