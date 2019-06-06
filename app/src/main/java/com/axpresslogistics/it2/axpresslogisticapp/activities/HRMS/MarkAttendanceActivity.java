package com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module.EmpProfileActivity;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.MarkAttendance.MarkAttendanceResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.MarkAttendance.MarkAttendancePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.services.GetShareLocationService;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.NativeHelper;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.MarkAttendanceView;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MarkAttendanceActivity extends AppCompatActivity implements LocationListener,
        MarkAttendanceView, View.OnClickListener {
    static final int REQUEST_LOCATION = 1;
    public static TextView txtUsername, txtUserId, txtDateTime, txtDept, txtBranch, txtDesignation;
    String strUsername, strUserId, strDateTime, strDept, strBranch, strDesignation, formattedDate;
    ImageView userImage;
    Button attendance_btn;
    Location location;
    LocationManager locationManager;
    Intent intent;
    Boolean FLAG = true;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    String locationPref = CONSTANT.INFO_NO,add;
    byte[] image = null;
    //Longitude and latitude Information...\
    double lat= 0.0, lon =0.0,latitude = 0.0,longitude = 0.0;
     EmpProfileActivity profileActivityInstance;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    List<Address> addresses = null;
//    Intent intent;
    MainPresenter presenter;
    ApiKey apiKey = new ApiKey();
    final String method = "attendance";
    final String apikey = apiKey.saltStr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        //custom toolbar...
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.MARK_ATTENDANCE);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        attendance_btn.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        latlong();
        profileActivityInstance  = new EmpProfileActivity();
        getValuesFromPref();
        setValuesInFields();

