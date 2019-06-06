package com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchModelListResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("brnsearch")
    @Expose
    private List<Brnsearch> brnsearch = null;
    @SerializedName("status")
    @Expose
    private String status;

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

    public List<Brnsearch> getBrnsearch() {
        return brnsearch;
    }

    public void setBrnsearch(List<Brnsearch> brnsearch) {
        this.brnsearch = brnsearch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}