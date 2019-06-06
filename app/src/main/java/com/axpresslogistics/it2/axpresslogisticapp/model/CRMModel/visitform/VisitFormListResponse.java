package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VisitFormListResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("saved_list")
    @Expose
    private List<SavedList> savedList = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("emplid")
    @Expose
    private String emplid;

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

    public List<SavedList> getSavedList() {
        return savedList;
    }

    public void setSavedList(List<SavedList> savedList) {
        this.savedList = savedList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

}