package com.example.it2.axpresslogisticapp.model;

/**
 * Created by IT2 on 5/25/2018.
 */

public class OperationModel {

    private String opt_name;
    private String opt_icon;

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public String getOpt_icon() {
        return opt_icon;
    }

    public void setOpt_icon(String opt_icon) {
        this.opt_icon = opt_icon;
    }

    public OperationModel(){
    }

    public OperationModel(String opt_name, String opt_icon) {
        this.opt_name = opt_name;
        this.opt_icon = opt_icon;
    }
}
