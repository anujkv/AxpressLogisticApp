package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;
import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.TIMEOUT_ERROR;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
//    private String url = CONSTANT.URL + "Operations/map";
    private String url = URL + "Operations/map";

    private GoogleMap mMap;
    ImageButton backbtn_toolbar,mapbtn_toolbar;
    Intent intent;
    ApiKey apiKey;
    double latitude,longitude;
    String receive_at_destination,tcs_no,vehicle_no,location,origin,destination,start_time,docketno,
            current_location;
    EditText edtDocket,edtVehicle,edtOrigin,edtDesignation,edtStartTime,edtCLocation,
            edtRADestination,edtLocation,edtTCS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Docket Tracking Details");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        mapbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        mapbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_information));
        backbtn_toolbar.setOnClickListener(this);
        mapbtn_toolbar.setOnClickListener(this);
        apiKey = new ApiKey();
        map();

    }

    private void map() {
        intent = getIntent();
        final String method = "map";
        final String apikey = apiKey.saltStr();
        final String docket_no = intent.getStringExtra("docket");
        docketno = docket_no;
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("status");
                    String apikeyResponse = object.optString("key");
                    Log.d("Response ==== ",object.toString());

                    if(status.equals("true") && apikeyResponse.equals(apikey)){
                        String str_latitude = object.optString("latitude");
                        String str_longitude = object.optString("longitude");

                        latitude = Double.parseDouble(str_latitude);
                        longitude = Double.parseDouble(str_longitude);
                        Log.d("Response ====> ",object.toString());
                        tcs_no = object.optString("tcs_no");

                        vehicle_no = object.optString("vehicle_no");
                        location  = object.optString("location");
                        origin  = object.optString("origin");
                        destination  = object.optString("destination");
                        start_time  = object.optString("record_datetime");
                        current_location  = object.optString(str_latitude + ", "+ str_longitude);
                        receive_at_destination  = object.optString("received_at_dest");
//
                        getlocation();

                    } else if(status.equals("false") && apikeyResponse.equals(apikey)){
                        Toast.makeText(getApplicationContext(),"Data not available",
                                Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error.toString());
                Toast.makeText(getBaseContext(),
                        CONSTANT.INFORMATION_NOT_AVAILABLE,
                        Toast.LENGTH_LONG).show();
                Log.e("VOLLEY_ERROR : ",TIMEOUT_ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("docket_no",docket_no);
                param.put("method",method);
                param.put("key",apikey);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getlocation() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng tcs = new LatLng(latitude, longitude);
        if (tcs.latitude == 0.0 && tcs.longitude == 0.0){
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(28.4995993,77.0738609), 3.0f) );
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tcs));
        } else {
            mMap.addMarker(new MarkerOptions().position(tcs));
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude) , 14.0f) );
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tcs));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabled(true);
            googleMap.getUiSettings().setTiltGesturesEnabled(true);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(/*current latitude*/latitude, /*current longitude*/longitude))      // Sets the center of the map to location user
//                .zoom(17)                   // Sets the zoom
//                .build();                   // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                showDetails();
        }
    }

    private void showDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.map_details_view,null);
        builder.setView(dialogView);
        initDialogView(dialogView);
        builder.setTitle("Map Details");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog b = builder.create();
        b.show();
    }

    private void initDialogView(View dialogView) {
        edtDocket = dialogView.findViewById(R.id.edtDocket);
        edtVehicle = dialogView.findViewById(R.id.edtVehicle);
        edtOrigin = dialogView.findViewById(R.id.edtOrigin);
        edtDesignation = dialogView.findViewById(R.id.edtDesignation);
        edtStartTime = dialogView.findViewById(R.id.edtStartTime);
        edtCLocation = dialogView.findViewById(R.id.edtCLocation);
        edtRADestination = dialogView.findViewById(R.id.edtRADestination);
        edtLocation = dialogView.findViewById(R.id.edtLocation);
        edtTCS = dialogView.findViewById(R.id.edtTCS);

        edtDocket.setText(docketno);
        edtVehicle.setText(vehicle_no);
        edtOrigin.setText(origin);
        edtDesignation.setText(destination);
        edtStartTime.setText(start_time);
        edtCLocation.setText(current_location);
        edtRADestination.setText(receive_at_destination);
        edtLocation.setText(location);
        edtTCS.setText(tcs_no);
    }
}
