package com.axpresslogistics.it2.axpresslogisticapp.network;


import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.AppRoleResponse.AppRoleResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.HRApprovalRes.HRApprovalResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.LoginResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.ProfileImageUploadResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.ProfileResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.ResetPasswordResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response.BranchListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.MapResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.ToDoListModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.AddVehicleReq.AddVehicleResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchApprovalStatusModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList.VehicleApprovalListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.ApprovedVehicle.VehicleApprovedListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.AddBroker.AddBrokerResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.BrokerListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.BrokerSearchResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.FetchBrokerResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.UpdatBrokerResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.AddBusinessCardResponse.AddBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.BusinessCardListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.SearchBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.FetchBusinessCardResponse.FetchBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.UpdateBusinessCardResponse.UpdateBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.NewVisitFormResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.UpdateVisitFormResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.VisitFormFetchResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.VisitFormHistoryResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.SearchVisitFormResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.VisitFormListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.DepartmentList.DeptListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.AppliedLeavedResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.ApplyingLeaveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApprovalLeave.ApprovalLeaveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.AttendanceSummary.AttendanceSummaryResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.EditLeave.EditLeaveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.MarkAttendance.MarkAttendanceRequest;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.MarkAttendance.MarkAttendanceResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ChallanReceive.ChallanCloseResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ChallanReceive.ChallanReceiveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList.SearchVehicleResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval.VendorApprovalRequest;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval.VendorApprovalResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch.VendorFetchResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorList.VendorViewListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.DocketResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse.InvoiceResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.PODScanResponse.PodScanResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.PodDocketResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListAddModelResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListDeleteModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListSendModelResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListSendRequest;
import com.axpresslogistics.it2.axpresslogisticapp.model.uploadDocumentResponse.UploadDocumentsResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface PosApiInterface {

    @POST("Operations/branch")
    Observable<BranchListResponse> branchList(@HeaderMap Map<String, String> headers, @Body Map<String,String>  body);

    @GET("hrms/get_Department")
    Observable<DeptListResponse> deptList(@HeaderMap Map<String, String> header);

    @Multipart
    @POST("Tickets/new_ticket")
    Call<ResponseBody> upload(@HeaderMap Map<String, String> headers, @Body Map<String, String> body, @Part MultipartBody.Part image_upload);

    @Multipart
    @POST("HRMS/upload_document")
    Call<UploadDocumentsResponse> uploadDocumentReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body, @Part MultipartBody.Part multipartBody);

    //---------------------HRMS------------------

    @POST("HRMS/leave_entry")
    Observable<ApplyingLeaveResponse> applyingleave(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);
    //
    @POST("HRMS/leave_entry")
    Observable<EditLeaveResponse> editLeave(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/approve_leave_show")
    Observable<ApprovalLeaveResponse> leaveRequest(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);
    //
    @POST("HRMS/approve_leave_update ")
    Observable<ApprovalLeaveResponse> approvalleave(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);
    //
    @POST("HRMS/attendance_summary")
    Observable<AttendanceSummaryResponse> attandance(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);
    //
    @POST("HRMS/leave_search")
    Observable<AppliedLeavedResponse> appliedLeave(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);
    //
    @POST("HRMS/Attendance")
    Observable<MarkAttendanceResponse> markattandance(@HeaderMap Map<String, String> headers, @Body MarkAttendanceRequest request);

    //---------------------OPERATIONS------------------
    @POST("Operations/Docket_Invoice")
    Observable<DocketResponse> docketRequest(@HeaderMap Map<String, String> header, @Body Map<String, String> body);
    @POST("Operations/invoice_details")
    Observable<InvoiceResponse> invoiceRequest(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("image/PODShow")
    Observable<PodDocketResponse> podDocketRequest(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("image/podscan")
    Observable<PodScanResponse> podScanRequest(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/Get_Challan")
    Observable<ChallanReceiveResponse> challanReceive(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/Close_Challan")
    Observable<ChallanCloseResponse> challanClose(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/Vendor_Vehicle_Attach_Request_approveORreject")
    Observable<VendorApprovalResponse> vendorApprovalResponse(@HeaderMap Map<String, String> headers, @Body VendorApprovalRequest vendorApprovalRequest);

    @POST("Operations/vendor_vehicle_attach_request_detail")
    Observable<VendorFetchResponse> vendorFetchResponse(@HeaderMap Map<String, String> headers, @Body Map<String,String>  body);

    @POST("Operations/vendor_vehicle_attach_request_list")
    Observable<VendorViewListResponse> vendorLisResponse(@HeaderMap Map<String, String> headers, @Body Map<String,String>  body);

    @POST("Operations/broker_add")
    Observable<AddBrokerResponse> addBroker(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/broker")
    Observable<BrokerListResponse> brokerList(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/searchbroker")
    Observable<BrokerSearchResponse> brokerSearchList(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/broker_fetch")
    Observable<FetchBrokerResponse> fetchBrokerDetails(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/brokerupdate")
    Observable<UpdatBrokerResponse> updateBrokerDetails(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("Operations/market_vehicle_entry")
    Observable<AddVehicleResponse> addVehicleReq(@HeaderMap Map<String, String> header, @Body Map<String, String> body);

    @POST("HRMS/employee_info")
    Observable<ProfileResponse> profile(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/search_vehicle")
    Observable<SearchVehicleResponse> vehicleSearchReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/market_vehicle_saved_list")
    Observable<VehicleApprovalListResponse> vehicleApprovalList(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/saved_vehicle_list_for_approval")
    Observable<FetchApprovalStatusModel> fetchApprovalStatus(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/approval_method")
    Observable<VehicleApprovedListResponse> approvedVehicalList(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);


    @POST("HRMS/image")
    Observable<ProfileImageUploadResponse> profileImageUploadReq(@HeaderMap Map<String, String> headers,
                                                                 @Body Map<String, String> body,
                                                                 @Part MultipartBody.Part image,
                                                                 @Part RequestBody type);

    //--------------------CRM-----------------
    @POST("Operations/saved_list")
    Observable<VisitFormListResponse> visitFormListReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/search_list")
    Observable<SearchVisitFormResponse> visitformListSearch(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/customer_visit")
    Observable<NewVisitFormResponse> newVisitFormReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/customer_search")
    Observable<VisitFormFetchResponse> visitformFetchReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/customer_visit")
    Observable<UpdateVisitFormResponse> updatevisitformReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/show_history")
    Observable<VisitFormHistoryResponse> fetchvisitHistoryReq(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/business_card_list")
    Observable<BusinessCardListResponse> buisnessCardList(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/business_card_search")
    Observable<SearchBusinessCardResponse> buisnessCardListSearch(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/business_card")
    Observable<AddBusinessCardResponse> addBuisnessCard(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/business_card_update")
    Observable<UpdateBusinessCardResponse> updateBuisnessCard(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/business_card_show")
    Observable<FetchBusinessCardResponse> fetchBuisnessCard(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    //--------------------CRM-----------------
    @POST("HRMS/Get_Login")
    Observable<LoginResponse> loginRequest(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("HRMS/Reset_Pass")
    Observable<ResetPasswordResponse> resetPasswordReq(@HeaderMap Map<String, String> headers, @Body  Map<String, String> body);

    @POST("HRMS/app_role")
    Observable<AppRoleResponse> appRoleRequest(@HeaderMap Map<String, String> headers, @Body  Map<String, String> body);

    @POST("HRMS/employee_info")
    Observable<ProfileResponse> profileRequest(@HeaderMap Map<String, String> headers, @Body  Map<String, String> body);

//    @POST("HRMS/image")
//    Observable<ProfileResponse> profileRequest(@HeaderMap Map<String, String> headers, @Body  Map<String, String> body);


    @POST("Operations/map")
    Observable<MapResponse> mapRequest(@HeaderMap Map<String, String> headers, @Body  Map<String, String> body);

    @POST("HRMS/hr_approval")
    Observable<HRApprovalResponse> hrApprovalRequest(@HeaderMap Map<String, String> headers, @Body  Map<String, String> body);

    @GET("Operations/Get_Company")
    Observable<ResponseBody> getList(@Query("EMPLID") String param);

    @POST("Operations/saved_reminder")
    Observable<ToDoListModel> toDoList(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/rem_contact")
    Observable<ToDoListAddModelResponse> ToDoListAdd(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/remove_reminder")
    Observable<ToDoListDeleteModel> ToDoListDelete(@HeaderMap Map<String, String> headers, @Body Map<String, String> body);

    @POST("Operations/reminder")
    Observable<ToDoListSendModelResponse> toDoListSend(@HeaderMap Map<String, String> header, @Body ToDoListSendRequest toDoListSendRequest);

//
}


