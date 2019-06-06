package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchApprovalStatusModelListOfList {
    public String getLoggedin_member() {
        return loggedin_member;
    }

    public void setLoggedin_member(String loggedin_member) {
        this.loggedin_member = loggedin_member;
    }

    boolean isSelected=false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @SerializedName("loggedin_member")
    public String loggedin_member;

    @SerializedName("broker_name")
    @Expose
    private String brokerName;
    @SerializedName("broker_code")
    @Expose
    private String brokerCode;
    @SerializedName("broker_rate")
    @Expose
    private String brokerRate;
    @SerializedName("broker_advance")
    @Expose
    private String brokerAdvance;
    @SerializedName("broker_remark")
    @Expose
    private String brokerRemark;

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getBrokerRate() {
        return brokerRate;
    }

    public void setBrokerRate(String brokerRate) {
        this.brokerRate = brokerRate;
    }

    public String getBrokerAdvance() {
        return brokerAdvance;
    }

    public void setBrokerAdvance(String brokerAdvance) {
        this.brokerAdvance = brokerAdvance;
    }

    public String getBrokerRemark() {
        return brokerRemark;
    }

    public void setBrokerRemark(String brokerRemark) {
        this.brokerRemark = brokerRemark;
    }
}
