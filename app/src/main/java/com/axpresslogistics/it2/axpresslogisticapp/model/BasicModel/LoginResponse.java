package com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse  {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("resMassage")
    @Expose
    private Object resMassage;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("employee_designation")
    @Expose
    private String employeeDesignation;
    @SerializedName("employee_department")
    @Expose
    private String employeeDepartment;
    @SerializedName("employee_branch")
    @Expose
    private String employeeBranch;
    @SerializedName("supervisior_id")
    @Expose
    private String supervisiorId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("imei_status")
    @Expose
    private String imeiStatus;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResMassage() {
        return resMassage;
    }

    public void setResMassage(Object resMassage) {
        this.resMassage = resMassage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getEmployeeBranch() {
        return employeeBranch;
    }

    public void setEmployeeBranch(String employeeBranch) {
        this.employeeBranch = employeeBranch;
    }

    public String getSupervisiorId() {
        return supervisiorId;
    }

    public void setSupervisiorId(String supervisiorId) {
        this.supervisiorId = supervisiorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImeiStatus() {
        return imeiStatus;
    }

    public void setImeiStatus(String imeiStatus) {
        this.imeiStatus = imeiStatus;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}