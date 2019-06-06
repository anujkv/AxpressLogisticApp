package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vpa {
    @SerializedName("actual_wt_of_goods")
    @Expose
    private String actualWtOfGoods;
    @SerializedName("bselectiondetail")
    @Expose
    private List<Bselectiondetail> bselectiondetail = null;
    @SerializedName("from_branch")
    @Expose
    private String fromBranch;
    @SerializedName("goods_type")
    @Expose
    private String goodsType;
    @SerializedName("loading_point")
    @Expose
    private String loadingPoint;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("requirement_type")
    @Expose
    private String requirementType;
    @SerializedName("to_branch")
    @Expose
    private String toBranch;
    @SerializedName("unloading_point")
    @Expose
    private String unloadingPoint;
    @SerializedName("vehicle_request_code")
    @Expose
    private String vehicleRequestCode;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;

    public String getActualWtOfGoods() {
        return actualWtOfGoods;
    }

    public void setActualWtOfGoods(String actualWtOfGoods) {
        this.actualWtOfGoods = actualWtOfGoods;
    }

    public List<Bselectiondetail> getBselectiondetail() {
        return bselectiondetail;
    }

    public void setBselectiondetail(List<Bselectiondetail> bselectiondetail) {
        this.bselectiondetail = bselectiondetail;
    }

    public String getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(String fromBranch) {
        this.fromBranch = fromBranch;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getLoadingPoint() {
        return loadingPoint;
    }

    public void setLoadingPoint(String loadingPoint) {
        this.loadingPoint = loadingPoint;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    public String getToBranch() {
        return toBranch;
    }

    public void setToBranch(String toBranch) {
        this.toBranch = toBranch;
    }

    public String getUnloadingPoint() {
        return unloadingPoint;
    }

    public void setUnloadingPoint(String unloadingPoint) {
        this.unloadingPoint = unloadingPoint;
    }

    public String getVehicleRequestCode() {
        return vehicleRequestCode;
    }

    public void setVehicleRequestCode(String vehicleRequestCode) {
        this.vehicleRequestCode = vehicleRequestCode;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

}