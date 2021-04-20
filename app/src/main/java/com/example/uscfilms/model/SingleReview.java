package com.example.uscfilms.model;

public class SingleReview {
    private String creation;
    private String rating;
    private String review;

    public SingleReview(String creation, String rating, String review) {
        this.creation = creation;
        this.rating = rating;
        this.review = review;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
