package com.axpresslogistics.it2.axpresslogisticapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("record_datetime")
    @Expose
    private Object recordDatetime;
    @SerializedName("received_at_dest")
    @Expose
    private Object receivedAtDest;
    @SerializedName("origin")
    @Expose
    private Object origin;
    @SerializedName("destination")
    @Expose
    private Object destination;
    @SerializedName("sim_no")
    @Expose
    private Object simNo;
    @SerializedName("tcs_close_date")
    @Expose
    private Object tcsCloseDate;
    @SerializedName("route_no")
    @Expose
    private Object routeNo;
    @SerializedName("vehicle_no")
    @Expose
    private Object vehicleNo;
    @SerializedName("tcs_no")
    @Expose
    private Object tcsNo;
    @SerializedName("db_name")
    @Expose
    private Object dbName;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getRecordDatetime() {
        return recordDatetime;
    }

    public void setRecordDatetime(Object recordDatetime) {
        this.recordDatetime = recordDatetime;
    }

    public Object getReceivedAtDest() {
        return receivedAtDest;
    }

    public void setReceivedAtDest(Object receivedAtDest) {
        this.receivedAtDest = receivedAtDest;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    public Object getSimNo() {
        return simNo;
    }

    public void setSimNo(Object simNo) {
        this.simNo = simNo;
    }

    public Object getTcsCloseDate() {
        return tcsCloseDate;
    }

    public void setTcsCloseDate(Object tcsCloseDate) {
        this.tcsCloseDate = tcsCloseDate;
    }

    public Object getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(Object routeNo) {
        this.routeNo = routeNo;
    }

    public Object getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(Object vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Object getTcsNo() {
        return tcsNo;
    }

    public void setTcsNo(Object tcsNo) {
        this.tcsNo = tcsNo;
    }

    public Object getDbName() {
        return dbName;
    }

    public void setDbName(Object dbName) {
        this.dbName = dbName;
    }

}