package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.DocketEnquiry;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.DocketTracking;
import com.axpresslogistics.it2.axpresslogisticapp.model.InvoiceDocketModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class InvoiceDocketAdaptor extends RecyclerView.Adapter<InvoiceDocketAdaptor.DocketHolder> {

    Context context;
    List<InvoiceDocketModel> docketModelList;
    DocketEnquiry docketEnquiry;

    public InvoiceDocketAdaptor(Context context, List<InvoiceDocketModel> docketModelList) {
        this.context = context;
        this.docketModelList = docketModelList;
    }

    @NonNull
    @Override
    public DocketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.docket_listview_item,
                parent,false);
        return new DocketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocketHolder holder, int position) {
        final InvoiceDocketModel docketModel = docketModelList.get(position);
        holder.txt_docketID.setText(docketModel.getDocket_noID());
        holder.txt_consigneeID.setText(docketModel.getTxt_consigneeID());
        holder.txt_docketDateID.setText(docketModel.getTxt_docketDateID());
        holder.txt_fromID.setText(docketModel.getTxt_fromID());
        holder.txt_toID.setText(docketModel.getTxt_toID());
        holder.txt_statusID.setText(docketModel.getTxt_statusID());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String docket = docketModel.getDocket_noID();
                dataJsonFunction(docket);
            }
        });

    }

    @Override
    public int getItemCount() {
        return docketModelList.size();
    }

    public class DocketHolder extends RecyclerView.ViewHolder {
        TextView txt_docketID,txt_docketDateID,txt_consigneeID, txt_fromID,txt_toID, txt_statusID;
        CardView cardView;
        public DocketHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.docket_listview_cardview);
            txt_docketID = itemView.findViewById(R.id.txt_docketID);
            txt_docketDateID = itemView.findViewById(R.id.txt_docketDateID);
            txt_consigneeID = itemView.findViewById(R.id.txt_consigneeID);
            txt_fromID = itemView.findViewById(R.id.txt_fromID);
            txt_toID = itemView.findViewById(R.id.txt_toID);
            txt_statusID = itemView.findViewById(R.id.txt_statusID);
        }
    }

    public void dataJsonFunction(final String docket) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
//        submit_docket_btn.setClickable(false);
//        final String method;
        String url = URL + "Operations/Docket_Invoice";
        ApiKey apiKey = new ApiKey();
        final String method = CONSTANT.DOCKET;
        final String apikey = apiKey.saltStr();
//        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.
                Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString(CONSTANT.STATUS);
                    String apkKeyResponse = object.optString(CONSTANT.KEY);

                    if (status.equals(CONSTANT.TRUE)) {
                        Intent intent = new Intent(context, DocketTracking.class);
                        intent.putExtra(CONSTANT.RESPNOSE, response);
                        context.startActivity(intent);
                    } else if (status.equals(CONSTANT.FALSE)){
                        Toast.makeText(context,method + CONSTANT.NOT_FOUND,
                                Toast.LENGTH_SHORT).show();
                        progressDialog.show();
                    }
                    else{
                        Toast.makeText(context,CONSTANT.SOMETHING_WENT_WRONG,
                                Toast.LENGTH_SHORT).show();
//                        submit_docket_btn.setClickable(true);
                        progressDialog.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(context,
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context,
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context,
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
//                submit_docket_btn.setClickable(true);
                progressDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.DOCKET_NO, docket);
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, apikey);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
