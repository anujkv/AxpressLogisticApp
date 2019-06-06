package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleApprovalListResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("savedvehiclesearch")
    @Expose
    private List<SavedvehiclList> savedvehiclLists = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("db_name")
    @Expose
    private String db_name;

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
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


    public List<SavedvehiclList> getSavedvehiclLists() {
        return savedvehiclLists;
    }

    public void setSavedvehiclLists(List<SavedvehiclList> savedvehiclLists) {
        this.savedvehiclLists = savedvehiclLists;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}