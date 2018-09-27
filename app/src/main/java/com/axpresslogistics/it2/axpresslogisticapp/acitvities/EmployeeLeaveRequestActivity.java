package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.LeaveApprovalAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.LeaveApprovalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeLeaveRequestActivity extends AppCompatActivity implements View.OnClickListener{
    String APPROVAL_LEAVE_SHOW = CONSTANT.DEVELOPMENT_URL + "HRMS/approve_leave_show";
    String APPROVAL_LEAVE_UPDATE = CONSTANT.DEVELOPMENT_URL + "HRMS/approve_leave_update";
    String supervisior_id;
    ConstraintLayout no_data_availableLayout;
    LeaveApprovalAdaptor adaptor;
    List<LeaveApprovalModel> modelList;
    RecyclerView recyclerView;
    ImageView refresh_image;
    ImageButton backbtn_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_leave_request);
        //custom toolbar...
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.MARK_ATTENDANCE);
        init();

        recyclerView = findViewById(R.id.leave_requestRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();

        leave_show_method();
    }

    private void init() {
        refresh_image = findViewById(R.id.refresh_image);
        refresh_image.setOnClickListener(this);
        no_data_availableLayout = findViewById(R.id.no_data_availableLayout);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        supervisior_id = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    private void leave_show_method() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        ApiKey apiKey = new ApiKey();
        final String method = "approve_leave_show";
        final String key = apiKey.saltStr();
        progressDialog.setMessage("Data fetching...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPROVAL_LEAVE_SHOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Leave Response :", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String KEY = object.getString(CONSTANT.KEY);

                            if (status.equals(CONSTANT.TRUE) && KEY.equals(key)) {
                                JSONArray array = object.getJSONArray("approve_leave");
                                no_data_availableLayout.setVisibility(View.GONE);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    LeaveApprovalModel approvalModel = new LeaveApprovalModel(
                                            jsonObject.optString("id"),
                                            jsonObject.optString("emplid"),
                                            jsonObject.optString("name"),
                                            jsonObject.optString("leave_type"),
                                            jsonObject.optString("from_date"),
                                            jsonObject.optString("to_date"),
                                            jsonObject.optString("days"),
                                            jsonObject.optString("reason"),
                                            jsonObject.optString("approval_status"),
                                            jsonObject.optString("approval_comment"));
                                    modelList.add(approvalModel);
                                }
                                adaptor = new LeaveApprovalAdaptor(getApplicationContext(), modelList);
                                recyclerView.setAdapter(adaptor);
                            } else {
                                no_data_availableLayout.setVisibility(View.VISIBLE);
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
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, key);
                params.put(CONSTANT.SUPERVISIOR_ID, supervisior_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.refresh_image:
                leave_show_method();
                break;
        }
    }
}
