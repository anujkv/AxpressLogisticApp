package com.axpresslogistics.it2.axpresslogisticapp.adaptor.HRMS;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.AttendanceSummary.Attendance;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceSummaryAdaptor extends RecyclerView.Adapter<AttendanceSummaryAdaptor.HolderView> {
    Context context;
    List<Attendance> attendanceList;
    Dialog dialog;
    String outputText;
    DateTime dateTimeInstance;
    public AttendanceSummaryAdaptor(Context context, List<Attendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceSummaryAdaptor.HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_summary_listview, viewGroup,
                false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceSummaryAdaptor.HolderView holder, final int position) {
        final Attendance attendanceModel = attendanceList.get(position);
        dateTimeInstance = new DateTime();
        //date formate change...
        if (!attendanceModel.getDate().isEmpty()) {
            DateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
            DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault());
            Date date = null;
            try {
                date = inputFormat.parse(attendanceModel.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            outputText = outputFormat.format(date);
            holder.date.setText(outputText);
        }

        if(attendanceModel.getInTime().equals("") && attendanceModel.getOutTime().equals("")){
            if(attendanceModel.getDayStatus().equals("S")){
                holder.inTime.setText("Sunday");
                holder.inTime.setTextColor(Color.parseColor("#F44336"));
                holder.inTime.setTextSize(16);
                holder.outTime.setVisibility(View.GONE);
//                holder.leaveAppliedCardView.setBackgroundColor(Color.parseColor("#F44336"));
            }
            else if(attendanceModel.getDayStatus().equals("H")){
                holder.inTime.setText(attendanceModel.getReason());
                holder.inTime.setTextColor(Color.parseColor("#66ffe5"));
                holder.inTime.setTextSize(16);
                holder.outTime.setVisibility(View.GONE);
//                holder.leaveAppliedCardView.setBackgroundColor(Color.parseColor("#66ffe5"));
            }
            else if(attendanceModel.getDayStatus().equals("A")){
                holder.inTime.setText("Absent");
                holder.inTime.setTextColor(Color.parseColor("#F44336"));
                holder.inTime.setTextSize(16);
                holder.outTime.setVisibility(View.GONE);
//                holder.leaveAppliedCardView.setBackgroundColor(Color.parseColor("#F44336"));
            }
            else if(attendanceModel.getDayStatus().equals("L")){
                if( attendanceModel.getApprovalFlag().equals("Approved")){
                    holder.inTime.setText(attendanceModel.getReason());
                    holder.inTime.setTextColor(Color.GREEN);
                    holder.leaveReason.setTextColor(Color.GREEN);

                    holder.inTime.setTextSize(16);
                    holder.leaveReason.setVisibility(View.VISIBLE);
//                    holder.leaveAppliedCardView.setBackgroundColor(Color.GREEN);
                }else {
                    holder.inTime.setText("Absent");
                    holder.inTime.setTextColor(Color.parseColor("#66ffe5"));
                    holder.inTime.setTextSize(16);
                    holder.outTime.setVisibility(View.GONE);
//                    holder.leaveAppliedCardView.setBackgroundColor(Color.parseColor("#66ffe5"));
                }
            }

        }

        else {
            if (attendanceModel.getOutTime().equals("00:00:00")) {
                holder.inTime.setText(attendanceModel.getInTime());
                holder.inTime.setVisibility(View.VISIBLE);
                holder.inTime.setTextSize(14);
                holder.outTime.setTextSize(14);
                holder.outTime.setText("MIS");
                holder.outTime.setVisibility(View.VISIBLE);
                holder.leaveAppliedCardView.setBackgroundColor(Color.WHITE);
            }
            if (attendanceModel.getInTime().equals("00:00:00")) {
                holder.outTime.setText(attendanceModel.getInTime());
                holder.inTime.setTextSize(14);
                holder.inTime.setText("MIS");
                holder.inTime.setVisibility(View.VISIBLE);
                holder.outTime.setVisibility(View.VISIBLE);
                holder.leaveAppliedCardView.setBackgroundColor(Color.WHITE);
            }
            if (!attendanceModel.getOutTime().equals("00:00:00")
                    && !attendanceModel.getInTime().equals("00:00:00")
                    && !attendanceModel.getInTime().equals("")
                    && !attendanceModel.getOutTime().equals("")) {

                holder.inTime.setText(attendanceModel.getInTime());
                holder.outTime.setText(attendanceModel.getOutTime());
                holder.inTime.setTextSize(14);
                holder.outTime.setTextSize(14);
                holder.inTime.setVisibility(View.VISIBLE);
                holder.outTime.setVisibility(View.VISIBLE);
                holder.leaveAppliedCardView.setBackgroundColor(Color.WHITE);
            }
        }

        holder.appliedDate.setText(attendanceModel.getAppliedDate());
        holder.pinNo.setText(attendanceModel.getPinNo());
        holder.leavetype.setText(attendanceModel.getLeaveType());
        holder.dayStatus.setText(attendanceModel.getDayStatus());

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.attendance_summary_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.leaveAppliedCardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                TextView txt_leaveType, txt_clickedDate, txt_leaveReason, txt_fromDate, txt_toDate, txt_leaveStatus,
                        txt_totalDays, txt_appliedDate,leaveType,leaveReason,fromDate,toDate,leaveStatus,
                        totalDays,appliedDate;
                txt_leaveType = dialog.findViewById(R.id.txtleaveType);
                txt_clickedDate = dialog.findViewById(R.id.txtclickedDate);
                txt_leaveReason = dialog.findViewById(R.id.txtleaveReason);
                txt_fromDate = dialog.findViewById(R.id.txtfromDate);
                txt_toDate = dialog.findViewById(R.id.txttoDate);
                txt_leaveStatus = dialog.findViewById(R.id.txtleaveStatus);
                txt_totalDays = dialog.findViewById(R.id.txttotalDays);
                txt_appliedDate = dialog.findViewById(R.id.txtappliedDate);

                leaveType = dialog.findViewById(R.id.leaveType);
                leaveReason = dialog.findViewById(R.id.leaveReason);
                fromDate = dialog.findViewById(R.id.fromDate);
                toDate = dialog.findViewById(R.id.toDate);
                leaveStatus = dialog.findViewById(R.id.leaveStatus);
                totalDays = dialog.findViewById(R.id.totalDays);
                appliedDate = dialog.findViewById(R.id.appliedDate);

                if (attendanceModel.getDayStatus().equals("P")){

                    if(attendanceModel.getDayStatus().equals("P")){

                        leaveType.setVisibility(View.VISIBLE);
                        txt_leaveType.setTextSize(12);
                        txt_leaveType.setTextColor(Color.DKGRAY);
                        fromDate.setVisibility(View.VISIBLE);
                        txt_fromDate.setVisibility(View.VISIBLE);
                        toDate.setVisibility(View.VISIBLE);
                        txt_toDate.setVisibility(View.VISIBLE);

                        txt_clickedDate.setBackgroundColor(Color.YELLOW);
                        txt_clickedDate.setTextColor(Color.BLACK);
                        txt_clickedDate.setText(dateTimeInstance.convertDate_dd_MMM_yyyy(attendanceModel.getDate()));
                        leaveType.setText("Attendance");
                        txt_leaveType.setText("Present");
                        txt_leaveType.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        fromDate.setText("In Time");
                        txt_fromDate.setText(attendanceModel.getInTime());
                        toDate.setText("Out Time");
                        txt_toDate.setText(attendanceModel.getOutTime());
                        leaveReason.setVisibility(View.GONE);
                        txt_leaveReason.setVisibility(View.GONE);
                        leaveStatus.setVisibility(View.GONE);
                        txt_leaveStatus.setVisibility(View.GONE);
                        totalDays.setVisibility(View.GONE);
                        txt_totalDays.setVisibility(View.GONE);
                        appliedDate.setVisibility(View.GONE);
                        txt_appliedDate.setVisibility(View.GONE);
                    }

                }else if(attendanceModel.getDayStatus().equals("L")){

                    leaveType.setVisibility(View.VISIBLE);
                    txt_leaveType.setTextSize(12);
                    txt_leaveType.setTextColor(Color.DKGRAY);
                    fromDate.setVisibility(View.VISIBLE);
                    txt_fromDate.setVisibility(View.VISIBLE);
                    toDate.setVisibility(View.VISIBLE);
                    txt_toDate.setVisibility(View.VISIBLE);
                    txt_leaveStatus.setVisibility(View.VISIBLE);
                    leaveStatus.setVisibility(View.VISIBLE);
                    txt_totalDays.setVisibility(View.VISIBLE);
                    totalDays.setVisibility(View.VISIBLE);
                    txt_appliedDate.setVisibility(View.VISIBLE);
                    appliedDate.setVisibility(View.VISIBLE);

                    txt_clickedDate.setBackgroundColor(Color.GREEN);
                    txt_clickedDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txt_clickedDate.setTextColor(Color.WHITE);
                    txt_clickedDate.setText(dateTimeInstance.convertDate_dd_MMM_yyyy(attendanceModel.getDate()));
                    txt_leaveReason.setText(attendanceModel.getReason());
                    txt_leaveType.setText(attendanceModel.getLeaveType());
                    txt_leaveType.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txt_fromDate.setVisibility(View.VISIBLE);
                    txt_fromDate.setText(attendanceModel.getInTime());
                    txt_toDate.setVisibility(View.VISIBLE);
                    txt_toDate.setText(attendanceModel.getOutTime());
                    txt_leaveStatus.setVisibility(View.VISIBLE);
                    txt_leaveStatus.setText(attendanceModel.getApprovalFlag());
                    txt_totalDays.setText(attendanceModel.getDayStatus());
                    txt_appliedDate.setText(attendanceModel.getAppliedDate());
                }else if (attendanceModel.getDayStatus().equals("A") ||
                        attendanceModel.getDayStatus().equals("S")){
                    if(attendanceModel.getDayStatus().equals("A")){
                        txt_clickedDate.setBackgroundColor(Color.RED);
                        txt_clickedDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        txt_clickedDate.setTextColor(Color.WHITE);
                        txt_clickedDate.setText(dateTimeInstance.convertDate_dd_MMM_yyyy(attendanceModel.getDate()));
                        leaveType.setVisibility(View.GONE);
                        txt_leaveType.setText("Absent");
                        txt_leaveType.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        txt_leaveType.setTextSize(16);
                        txt_leaveType.setTextColor(Color.RED);
                        fromDate.setVisibility(View.GONE);
                        txt_fromDate.setVisibility(View.GONE);
                        toDate.setVisibility(View.GONE);
                        txt_toDate.setVisibility(View.GONE);
                        leaveReason.setVisibility(View.GONE);
                        txt_leaveReason.setVisibility(View.GONE);
                        leaveStatus.setVisibility(View.GONE);
                        txt_leaveStatus.setVisibility(View.GONE);
                        totalDays.setVisibility(View.GONE);
                        txt_totalDays.setVisibility(View.GONE);
                        appliedDate.setVisibility(View.GONE);
                        txt_appliedDate.setVisibility(View.GONE);
                    }else if(attendanceModel.getDayStatus().equals("S") &&
                            attendanceModel.getInTime().equals("")){
//                        txt_clickedDate.setBackgroundColor(Color.RED);
                        txt_clickedDate.setBackgroundColor(Color.RED);
                        txt_clickedDate.setTextColor(Color.WHITE);
                        txt_clickedDate.setText(dateTimeInstance.convertDate_dd_MMM_yyyy(attendanceModel.getDate()));
                        leaveType.setVisibility(View.GONE);
                        txt_leaveType.setText("Sunday");
                        txt_leaveType.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        txt_leaveType.setTextSize(16);
                        txt_leaveType.setTextColor(Color.RED);
                        fromDate.setVisibility(View.GONE);
                        txt_fromDate.setVisibility(View.GONE);
                        toDate.setVisibility(View.GONE);
                        txt_toDate.setVisibility(View.GONE);
                        leaveReason.setVisibility(View.GONE);
                        txt_leaveReason.setVisibility(View.GONE);
                        leaveStatus.setVisibility(View.GONE);
                        txt_leaveStatus.setVisibility(View.GONE);
                        totalDays.setVisibility(View.GONE);
                        txt_totalDays.setVisibility(View.GONE);
                        appliedDate.setVisibility(View.GONE);
                        txt_appliedDate.setVisibility(View.GONE);
                    }else if (attendanceModel.getDayStatus().equals("S") &&
                            (!attendanceModel.getInTime().equals(""))){
                        leaveType.setVisibility(View.VISIBLE);
                        txt_leaveType.setTextSize(14);
                        txt_leaveType.setTextColor(Color.BLACK);
                        fromDate.setVisibility(View.VISIBLE);
                        txt_fromDate.setVisibility(View.VISIBLE);
                        toDate.setVisibility(View.VISIBLE);
                        txt_toDate.setVisibility(View.VISIBLE);

                        txt_clickedDate.setBackgroundColor(Color.YELLOW);
                        txt_clickedDate.setTextColor(Color.BLACK);
                        txt_clickedDate.setText(dateTimeInstance.convertDate_dd_MMM_yyyy(attendanceModel.getDate()));
                        leaveType.setText("Attendance");
                        txt_leaveType.setText("Present");
                        fromDate.setText("In Time");
                        txt_fromDate.setText(attendanceModel.getInTime());
                        toDate.setText("Out Time");
                        txt_toDate.setText(attendanceModel.getOutTime());
                        leaveReason.setVisibility(View.GONE);
                        txt_leaveReason.setVisibility(View.GONE);
                        leaveStatus.setText("Day");
                        txt_leaveStatus.setText("Sunday");
                        totalDays.setVisibility(View.GONE);
                        txt_totalDays.setVisibility(View.GONE);
                        appliedDate.setVisibility(View.GONE);
                        txt_appliedDate.setVisibility(View.GONE);
                    }
                }
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        TextView date,inTime,outTime,leaveReason,approvalFlag,appliedDate,pinNo,dayStatus,leavetype;
        CardView leaveAppliedCardView;
        public HolderView(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txt_date);
            inTime =itemView.findViewById(R.id.txtInTime);
            outTime = itemView.findViewById(R.id.txtOutTime);
            leaveReason = itemView.findViewById(R.id.txtLeaveReason);
            approvalFlag =itemView.findViewById(R.id.txtApprovedStatus);
            appliedDate = itemView.findViewById(R.id.txtLeaveAppliedDate);
            pinNo = itemView.findViewById(R.id.txtpin_no);
            dayStatus =itemView.findViewById(R.id.txtDayStatus);
            leavetype = itemView.findViewById(R.id.days);
            leaveAppliedCardView = itemView.findViewById(R.id.attSummaryCardView);

        }
    }
}
