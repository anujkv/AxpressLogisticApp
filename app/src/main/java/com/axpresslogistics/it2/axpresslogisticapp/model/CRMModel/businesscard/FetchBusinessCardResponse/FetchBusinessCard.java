package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.FetchBusinessCardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchBusinessCard {
    @SerializedName("frontimage")
    @Expose
    private String frontimage;
    @SerializedName("backimage")
    @Expose
    private String backimage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("mobile1")
    @Expose
    private String mobile1;
    @SerializedName("mobile2")
    @Expose
    private String mobile2;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("image_barcode")
    @Expose
    private String imageBarcode;
    @SerializedName("address")
    @Expose
    private String address;

    public String getFrontimage() {
        return frontimage;
    }

    public void setFrontimage(String frontimage) {
        this.frontimage = frontimage;
    }

    public String getBackimage() {
        return backimage;
    }

    public void setBackimage(String backimage) {
        this.backimage = backimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImageBarcode() {
        return imageBarcode;
    }

    public void setImageBarcode(String imageBarcode) {
        this.imageBarcode = imageBarcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
