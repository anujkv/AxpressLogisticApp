
package com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToDoListAddModelList {
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelected;

    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dept_code")
    @Expose
    private String deptCode;
    @SerializedName("branch_code")
    @Expose
    private String branchCode;

    public ToDoListAddModelList(String emplid, String name, String contact, String email, String deptCode, String branchCode) {
    this.emplid = emplid;
    this.name= name;
    this.contact = contact;
    this.email = email;
    this.deptCode = deptCode;
    this.branchCode = branchCode;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

}
