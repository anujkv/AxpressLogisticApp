package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.SavedCardAdaptor;
import com.example.it2.axpresslogisticapp.model.SavedCardModel;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SavedCardActivity extends AppCompatActivity {
    String url = "https://api.myjson.com/bins/s2rtg";
    RecyclerView saved_card_recyclerview;
    List<SavedCardModel> savedCardList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_card);
        Toolbar toolbar = findViewById(R.id.app_bar);
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
        saved_card_recyclerview = findViewById(R.id.saved_card_recyclerview);
        adapter = new SavedCardAdaptor(getApplicationContext(),savedCardList);

        savedCardList = new ArrayList<>();

        saved_card_recyclerview.setHasFixedSize(true);
        saved_card_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        saved_card_recyclerview.setAdapter(adapter);

        getData();

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.e("Response=======",response.toString());
//                JSONObject object = new JSONObject(response.toJSONObject("care_no"));
//                JSONArray jsonArray = new JSONArray();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonArrayRequest);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject object1 = object.getJSONObject("cards");
                    JSONArray jsonArray = object1.getJSONArray("card_no");
                    Log.e("jsonARRAY====",jsonArray.toString());

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        SavedCardModel cardModel = new SavedCardModel(object2.getString("user_image"),object2.getString(
                                "username"),object2.getString("website"),object2.getString("email"));
                        cardModel.setUsername(object2.getString("username"));
                        cardModel.setWebsite(object2.getString("website"));
                        cardModel.setEmail(object2.getString("email"));

                        savedCardList.add(cardModel);
                    }
                    adapter = new SavedCardAdaptor(getApplicationContext(),savedCardList);
                    saved_card_recyclerview.setAdapter(adapter);

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
