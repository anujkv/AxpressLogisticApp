package com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.AppRoleResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Module {
    @SerializedName("form_name")
    @Expose
    private String formName;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

}