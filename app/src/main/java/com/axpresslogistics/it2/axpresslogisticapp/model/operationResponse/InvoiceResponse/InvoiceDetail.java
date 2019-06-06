package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceDetail {

    @SerializedName("docket_no")
    @Expose
    private String docketNo;
    @SerializedName("consignee_name")
    @Expose
    private String consigneeName;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("from_destination")
    @Expose
    private String fromDestination;
    @SerializedName("to_destination")
    @Expose
    private String toDestination;
    @SerializedName("status")
    @Expose
    private String status;

    public String getDocketNo() {
        return docketNo;
    }

    public void setDocketNo(String docketNo) {
        this.docketNo = docketNo;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}