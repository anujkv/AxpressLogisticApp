package com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.AttendanceSummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("in_time")
    @Expose
    private String inTime;
    @SerializedName("out_time")
    @Expose
    private String outTime;
    @SerializedName("day_status")
    @Expose
    private String dayStatus;
    @SerializedName("pin_no")
    @Expose
    private String pinNo;
    @SerializedName("leave_type")
    @Expose
    private String leaveType;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("applied_date")
    @Expose
    private String appliedDate;
    @SerializedName("approval_flag")
    @Expose
    private String approvalFlag;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(String dayStatus) {
        this.dayStatus = dayStatus;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
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