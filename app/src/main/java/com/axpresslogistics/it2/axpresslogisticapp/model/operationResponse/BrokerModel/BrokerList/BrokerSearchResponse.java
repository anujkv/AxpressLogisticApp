package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrokerSearchResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("brokersearch")
    @Expose
    private List<Brokersearch> brokersearch = null;
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

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public List<Brokersearch> getBrokersearch() {
        return brokersearch;
    }

    public void setBrokersearch(List<Brokersearch> brokersearch) {
        this.brokersearch = brokersearch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}