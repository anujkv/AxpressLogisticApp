package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.SavedCardAdaptor;
import com.example.it2.axpresslogisticapp.model.SaveCardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SavedCardActivity extends AppCompatActivity {
    String url = "https://api.myjson.com/bins/mgd6a";
    RecyclerView recyclerViewSCV;
    List<SaveCardModel>  cardModelList;
    SavedCardAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_card);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Saved Cards");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewSCV = findViewById(R.id.savedCardRecyclerView);
        recyclerViewSCV.setHasFixedSize(true);
        recyclerViewSCV.setLayoutManager(new LinearLayoutManager(this));
        cardModelList = new ArrayList<>();

        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject object1 = object.getJSONObject("cards");
                    JSONArray jsonArray = object1.getJSONArray("card_no");

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        SaveCardModel cardModel = new SaveCardModel(object2.getString("user_image"),object2.getString(
                                "username"),object2.getString("website"),object2.getString("email"));

                        cardModelList.add(cardModel);
                    }
                    adapter = new SavedCardAdaptor(getApplicationContext(),cardModelList);
                    recyclerViewSCV.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
