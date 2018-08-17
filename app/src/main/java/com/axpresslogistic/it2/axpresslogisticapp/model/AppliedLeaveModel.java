package com.axpresslogistic.it2.axpresslogisticapp.model;

public class AppliedLeaveModel {
    String from,reason,days,type,to,pin_no,applied_date,leave_status;

    public AppliedLeaveModel() {
    }

    public AppliedLeaveModel(String from, String reason, String days, String type, String to,
                             String pin_no, String applied_date, String leave_status) {
        this.from = from;
        this.reason = reason;
        this.days = days;
        this.type = type;
        this.to = to;
        this.pin_no = pin_no;
        this.applied_date = applied_date;
        this.leave_status = leave_status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPin_no() {
        return pin_no;
    }

    public void setPin_no(String pin_no) {
        this.pin_no = pin_no;
    }

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
    }

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }
}
