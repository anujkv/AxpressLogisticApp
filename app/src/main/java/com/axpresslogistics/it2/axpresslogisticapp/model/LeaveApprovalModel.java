package com.axpresslogistics.it2.axpresslogisticapp.model;

public class LeaveApprovalModel {
    String id,emplid,applier_name,leave_type,from_date,to_date,days,reason,approval_status,
            approval_comment;

    public LeaveApprovalModel() {
    }

    public LeaveApprovalModel(String id, String emplid, String applier_name,
                              String leave_type, String from_date, String to_date,
                              String days, String reason, String approval_status,
                              String approval_comment) {
        this.id = id;
        this.emplid = emplid;
        this.applier_name = applier_name;
        this.leave_type = leave_type;
        this.from_date = from_date;
        this.to_date = to_date;
        this.days = days;
        this.reason = reason;
        this.approval_status = approval_status;
        this.approval_comment = approval_comment;
    }

    public String getId() {
        return id;
    }

    public String getEmplid() {
        return emplid;
    }

    public String getApplier_name() {
        return applier_name;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getDays() {
        return days;
    }

    public String getReason() {
        return reason;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public String getApproval_comment() {
        return approval_comment;
    }
}
