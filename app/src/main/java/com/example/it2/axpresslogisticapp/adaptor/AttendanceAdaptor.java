package com.example.it2.axpresslogisticapp.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
            String outputText = outputFormat.format(date);
            holder.date.setText(outputText);
        }
//        holder.date.setText(attendanceModel.getDate());
        //If Input date not found then show leave reason...
        if(attendanceModel.getInTime().equals("") || !attendanceModel.getOutTime().isEmpty()){
            if(attendanceModel.getDayStatus().equals("P")){
                holder.inTime.setText(attendanceModel.getInTime());
            }
            else if (attendanceModel.getDayStatus().equals("A")){
                holder.inTime.setText(attendanceModel.getLeaveReason());
            }
            else if (attendanceModel.getDayStatus().equals("S")){
                holder.inTime.setText("Sunday");
                holder.inTime.setTextColor(Color.WHITE);
                holder.date.setTextColor(Color.WHITE);
                holder.dayStatus.setTextColor(Color.WHITE);
                holder.outTime.setVisibility(View.GONE);
                holder.inTime.setTextSize(18);
                holder.leaveAppliedCardView.setBackgroundColor(Color.RED);
            }
        }
//        holder.leaveReason.setText(attendanceModel.getInTime());

        //If Output date not found then show approval status...
//        if(attendanceModel.getOutTime().equals("") || attendanceModel.getOutTime().equals("00:00:00")
//                || !attendanceModel.getOutTime().isEmpty()){
//            if(attendanceModel.getDayStatus().equals("P")){
//                holder.outTime.setText(attendanceModel.getOutTime());
//            }else if (attendanceModel.getDayStatus().equals("A")){
//                holder.outTime.setText(attendanceModel.getApprovalFlag());
//            }else if (attendanceModel.getDayStatus().equals("S")){
//
//            }
//        }
        holder.outTime.setText(attendanceModel.getOutTime());
        holder.leaveReason.setText(attendanceModel.getOutTime());
        holder.leaveReason.setText(attendanceModel.getLeaveReason());
        holder.approvalFlag.setText(attendanceModel.getApprovalFlag());
        holder.appliedDate.setText(attendanceModel.getAppliedDate());
        holder.pinNo.setText(attendanceModel.getPinNo());

        //Set Day status if emp. absent then show type of leave, if type not found then show absent..
        if(attendanceModel.getDayStatus().equals("A")){
            if(attendanceModel.getApprovalFlag().equals("approved")){
                holder.dayStatus.setText(attendanceModel.getLeavetype());
            }else {
                holder.dayStatus.setText(attendanceModel.getDayStatus());
            }
        }else {
            holder.dayStatus.setText(attendanceModel.getDayStatus());
        }
        holder.leavetype.setText(attendanceModel.getLeavetype());
        holder.leaveAppliedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,attendanceModel.getDate(),Toast.LENGTH_SHORT).show();
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
