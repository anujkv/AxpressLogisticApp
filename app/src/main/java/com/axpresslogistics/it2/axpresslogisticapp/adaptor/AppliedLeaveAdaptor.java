package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.ApplyLeaveActivity;
import com.axpresslogistics.it2.axpresslogisticapp.model.AppliedLeaveModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppliedLeaveAdaptor extends RecyclerView.Adapter<AppliedLeaveAdaptor.AppliedLeaveHolder> {
    Context context;
    List<AppliedLeaveModel> appliedLeaveModelList;
    Dialog dialog;
    Button edit_btn;

    public AppliedLeaveAdaptor(Context context, List<AppliedLeaveModel> appliedLeaveModelList) {
        this.context = context;
        this.appliedLeaveModelList = appliedLeaveModelList;
    }

    @NonNull
    @Override
    public AppliedLeaveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.applied_leave_listview, parent, false);
        return new AppliedLeaveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppliedLeaveHolder holder, int position) {
        final AppliedLeaveModel appliedLeaveModel = appliedLeaveModelList.get(position);
        String from = dateConversion(appliedLeaveModel.getFrom());
        holder.id.setText(appliedLeaveModel.getId());
        holder.from.setText(from);
        holder.reason.setText(appliedLeaveModel.getReason().trim());
        holder.day.setText(appliedLeaveModel.getDays() + " day");
        holder.type.setText(appliedLeaveModel.getType());
        if (appliedLeaveModel.getLeave_status().toLowerCase().equals("pending")) {
            holder.type.setBackgroundColor(Color.YELLOW);
            holder.leaveStatusIndicator.setCircleBackgroundColor(Color.YELLOW);
        } else if (appliedLeaveModel.getLeave_status().toLowerCase().equals("approved")) {
            holder.type.setBackgroundColor(Color.GREEN);
            holder.leaveStatusIndicator.setCircleBackgroundColor(Color.GREEN);
        } else if (appliedLeaveModel.getLeave_status().toLowerCase().equals("pushback")) {
            holder.type.setBackgroundColor(Color.DKGRAY);
            holder.leaveStatusIndicator.setCircleBackgroundColor(Color.DKGRAY);
        } else if (appliedLeaveModel.getLeave_status().toLowerCase().equals("unapproved") ||
                appliedLeaveModel.getLeave_status().toLowerCase().equals("rejected")) {
            holder.type.setBackgroundColor(Color.RED);
            holder.leaveStatusIndicator.setCircleBackgroundColor(Color.RED);
        } else {
            holder.type.setBackgroundColor(Color.WHITE);
            holder.leaveStatusIndicator.setCircleBackgroundColor(Color.WHITE);
        }

        String to = dateConversion(appliedLeaveModel.getTo());
        holder.to.setText(to);
        holder.pin_no.setText(appliedLeaveModel.getPin_no());
        holder.applied_date.setText(appliedLeaveModel.getApplied_date());
        holder.leave_status.setText(appliedLeaveModel.getLeave_status());

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.attendance_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt_id,txt_leaveType, txt_clickedDate, txt_leaveReason, txt_fromDate,
                        txt_toDate, txt_leaveStatus, txt_totalDays, txt_appliedDate;
                txt_leaveType = dialog.findViewById(R.id.txt_leaveType);
                txt_leaveReason = dialog.findViewById(R.id.txt_leaveReason);
                txt_fromDate = dialog.findViewById(R.id.txt_fromDate);
                txt_toDate = dialog.findViewById(R.id.txt_toDate);
                txt_leaveStatus = dialog.findViewById(R.id.txt_leaveStatus);
                txt_totalDays = dialog.findViewById(R.id.txt_totalDays);
                txt_appliedDate = dialog.findViewById(R.id.txt_appliedDate);
                edit_btn = dialog.findViewById(R.id.edit_btn);
                txt_leaveReason.setText(appliedLeaveModel.getReason().trim());
                txt_fromDate.setText(dateConversion(appliedLeaveModel.getFrom()));
                txt_toDate.setText(dateConversion(appliedLeaveModel.getTo()));
                txt_leaveStatus.setText(appliedLeaveModel.getLeave_status());
                if (appliedLeaveModel.getLeave_status().equals("approved")) {
                    txt_leaveType.setBackgroundColor(Color.GREEN);
                    txt_leaveType.setTextColor(Color.RED);
                    edit_btn.setVisibility(View.GONE);
                } else if (appliedLeaveModel.getLeave_status().toLowerCase().equals("pending")) {
                    txt_leaveType.setBackgroundColor(Color.YELLOW);
                    txt_leaveType.setTextColor(Color.RED);
                    edit_btn.setVisibility(View.GONE);
                } else if (appliedLeaveModel.getLeave_status().toLowerCase().equals("unapproved")) {
                    txt_leaveType.setBackgroundColor(Color.RED);
                    txt_leaveType.setTextColor(Color.WHITE);
                    edit_btn.setVisibility(View.GONE);
                } else if (appliedLeaveModel.getLeave_status().equals("pushback")) {
                    txt_leaveType.setBackgroundColor(Color.DKGRAY);
                    txt_leaveType.setTextColor(Color.WHITE);
                    edit_btn.setVisibility(View.VISIBLE);

                    edit_btn.setOnClickListener(new View.OnClickListener() {
                        ApiKey apiKey = new ApiKey();
                        String key = apiKey.saltStr();
                        String emplid = Preferences.getPreference(context, CONSTANT.EMPID);

                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ApplyLeaveActivity.class);
                            intent.putExtra("id", appliedLeaveModel.getId());
                            intent.putExtra("from", appliedLeaveModel.getFrom());
                            intent.putExtra("to", appliedLeaveModel.getTo());
                            intent.putExtra("type", appliedLeaveModel.getType());
                            intent.putExtra("days", appliedLeaveModel.getDays());
                            intent.putExtra("reason", appliedLeaveModel.getReason());
                            intent.putExtra("pin_no", appliedLeaveModel.getType());
                            intent.putExtra(CONSTANT.METHOD, "edit_leave");
                            intent.putExtra(CONSTANT.KEY, key);
                            intent.putExtra(CONSTANT.EMPID, emplid);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
                txt_totalDays.setText(appliedLeaveModel.getDays() + " Day");
                txt_appliedDate.setText(appliedLeaveModel.getApplied_date().substring(0, 11));
                txt_leaveType.setText(appliedLeaveModel.getType().trim());
                dialog.show();
            }
        });
    }

    private String dateConversion(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yyyy");
        String finalFromDate = timeFormat.format(fromDate);
        return finalFromDate;
    }

    @Override
    public int getItemCount() {
        return appliedLeaveModelList.size();
    }

    public class AppliedLeaveHolder extends RecyclerView.ViewHolder {
        TextView id,from, to, reason, type, pin_no, day, applied_date, leave_status;
        CardView cardView;
        CircleImageView leaveStatusIndicator;

        public AppliedLeaveHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtid);
            from = itemView.findViewById(R.id.txtfromdate);
            to = itemView.findViewById(R.id.txttodate);
            reason = itemView.findViewById(R.id.txtreason);
            day = itemView.findViewById(R.id.days);
            type = itemView.findViewById(R.id.txttype);
            pin_no = itemView.findViewById(R.id.txtpin_no);
            applied_date = itemView.findViewById(R.id.txtappliedDate);
            leave_status = itemView.findViewById(R.id.txtleave_status);
            cardView = itemView.findViewById(R.id.leaveAppliedCardView);
            leaveStatusIndicator = itemView.findViewById(R.id.leaveStatusIndicator);
        }
    }
}
