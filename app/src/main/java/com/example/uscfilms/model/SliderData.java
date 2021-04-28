package com.example.uscfilms.model;

public class SliderData {

    // image url is used to
    // store the url of image
    private String imgUrl;
    private String id;
    private String type;

    // Constructor method.
    public SliderData(String imgUrl, String id, String type) {
        this.imgUrl = imgUrl;
        this.id = id;
        this.type = type;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}