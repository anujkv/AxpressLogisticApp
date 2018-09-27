package com.axpresslogistics.it2.axpresslogisticapp.model;

public class SearchVehicleReqModel {
    String  request_code,request_date, from_branch,to_branch,broker_name,minimum_rate;

    public SearchVehicleReqModel() {
    }

    public SearchVehicleReqModel(String request_code, String request_date, String from_branch,
                                 String to_branch, String broker_name, String minimum_rate) {
        this.request_code = request_code;
        this.request_date = request_date;
        this.from_branch = from_branch;
        this.to_branch = to_branch;
        this.broker_name = broker_name;
        this.minimum_rate = minimum_rate;
    }

    public String getRequest_code() {
        return request_code;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getFrom_branch() {
        return from_branch;
    }

    public String getTo_branch() {
        return to_branch;
    }

    public String getBroker_name() {
        return broker_name;
    }

    public String getMinimum_rate() {
        return minimum_rate;
    }
}
