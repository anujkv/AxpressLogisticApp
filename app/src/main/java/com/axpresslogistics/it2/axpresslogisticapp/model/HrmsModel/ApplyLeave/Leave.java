package com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leave {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pin_no")
    @Expose
    private String pinNo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("applied_date")
    @Expose
    private String appliedDate;
    @SerializedName("approval_flag")
    @Expose
    private String approvalFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

}
