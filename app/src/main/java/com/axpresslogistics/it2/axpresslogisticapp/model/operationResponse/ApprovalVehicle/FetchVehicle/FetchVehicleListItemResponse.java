package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchVehicleListItemResponse {
    @SerializedName("broker_status")
    @Expose
    private String brokerStatus;
    @SerializedName("db_name")
    @Expose
    private String dbName;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vpa")
    @Expose
    private List<Vpa> vpa = null;

    public String getBrokerStatus() {
        return brokerStatus;
    }

    public void setBrokerStatus(String brokerStatus) {
        this.brokerStatus = brokerStatus;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Vpa> getVpa() {
        return vpa;
    }

    public void setVpa(List<Vpa> vpa) {
        this.vpa = vpa;
    }

}