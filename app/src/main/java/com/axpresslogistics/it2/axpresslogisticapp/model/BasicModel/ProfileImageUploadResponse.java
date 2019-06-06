package com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileImageUploadResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
