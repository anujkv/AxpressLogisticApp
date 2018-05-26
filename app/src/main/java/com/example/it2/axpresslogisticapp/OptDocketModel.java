package com.example.it2.axpresslogisticapp;

/**
 * Created by IT2 on 5/25/2018.
 */

public class OptDocketModel {

    private String opt_name;
    private String opt_icon;
    private String opt_url;

    public String getOpt_name(){ return opt_name;}

    public String getOpt_icon() { return opt_icon;}

    public String getOpt_url() { return opt_url;}

    public OptDocketModel(String opt_name, String opt_icon, String opt_url) {
        this.opt_name = opt_name;
        this.opt_icon = opt_icon;
        this.opt_url = opt_url;
    }
}
