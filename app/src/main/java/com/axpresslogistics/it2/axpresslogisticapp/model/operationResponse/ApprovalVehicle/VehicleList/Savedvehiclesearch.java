package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Savedvehiclesearch {

    @SerializedName("request_code")
    @Expose
    private String requestCode;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("from_branch")
    @Expose
    private String fromBranch;
    @SerializedName("to_branch")
    @Expose
    private String toBranch;
    @SerializedName("broker_name")
    @Expose
    private String brokerName;
    @SerializedName("minimum_rate")
    @Expose
    private String minimumRate;

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(String fromBranch) {
        this.fromBranch = fromBranch;
    }

    public String getToBranch() {
        return toBranch;
    }

    public void setToBranch(String toBranch) {
        this.toBranch = toBranch;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getMinimumRate() {
        return minimumRate;
    }

    public void setMinimumRate(String minimumRate) {
        this.minimumRate = minimumRate;
    }

}
