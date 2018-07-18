package com.example.it2.axpresslogisticapp.model;

public class AppliedLeaveModel {
    String from,reason,days,type;
    String to,pin_no;

    public AppliedLeaveModel() {
    }

    public AppliedLeaveModel(String from, String reason, String days, String type, String to, String pin_no) {
        this.from = from;
        this.reason = reason;
        this.days = days;
        this.type = type;
        this.to = to;
        this.pin_no = pin_no;
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
}
