package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.adaptor.OperationAdaptor;
import com.example.it2.axpresslogisticapp.model.OperationModel;
import com.example.it2.axpresslogisticapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OperationActivity extends AppCompatActivity {
//    private String URL_JSON = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json";
//    private JsonArrayRequest ArrayRequest ;
//    private RequestQueue requestQueue ;
    private RecyclerView recyclerView;
    private List<OperationModel> operationModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
      //  Toast.makeText(OperationActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.operation_recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        operationModels = new ArrayList<>();
        dataload();
//        functionJsonCall();
    }

    private void dataload() {
        OperationModel opt = new OperationModel("Docket/ Invoice Enquiry","R.mipmap.icon_operation");
        operationModels.add(opt);
        setOptadapter(operationModels);
    }

//    private void functionJsonCall() {
//
//        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                JSONObject jsonObject = null;
//
//
//                for (int i = 0 ; i<response.length();i++) {
//
//                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
//
//                    try {
//
//                        jsonObject = response.getJSONObject(i);
//                        OperationModel optactivity = new OperationModel();
//
//                        optactivity.setOpt_name(jsonObject.getString("name"));
//                        optactivity.setOpt_icon(jsonObject.getString("img"));
////                        Toast.makeText(OperationActivity.this,optactivity.toString(),Toast.LENGTH_SHORT).show();
//                        operationModels.add(optactivity);
//                    }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                setOptadapter(operationModels);
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//
//        requestQueue = Volley.newRequestQueue(OperationActivity.this);
//        requestQueue.add(ArrayRequest);
//    }
//
    private void setOptadapter(List<OperationModel> operationModels) {
        OperationAdaptor myAdapter = new OperationAdaptor(this, operationModels) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }
}
