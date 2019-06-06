package com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApprovalLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovaLeave {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("leave_type")
    @Expose
    private String leaveType;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("approval_status")
    @Expose
    private String approvalStatus;
    @SerializedName("approval_comment")
    @Expose
    private String approvalComment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalComment() {
        return approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

}