//        if (FLAG.equals(true)) {
//            attendance_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                        markAttendance();
//                        FLAG = false;
//                }
//            });
//        }
    }

    private void latlong() {
        locationTrack = new LocationTrack(this);
        String errorMessage = "";

        if (locationTrack.canGetLocation()) {

            locationTrack = new LocationTrack(this);
            if (locationTrack.canGetLocation()) {
                longitude = locationTrack.getLongitude();
                latitude = locationTrack.getLatitude();
                Log.e("L & L", longitude + ", " + latitude);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(
                            longitude,
                            latitude,
                            // In this sample, get just a single address.
                            1);
                } catch (IOException ioException) {
                    // Catch network or other I/O problems.
                    errorMessage = getString(R.string.service_not_available);
                    Log.e("TAG", errorMessage, ioException);
                } catch (IllegalArgumentException illegalArgumentException) {
                    // Catch invalid latitude or longitude values.
                    errorMessage = getString(R.string.invalid_lat_long_used);
                    Log.e("TAG", errorMessage + ". " +
                            "Latitude = " + latitude +
                            ", Longitude = " +
                            longitude, illegalArgumentException);
                }

                // Handle case where no address was found.
                if (addresses == null || addresses.size() == 0) {
                    if (errorMessage.isEmpty()) {
                        errorMessage = getString(R.string.no_address_found);
                        Log.e("TAG", errorMessage);
                    }
//                    deliverResultToReceiver(SyncStateContract.Constants.FAILURE_RESULT, errorMessage);
                } else {
                    Address address = addresses.get(0);
                    ArrayList<String> addressFragments = new ArrayList<String>();

                    // Fetch the address lines using getAddressLine,
                    // join them, and send them to the thread.
                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressFragments.add(address.getAddressLine(i));
                    }
                    Log.e("TAG", getString(R.string.address_found));
                    Log.e("address", new Gson().toJson(addressFragments));
//                    deliverResultToReceiver(Constant.SUCCESS_RESULT,
//                            TextUtils.join(System.getProperty("line.separator"),
//                                    addressFragments));
                }
            } else {

                locationTrack.showSettingsAlert();
            }
        } else {
            locationTrack.showSettingsAlert();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOK(
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                }

                break;
        }

    }

    public void showMessageOK(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("These permissions are mandatory for the application. Please allow access.")
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }

    private void markAttendance() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(CONSTANT.GETTING_LOCATION);
        disable_button();
        if(latitude != 0.0 && longitude!= 0.0) {
//            pushAttendance();
        }
        else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(latitude != 0.0 && longitude!= 0.0) {
//                        pushAttendance();
                    }else{
                        Toast.makeText(getApplicationContext(), CONSTANT.LOCATION_NOT_GETTING, Toast.LENGTH_SHORT).show();
                        enable_button();
                    }
                }
            }, 10000);
        }
    }

    private void init() {
        txtUsername = findViewById(R.id.user_nameId);
        txtUserId = findViewById(R.id.user_id);
        txtDesignation = findViewById(R.id.txtDesignationId);
        txtBranch = findViewById(R.id.txtBranchId);
        txtDept = findViewById(R.id.txtDeptID);
        userImage = findViewById(R.id.user_imageId);
        attendance_btn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.attendance_progressbarId);

    }

    private void getValuesFromPref() {
        strUsername = Preferences.getPreference(getApplicationContext(), CONSTANT.USER_NAME);
        strUserId = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        strDesignation = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_DESIGNATION.trim());
        strDept = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_DEPT.trim());
        strBranch = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_BRANCH.trim());
        String image_profile = Preferences.getPreference(getApplicationContext(), CONSTANT.USER_IMAGE.trim());
        String imageUrl = Preferences.getPreference(getApplicationContext(), CONSTANT.IMAGEURL.trim());
        Log.e("image_profile",image_profile);
        Log.e("imageUrl",imageUrl);
        if(imageUrl!=null && !imageUrl.equals("null")){
            userImage.setImageBitmap(profileActivityInstance.convertBasetoBitmap(imageUrl));
        }
        else{
            Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE )
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(userImage);
        }

    }

    private void setValuesInFields() {
        txtUsername.setText(strUsername.trim());
        txtUserId.setText(strUserId.trim());
        txtBranch.setText(strBranch.trim());
        txtDesignation.setText(strDesignation.trim());
        txtDept.setText(strDept.trim());
        attendance_btn.setText("Submit Attendance");
    }

    private void date_time() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    }

    private void getLocationPermissionCheck() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(getApplicationContext(), Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.
                            ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String latLong = location.toString();
        lat = location.getLatitude();
        lon = location.getLongitude();
        add = getAddress(lat,lon);
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MarkAttendanceActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            Log.e("Address>>", new Gson().toJson(geocoder.getFromLocation(lat, lng, 1)));
            addresses = geocoder.getFromLocation(lat, lng, 1);
            Log.e("Address", new Gson().toJson(addresses));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Address obj = null;
            if (addresses != null) {
                obj = addresses.get(0);
                add = obj.getAddressLine(0);
                String  currentAddress = obj.getSubAdminArea() + ","
                        + obj.getAdminArea();

                latitude = obj.getLatitude();
                longitude = obj.getLongitude();
                String currentCity= obj.getSubAdminArea();
                String currentState= obj.getAdminArea();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return add;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MarkAttendanceActivity.this, "Please Enable GPS and Internet",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void disable_button() {
        attendance_btn.setTextColor(getColor(R.color.white));
        progressDialog.show();
        attendance_btn.setClickable(false);
    }

    private void enable_button() {
        attendance_btn.setTextColor(getColor(R.color.black));
        progressDialog.dismiss();
        attendance_btn.setClickable(true);
    }

    @Override
    public String getLatitude() {
        return String.valueOf(latitude);
    }

    @Override
    public String getLongitude() {
        return String.valueOf(longitude);
    }

    @Override
    public String getDate_time() {
        return formattedDate;
    }

    @Override
    public String getEmp_id() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public String getKey() {
        return apikey.trim();
    }

    @Override
    public String getLocation() {
        return String.valueOf(getAddress(latitude,longitude));
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof MarkAttendanceResponse) {
            MarkAttendanceResponse response = (MarkAttendanceResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                Date timeLogoutTime = Calendar.getInstance().getTime();
                int timeL = timeLogoutTime.getHours();
                Log.e("timeLogout", String.valueOf(timeL));
                if(timeL<=20){
                    intent = new Intent(MarkAttendanceActivity.this, GetShareLocationService.class);
                    startService(intent);
                }
                else{
                    intent = new Intent(MarkAttendanceActivity.this, GetShareLocationService.class);
                    stopService(intent);
                }
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    startService(new Intent(MarkAttendanceActivity.this, GetShareLocationService.class));
                    Toast.makeText(getApplicationContext(), " Your attandance has marked Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Attandance not mark", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), "server error,try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                date_time();
                if (NativeHelper.isConnectionAvailable(this)) {
                    presenter = new MarkAttendancePresenterImpl(this);
                    presenter.init();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
