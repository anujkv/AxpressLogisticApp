package com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToDoListSendRequest {

    @SerializedName("contact")
    @Expose
    private List<ToDoListAddModelList> contact = null;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("reminder_switch")
    @Expose
    private String reminderSwitch;



    public List<ToDoListAddModelList> getContact() {
        return contact;
    }

    public void setContact(List<ToDoListAddModelList> contact) {
        this.contact = contact;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReminderSwitch() {
        return reminderSwitch;
    }

    public void setReminderSwitch(String reminderSwitch) {
        this.reminderSwitch = reminderSwitch;
    }

}



