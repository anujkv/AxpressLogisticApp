package com.example.it2.axpresslogisticapp.model;

public class AttendanceModel {
    String date,inTime,outTime,leaveReason,approvalFlag,appliedDate,pinNo,dayStatus,leavetype;

    public AttendanceModel(String date, String inTime, String outTime, String leaveReason,
                           String approvalFlag, String appliedDate, String pinNo, String dayStatus,
                           String leavetype) {
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
        this.leaveReason = leaveReason;
        this.approvalFlag = approvalFlag;
        this.appliedDate = appliedDate;
        this.pinNo = pinNo;
        this.dayStatus = dayStatus;
        this.leavetype = leavetype;
    }

    public AttendanceModel() {
    }

    public String getDate() {
        return date.replace("AM","").trim();
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

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(String dayStatus) {
        this.dayStatus = dayStatus;
    }

    public String getLeavetype() {
        return leavetype;
    }

    public void setLeavetype(String leavetype) {
        this.leavetype = leavetype;
    }
}
