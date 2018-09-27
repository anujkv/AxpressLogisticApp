package com.axpresslogistics.it2.axpresslogisticapp.model;

public class BrokerApprovalModel {
    String  broker_name,broker_code,broker_rate,broker_advance,broker_remark,loggedin_member;

    public BrokerApprovalModel() {
    }

    public BrokerApprovalModel(String broker_name, String broker_code, String broker_rate,
                               String broker_advance, String broker_remark, String loggedin_member) {
        this.broker_name = broker_name;
        this.broker_code = broker_code;
        this.broker_rate = broker_rate;
        this.broker_advance = broker_advance;
        this.broker_remark = broker_remark;
        this.loggedin_member = loggedin_member;
    }

    public String getBroker_name() {
        return broker_name;
    }

    public String getBroker_code() {
        return broker_code;
    }

    public String getBroker_rate() {
        return broker_rate;
    }

    public String getBroker_advance() {
        return broker_advance;
    }

    public String getBroker_remark() {
        return broker_remark;
    }

    public String getLoggedin_member() { return loggedin_member;}
}
