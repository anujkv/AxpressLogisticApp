package com.axpresslogistics.it2.axpresslogisticapp.model.DepartmentList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeptListResponse {

    @SerializedName("department")
    List<DepartmentCodeName> department;

    public List<DepartmentCodeName> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentCodeName> department) {
        this.department = department;
    }
}
