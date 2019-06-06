package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bselectiondetail {
    @SerializedName("broker_advance")
    @Expose
    private String brokerAdvance;
    @SerializedName("broker_code")
    @Expose
    private String brokerCode;
    @SerializedName("broker_name")
    @Expose
    private String brokerName;
    @SerializedName("broker_rate")
    @Expose
    private String brokerRate;
    @SerializedName("broker_remark")
    @Expose
    private String brokerRemark;

    public String getBrokerAdvance() {
        return brokerAdvance;
    }

    public void setBrokerAdvance(String brokerAdvance) {
        this.brokerAdvance = brokerAdvance;
    }

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

    public String getBrokerRate() {
        return brokerRate;
    }

    public void setBrokerRate(String brokerRate) {
        this.brokerRate = brokerRate;
    }

    public String getBrokerRemark() {
        return brokerRemark;
    }

    public void setBrokerRemark(String brokerRemark) {
        this.brokerRemark = brokerRemark;
    }

}