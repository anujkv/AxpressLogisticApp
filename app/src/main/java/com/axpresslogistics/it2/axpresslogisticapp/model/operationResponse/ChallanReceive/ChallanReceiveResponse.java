package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ChallanReceive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallanReceiveResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("challan_no")
    @Expose
    private String challanNo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("closing_date")
    @Expose
    private String closingDate;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("from_branch")
    @Expose
    private String fromBranch;
    @SerializedName("to_branch")
    @Expose
    private String toBranch;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;
    @SerializedName("tcs_no")
    @Expose
    private String tcsNo;
    @SerializedName("awb_no")
    @Expose
    private String awbNo;
    @SerializedName("seal_no")
    @Expose
    private String sealNo;
    @SerializedName("remark")
    @Expose
    private String remark;
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

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getTcsNo() {
        return tcsNo;
    }

    public void setTcsNo(String tcsNo) {
        this.tcsNo = tcsNo;
    }

    public String getAwbNo() {
        return awbNo;
    }

    public void setAwbNo(String awbNo) {
        this.awbNo = awbNo;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
