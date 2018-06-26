package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.it2.axpresslogisticapp.acitvities.ApiKey;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private String url = "http://webapi.axpresslogistics.com/api/Operations/map";
    private GoogleMap mMap;
    Intent intent;
    ApiKey apiKey;
    double latitude,longitude;
    String receive_at_destination,tcs_no,vehicle_no,location,origin,destination,start_time,
            current_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        apiKey = new ApiKey();

        map();

    }

    private void map() {
        intent = getIntent();
        final String method = "map";
        final String apikey = apiKey.saltStr();
        final String docket_no = intent.getStringExtra("docket");

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
//                        tcs_no = object.optString("tcs_no");

                        vehicle_no = object.optString("vehicle_no");
//                        location  = object.optString("location");
//                        origin  = object.optString("origin");
//                        destination  = object.optString("destination");
//                        start_time  = object.optString("start_time");
//                        current_location  = object.optString("current_location");
//                        receive_at_destination  = object.optString("recevied_at_destination");
//                        Toast.makeText(getApplicationContext(),"Latitude = "+ latitude +
//                                " , longitude = "+ longitude,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"receive_at_destination = "+
//                                receive_at_destination,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"tcs_no = "+ tcs_no,
//                                Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"vehicle_no = "+ vehicle_no
                               ,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"location = "+ location,
//                                Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"origin = "+ origin,
//                                Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"destination = "+ destination,
//                                Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"start_time = "+ start_time,
//                                Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"current_location = "+ current_location,
//                                Toast.LENGTH_SHORT).show();
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
                if(error.toString().equals("com.android.volley.ServerError")){
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500",
                            Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
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
