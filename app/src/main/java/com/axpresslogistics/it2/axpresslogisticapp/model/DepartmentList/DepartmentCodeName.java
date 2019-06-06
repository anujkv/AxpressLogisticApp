package com.axpresslogistics.it2.axpresslogisticapp.model.DepartmentList;

import com.google.gson.annotations.SerializedName;

public class DepartmentCodeName {

    @SerializedName("Dept_code")
    String code;

    @SerializedName("Dept_Name")
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
