package com.example.it2.axpresslogisticapp.model;

public class SavedCardModel {
    String user_image;
    String username, website, email;

    public SavedCardModel(String username) {
        this.user_image = user_image;
        this.username = username;
        this.website = website;
        this.email = email;
    }

    public SavedCardModel(String user_image, String username, String website, String email) {
        this.user_image = user_image;
        this.username = username;
        this.website = website;
        this.email = email;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
