package com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.AttendanceSummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceSummaryResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("attendance")
    @Expose
    private List<Attendance> attendance = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("approval_flag")
    @Expose
    private Object approvalFlag;
    @SerializedName("year")
    @Expose
    private String year;

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

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(Object approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
