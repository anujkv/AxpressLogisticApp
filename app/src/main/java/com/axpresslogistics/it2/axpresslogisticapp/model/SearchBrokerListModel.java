package com.axpresslogistics.it2.axpresslogisticapp.model;

public class SearchBrokerListModel {
    String broker_name, broker_code, broker_branch;

    public SearchBrokerListModel(String broker_name, String broker_code, String broker_branch) {
        this.broker_name = broker_name;
        this.broker_code = broker_code;
        this.broker_branch = broker_branch;
    }

    public SearchBrokerListModel() {
    }

    public String getBroker_name() {
        return broker_name;
    }

    public String getBroker_code() {
        return broker_code;
    }

    public String getBroker_branch() {
        return broker_branch;
    }
}
