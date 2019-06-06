package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.MapResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MapPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.view.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,
        MapView {
    //    ProgressDialog progressDialog;
    LoadingLayout loadingLayout = new LoadingLayout();
    private GoogleMap mMap;
    ImageButton backbtn_toolbar, mapbtn_toolbar;
    Intent intent;
    ApiKey apiKey = new ApiKey();
    double latitude, longitude;
    String tcs_no, docketno;
    EditText edtDocket, edtVehicle, edtOrigin, edtDesignation, edtStartTime, edtCLocation,
            edtRADestination, edtLocation, edtTCS;
    MainPresenter presenter;

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

        intent = getIntent();
        docketno = intent.getStringExtra("docket");
        map();

    }

    private void map() {
        presenter = new MapPresenterImpl(this);
        presenter.init();

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
        if (tcs.latitude == 0.0 && tcs.longitude == 0.0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.4995993, 77.0738609), 3.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tcs));
        } else {
            mMap.addMarker(new MarkerOptions().position(tcs));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14.0f));
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
        final View dialogView = inflater.inflate(R.layout.map_details_view, null);
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

    }

    @Override
    public String getDocketNo() {
        return docketno;
    }

    @Override
    public String getKey() {
        return apiKey.saltStr();
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public void showLoadingLayout() {
//        progressDialog = new ProgressDialog(MapsActivity.this) {
//            @Override
//            public void onBackPressed() {
//                progressDialog.dismiss();
//            }
//        };
//        progressDialog.setMessage("Loading");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        loadingLayout.showLoadingLayout(this);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();
    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof MapResponse) {
            MapResponse mapResponse = (MapResponse) o;
            if (mapResponse.getStatus().equals(CONSTANT.TRUE)) {
                try {

                    latitude = Double.parseDouble((String) mapResponse.getLatitude());
                    longitude = Double.parseDouble((String) mapResponse.getLongitude());
                    tcs_no = (String) mapResponse.getTcsNo();
                    edtDocket.setText(docketno);
                    edtVehicle.setText((String) mapResponse.getVehicleNo());
                    edtOrigin.setText((String) mapResponse.getOrigin());
                    edtDesignation.setText((String) mapResponse.getDestination());
                    edtStartTime.setText((String) mapResponse.getRecordDatetime());
                    edtCLocation.setText(mapResponse.getLatitude() + ", " + mapResponse.getLongitude());
                    edtRADestination.setText((String) mapResponse.getReceivedAtDest());
                    edtLocation.setText((String) mapResponse.getDestination());
                    edtTCS.setText((String) mapResponse.getTcsNo());

                    getlocation();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), CONSTANT.serverNotReachable, Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }
}
