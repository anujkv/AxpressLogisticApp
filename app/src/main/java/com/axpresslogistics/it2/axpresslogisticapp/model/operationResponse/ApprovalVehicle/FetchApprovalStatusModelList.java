package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchApprovalStatusModelList {
    @SerializedName("vehicle_request_code")
    @Expose
    private String vehicleRequestCode;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("from_branch")
    @Expose
    private String fromBranch;
    @SerializedName("to_branch")
    @Expose
    private String toBranch;
    @SerializedName("loading_point")
    @Expose
    private String loadingPoint;
    @SerializedName("unloading_point")
    @Expose
    private String unloadingPoint;
    @SerializedName("requirement_type")
    @Expose
    private String requirementType;
    @SerializedName("goods_type")
    @Expose
    private String goodsType;
    @SerializedName("actual_wt_of_goods")
    @Expose
    private String actualWtOfGoods;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("bselectiondetail")
    @Expose
    private List<FetchApprovalStatusModelListOfList> bselectiondetail = null;

    public String getVehicleRequestCode() {
        return vehicleRequestCode;
    }

    public void setVehicleRequestCode(String vehicleRequestCode) {
        this.vehicleRequestCode = vehicleRequestCode;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(String fromBranch) {
        this.fromBranch = fromBranch;
    }

    public String getToBranch() {
        return toBranch;
    }

    public void setToBranch(String toBranch) {
        this.toBranch = toBranch;
    }

    public String getLoadingPoint() {
        return loadingPoint;
    }

    public void setLoadingPoint(String loadingPoint) {
        this.loadingPoint = loadingPoint;
    }

    public String getUnloadingPoint() {
        return unloadingPoint;
    }

    public void setUnloadingPoint(String unloadingPoint) {
        this.unloadingPoint = unloadingPoint;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getActualWtOfGoods() {
        return actualWtOfGoods;
    }

    public void setActualWtOfGoods(String actualWtOfGoods) {
        this.actualWtOfGoods = actualWtOfGoods;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<FetchApprovalStatusModelListOfList> getBselectiondetail() {
        return bselectiondetail;
    }

    public void setBselectiondetail(List<FetchApprovalStatusModelListOfList> bselectiondetail) {
        this.bselectiondetail = bselectiondetail;
    }
}
