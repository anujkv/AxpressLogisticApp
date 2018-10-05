package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.model.LeaveApprovalModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaveApprovalAdaptor extends RecyclerView.Adapter<LeaveApprovalAdaptor.ViewHolder> {
    Context context;
    List<LeaveApprovalModel> approvalModels;
    String leaved_status,approval_status;

    public LeaveApprovalAdaptor(Context context, List<LeaveApprovalModel> approvalModels) {
        this.context = context;
        this.approvalModels = approvalModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leave_approval_show_listview,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final LeaveApprovalModel model = approvalModels.get(position);
        holder.id.setText(model.getId());
        holder.emplid.setText(model.getEmplid());
        holder.applier_name.setText(model.getApplier_name());
        holder.leave_type.setText(model.getLeave_type());
        holder.from_date.setText(model.getFrom_date());
        holder.to_date.setText(model.getTo_date());
        holder.days.setText(model.getDays());
        holder.reason.setText(model.getReason());
        holder.approval_status.setText(model.getApproval_status());
        holder.approval_comment.setText(model.getApproval_comment());
        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context.getApplicationContext(), "Approved", Toast.LENGTH_SHORT).show();
                holder.approved.setTextColor(Color.GREEN);
                holder.denied.setTextColor(Color.WHITE);
                holder.pushback.setTextColor(Color.WHITE);
                approval_status = "approved";
                push_data(holder,model,approval_status);

            }
        });
        holder.denied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context.getApplicationContext(), "Denied", Toast.LENGTH_SHORT).show();
                holder.denied.setTextColor(Color.RED);
                holder.approved.setTextColor(Color.WHITE);
                holder.pushback.setTextColor(Color.WHITE);
                approval_status = "unapproved";
                push_data(holder,model,approval_status);
            }
        });
        holder.pushback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pushback.setTextColor(Color.YELLOW);
                holder.approved.setTextColor(Color.WHITE);
                holder.denied.setTextColor(Color.WHITE);
                approval_status = "pushback";
                push_data(holder,model,approval_status);
                holder.cardView.removeAllViews();
            }
        });
    }

    @Override
    public int getItemCount() {
        return approvalModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, emplid, applier_name, leave_type, from_date, to_date, days, reason, approval_status,
                approval_comment;
        Button approved, denied,pushback;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.leave_id);
            emplid = itemView.findViewById(R.id.emplid);
            applier_name = itemView.findViewById(R.id.applier_name);
            leave_type = itemView.findViewById(R.id.leave_type);
            from_date = itemView.findViewById(R.id.leave_from);
            to_date = itemView.findViewById(R.id.leave_to);
            days = itemView.findViewById(R.id.leave_days);
            reason = itemView.findViewById(R.id.leave_reason);
            approval_status = itemView.findViewById(R.id.approval_status);
            approval_comment = itemView.findViewById(R.id.approval_comment);
            approved = itemView.findViewById(R.id.approve_btn);
            denied = itemView.findViewById(R.id.denied_btn);
            pushback = itemView.findViewById(R.id.pushback_btn);
            cardView = itemView.findViewById(R.id.approved_cardview);
        }
    }

    private void push_data(ViewHolder holder, final LeaveApprovalModel model, final String approval_status) {
        ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        final String method = "approve_leave_update";
        String APPROVAL_LEAVE_UPDATE = CONSTANT.DEVELOPMENT_URL + "HRMS/approve_leave_update";
        final String
                supervisior_id = Preferences.getPreference(context, CONSTANT.EMPID);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPROVAL_LEAVE_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressDialog.dismiss();
                        Log.e("Leave Response :", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String KEY = object.getString(CONSTANT.KEY);

                            if (status.equals(CONSTANT.TRUE) && KEY.equals(key)) {
                                if(approval_status.equals("approved")){
                                    leaved_status = "Leave Approved";
                                    Toast.makeText(context,model.getId()+ " - " + leaved_status,Toast.LENGTH_SHORT).show();
                                } else if (approval_status.equals("unapproved")){
                                    leaved_status = "Leave Denied";
                                    Toast.makeText(context,model.getId()+ " - " + leaved_status,Toast.LENGTH_SHORT).show();
                                }else if (approval_status.equals("pushback")){
                                    leaved_status = "Leave PushBack";
                                    Toast.makeText(context,model.getId()+ " - " + leaved_status,Toast.LENGTH_SHORT).show();
                                }

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======", "" + error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(context,
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context,
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
//                        progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, key);
                params.put("approval_status", approval_status);
                params.put(CONSTANT.SUPERVISIOR_ID, supervisior_id);
                params.put("id", model.getId());
                Log.e("approval_status",approval_status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
