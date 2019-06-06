package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brokersearch {

    @SerializedName("broker_code")
    @Expose
    private String brokerCode;
    @SerializedName("broker_name")
    @Expose
    private String brokerName;
    @SerializedName("branch")
    @Expose
    private String branch;

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}