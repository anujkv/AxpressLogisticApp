package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Challan {
    @SerializedName("challan_no")
    @Expose
    private String challanNo;
    @SerializedName("challan_date")
    @Expose
    private String challanDate;
    @SerializedName("challan_from")
    @Expose
    private String challanFrom;
    @SerializedName("challan_to")
    @Expose
    private String challanTo;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;
    @SerializedName("status")
    @Expose
    private String status;

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(String challanDate) {
        this.challanDate = challanDate;
    }

    public String getChallanFrom() {
        return challanFrom;
    }

    public void setChallanFrom(String challanFrom) {
        this.challanFrom = challanFrom;
    }

    public String getChallanTo() {
        return challanTo;
    }

    public void setChallanTo(String challanTo) {
        this.challanTo = challanTo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
