package com.axpresslogistics.it2.axpresslogisticapp.model;


import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListModelList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToDoListModel {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("reminder_list")
    @Expose
    private List<ToDoListModelList> reminderList = null;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<ToDoListModelList> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<ToDoListModelList> reminderList) {
        this.reminderList = reminderList;
    }

}
