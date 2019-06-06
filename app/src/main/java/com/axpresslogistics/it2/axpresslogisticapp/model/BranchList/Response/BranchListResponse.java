package com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchListResponse {

    @SerializedName("status")
    String status;

    @SerializedName("method")
    String method;

    @SerializedName("key")
    String key;

    @SerializedName("db_name")
    String db_name;

    @SerializedName("brnsearch")
    List<BranchCodeName> brnsearch;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public List<BranchCodeName> getBrnsearch() {
        return brnsearch;
    }

    public void setBrnsearch(List<BranchCodeName> brnsearch) {
        this.brnsearch = brnsearch;
    }
}
