package com.example.it2.axpresslogisticapp.model;

public class SaveCardModel {

    String image;
    String username, website, email;

    public SaveCardModel(String image, String username, String website, String email) {
        this.image = image;
        this.username = username;
        this.website = website;
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }
}
