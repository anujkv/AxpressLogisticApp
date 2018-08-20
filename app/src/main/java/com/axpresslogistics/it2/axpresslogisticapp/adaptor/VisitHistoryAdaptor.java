package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
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
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.CustomerVisitFormActivity;
import com.axpresslogistics.it2.axpresslogisticapp.model.VisitHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class VisitHistoryAdaptor extends RecyclerView.Adapter<VisitHistoryAdaptor.HistoryHolder> {
    Context mContext;
    List<VisitHistoryModel> visitHistoryModels;
    Dialog dialog;
    VisitHistoryModel visitHistoryModel;
    String VISIT_HISTORY_URL = URL+ "Operations/show_history";
    private EditText edt_coustomer_company,edt_visit_date,edt_visit_for,edt_visit_type,edt_contact_person,
            edt_contact_no,edt_email_id,edt_address,edt_product,edt_status,edt_scope,edt_remark,
            edt_other_employee_name;
    TextView ref_no;

    public VisitHistoryAdaptor(Context context, List<VisitHistoryModel> visitHistoryModels) {
        this.mContext = context;
        this.visitHistoryModels = visitHistoryModels;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.visit_history_layout,parent,false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryHolder holder, int position) {
        visitHistoryModel = visitHistoryModels.get(position);
        holder.customer.setText(visitHistoryModel.getCustomer());
        holder.visit_date.setText(visitHistoryModel.getVisit_date());
        holder.visit_for.setText(visitHistoryModel.getVisit_for());
        holder.visit_type.setText(visitHistoryModel.getVisit_type());
        holder.ref_no.setText(visitHistoryModel.getRef_no());

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitHistoryModels.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView  customer,visit_date,visit_for,visit_type,ref_no;
        CardView cardView;
        public HistoryHolder(View itemView) {
            super(itemView);
            customer = itemView.findViewById(R.id.edt_customer_name);
            visit_date = itemView.findViewById(R.id.edtVisitDate);
            visit_for = itemView.findViewById(R.id.edt_visit_for);
            visit_type = itemView.findViewById(R.id.edt_visit_type);
            ref_no = itemView.findViewById(R.id.ref_no_id);
            cardView = itemView.findViewById(R.id.visit_history_layout);
        }
    }

    void MyDialog(){

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(CONSTANT.LOADING_STATUS);
        progressDialog.show();
        final String method = CONSTANT.SHOW_VISIT_DETAILS;

        ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VISIT_HISTORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && response.length() > 0) {
                            progressDialog.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.optString(CONSTANT.STATUS);
                                String apiKeyResponse = object.optString(CONSTANT.KEY);

                                if (status.equals(CONSTANT.TRUE)) {
                                    progressDialog.dismiss();
                                    JSONArray array = object.getJSONArray(CONSTANT.HISTORY);
                                    for(int i =0; i<array.length();i++){
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        setData(jsonObject);

                                    }
                                } else {
                                    Toast.makeText(mContext, CONSTANT.DATA_NOT_FOUND, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(mContext,
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(mContext,
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e(CONSTANT.REF_NO,visitHistoryModel.getRef_no());
                Log.e(CONSTANT.SHOW_VISIT,visitHistoryModel.getVisit_date().replace("/","-"));

                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, key);
                params.put(CONSTANT.REF_NO, visitHistoryModel.getRef_no());
                params.put(CONSTANT.VISIT_DATE, visitHistoryModel.getVisit_date().replace("/","-"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    private void setData(JSONObject jsonObject) {

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.visit_item_details_listview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        edt_coustomer_company = dialog.findViewById(R.id.edt_customer_name);
        edt_coustomer_company.setText(jsonObject.optString("customer"));
        edt_visit_date = dialog.findViewById(R.id.edtVisitDate);
        edt_visit_date.setText(jsonObject.optString("visit_date"));
        edt_visit_for = dialog.findViewById(R.id.edt_visit_for);
        edt_visit_for.setText(jsonObject.optString("visit_for"));
        edt_visit_type = dialog.findViewById(R.id.edt_visit_type);
        edt_visit_type.setText(jsonObject.optString("visit_type"));

        edt_contact_person = dialog.findViewById(R.id.edtContactPerson);
        edt_contact_person.setText(jsonObject.optString("contact_person"));
        edt_contact_no = dialog.findViewById(R.id.edtContactNo);
        edt_contact_no.setText(jsonObject.optString("contact"));
        edt_email_id = dialog.findViewById(R.id.edtEmail);
        edt_email_id.setText(jsonObject.optString("email_id"));
        edt_address = dialog.findViewById(R.id.edtAddress);
        edt_address.setText(jsonObject.optString("address"));
        edt_product = dialog.findViewById(R.id.edt_product_name);
        edt_product.setText(jsonObject.optString("product"));
        edt_status = dialog.findViewById(R.id.edt_status);
        edt_status.setText(jsonObject.optString("followup_status"));
        edt_scope = dialog.findViewById(R.id.edt_scope);
        edt_scope.setText(jsonObject.optString("scope"));
        edt_remark = dialog.findViewById(R.id.edtRemark);
        edt_remark.setText(jsonObject.optString("remark"));
        edt_other_employee_name = dialog.findViewById(R.id.edt_other_employee_name);
        edt_other_employee_name.setText(jsonObject.optString("other_employee_name"));
        dialog.show();
    }
}
