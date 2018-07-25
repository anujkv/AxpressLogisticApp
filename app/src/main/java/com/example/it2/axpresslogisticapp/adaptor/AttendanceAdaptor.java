package com.example.it2.axpresslogisticapp.adaptor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.acitvities.AttendanceSummaryActivity;
import com.example.it2.axpresslogisticapp.model.AttendanceModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.it2.axpresslogisticapp.R.color.colorPrimary;

public class AttendanceAdaptor extends RecyclerView.Adapter<AttendanceAdaptor.AttendanceHolder> {
    Context context;
    List<AttendanceModel> attendanceModelList;
    AttendanceSummaryActivity summaryActivity;
    Dialog dialog;
    String outputText;

    public AttendanceAdaptor(Context context, List<AttendanceModel> attendanceModelList) {
        this.context = context;
        this.attendanceModelList = attendanceModelList;
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_summary_listview, parent,
                false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceHolder holder, int position) {
        final AttendanceModel attendanceModel = attendanceModelList.get(position);
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
//        holder.date.setText(attendanceModel.getDate());
        //If Input date not found then show leave reason...
        if(attendanceModel.getInTime().equals("") && attendanceModel.getOutTime().equals("") &&
                attendanceModel.getDayStatus().equals("S")) {
            holder.inTime.setText("Sunday");
            holder.inTime.setTextSize(16);
            holder.outTime.setVisibility(View.GONE);
            holder.leaveAppliedCardView.setBackgroundColor(Color.RED);
        } else if (attendanceModel.getInTime().equals("") && attendanceModel.getOutTime().equals("") &&
                attendanceModel.getDayStatus().equals("A")){
            holder.inTime.setText("Absent");
            holder.inTime.setTextSize(16);
            holder.outTime.setVisibility(View.GONE);
            holder.leaveAppliedCardView.setBackgroundColor(Color.RED);
        }else{
            if(!attendanceModel.getInTime().equals("") && attendanceModel.getOutTime().equals("00:00:00")){
                holder.inTime.setText(attendanceModel.getInTime());
                holder.inTime.setTextSize(14);
                holder.outTime.setText("MIS");
                holder.outTime.setVisibility(View.VISIBLE);
                holder.leaveAppliedCardView.setBackgroundColor(Color.WHITE);
            }else{
                holder.inTime.setText(attendanceModel.getInTime());
                holder.outTime.setText(attendanceModel.getOutTime());
                holder.outTime.setVisibility(View.VISIBLE);
                holder.leaveAppliedCardView.setBackgroundColor(Color.WHITE);
            }
        }

        //set OutTime...
        if(attendanceModel.getOutTime().equals("00:00:00")){
            holder.outTime.setText("MIS");
        }else{
            holder.outTime.setText(attendanceModel.getOutTime());
        }
        // set leaveReason...
        if (attendanceModel.getInTime().equals("") && attendanceModel.getOutTime().equals("") &&
                attendanceModel.getDayStatus().equals("L")){
            holder.inTime.setVisibility(View.GONE);
            holder.inTime.setTextSize(16);
            holder.leaveReason.setVisibility(View.VISIBLE);
            holder.leaveAppliedCardView.setBackgroundColor(Color.GREEN);
        }
        //set LeaveStatus...
        if(attendanceModel.getInTime().equals("") && attendanceModel.getOutTime().equals("") &&
                attendanceModel.getApprovalFlag().equals("apporved")){
            holder.outTime.setVisibility(View.GONE);
            holder.approvalFlag.setVisibility(View.VISIBLE);
        }
        //set applied Date...
        holder.appliedDate.setText(attendanceModel.getAppliedDate());
        holder.pinNo.setText(attendanceModel.getPinNo());

        //Set Day status if emp. absent then show type of leave, if type not found then show absent..
        if(!attendanceModel.getDayStatus().equals("")){
            if(attendanceModel.getDayStatus().equals("L")){
                holder.dayStatus.setVisibility(View.GONE);
                holder.leavetype.setVisibility(View.VISIBLE);
            }else {
                holder.dayStatus.setText(attendanceModel.getDayStatus());
            }
        }
        holder.leavetype.setText(attendanceModel.getLeavetype());

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.attendance_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.leaveAppliedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt_leaveType, txt_clickedDate, txt_leaveReason, txt_fromDate, txt_toDate, txt_leaveStatus,
                        txt_totalDays, txt_appliedDate;
                txt_leaveType = dialog.findViewById(R.id.txt_leaveType);
                txt_clickedDate = dialog.findViewById(R.id.txt_clickedDate);
                txt_leaveReason = dialog.findViewById(R.id.txt_leaveReason);
                txt_fromDate = dialog.findViewById(R.id.txt_fromDate);
                txt_toDate = dialog.findViewById(R.id.txt_toDate);
                txt_leaveStatus = dialog.findViewById(R.id.txt_leaveStatus);
                txt_totalDays = dialog.findViewById(R.id.txt_totalDays);
                txt_appliedDate = dialog.findViewById(R.id.txt_appliedDate);
                txt_clickedDate.setVisibility(View.VISIBLE);
                txt_clickedDate.setText(attendanceModel.getDate());

                txt_clickedDate.setVisibility(View.VISIBLE);
                txt_clickedDate.setText(outputText);
                if(attendanceModel.getDayStatus().equals("L")){
                    txt_leaveReason.setText(attendanceModel.getLeaveReason());
                    txt_leaveType.setText(attendanceModel.getLeavetype());
                    txt_fromDate.setVisibility(View.VISIBLE);
                    txt_fromDate.setText(attendanceModel.getInTime());
                    txt_toDate.setVisibility(View.VISIBLE);
                    txt_toDate.setText(attendanceModel.getOutTime());
                    txt_leaveStatus.setVisibility(View.VISIBLE);
                    txt_leaveStatus.setText(attendanceModel.getApprovalFlag());
                    txt_totalDays.setText(attendanceModel.getDayStatus());
                    txt_appliedDate.setText(attendanceModel.getAppliedDate());
                }else if (attendanceModel.getDayStatus().equals("A")){
                    txt_fromDate.setVisibility(View.GONE);
                    txt_toDate.setVisibility(View.GONE);
                    txt_totalDays.setVisibility(View.GONE);
                    txt_leaveType.setText("Absent");
                    txt_leaveStatus.setText("Absent");
                }else if (attendanceModel.getDayStatus().equals("P")){
                    txt_leaveReason.setVisibility(View.GONE);
                    txt_leaveType.setText("Present");
                    txt_fromDate.setVisibility(View.VISIBLE);
                    txt_fromDate.setText(attendanceModel.getInTime());
                    txt_toDate.setVisibility(View.VISIBLE);
                    txt_toDate.setText(attendanceModel.getOutTime());
                    txt_leaveStatus.setVisibility(View.VISIBLE);
                    txt_leaveStatus.setText(attendanceModel.getDayStatus());
                    txt_totalDays.setVisibility(View.GONE);
                    txt_appliedDate.setVisibility(View.GONE);
                }
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceModelList.size();
    }

    public class AttendanceHolder extends RecyclerView.ViewHolder {
        TextView date,inTime,outTime,leaveReason,approvalFlag,appliedDate,pinNo,dayStatus,leavetype;
        CardView leaveAppliedCardView;
        public AttendanceHolder(View itemView) {
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
