package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchApprovalStatusModel {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("vpa")
    @Expose
    private List<FetchApprovalStatusModelList> vpa = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("broker_status")
    @Expose
    private String brokerStatus;

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

    public List<FetchApprovalStatusModelList> getVpa() {
        return vpa;
    }

    public void setVpa(List<FetchApprovalStatusModelList> vpa) {
        this.vpa = vpa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrokerStatus() {
        return brokerStatus;
    }

    public void setBrokerStatus(String brokerStatus) {
        this.brokerStatus = brokerStatus;
    }

}
