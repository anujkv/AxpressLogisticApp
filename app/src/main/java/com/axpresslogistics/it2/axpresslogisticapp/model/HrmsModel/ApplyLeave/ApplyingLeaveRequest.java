package com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyingLeaveRequest {

    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("pin_no")
    @Expose
    private String pinNo;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("applied_date")
    @Expose
    private String appliedDate;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("type")
    @Expose
    private String type;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}