
package com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToDoListModelList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("reminder_switch")
    @Expose
    private String reminderSwitch;
    @SerializedName("created_by")
    @Expose
    private String createdBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReminderSwitch() {
        return reminderSwitch;
    }

    public void setReminderSwitch(String reminderSwitch) {
        this.reminderSwitch = reminderSwitch;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
