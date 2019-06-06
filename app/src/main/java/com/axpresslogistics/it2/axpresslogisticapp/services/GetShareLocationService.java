package com.axpresslogistics.it2.axpresslogisticapp.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
import com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS.LocationTrack;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS.MarkAttendanceActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT.URL;
import static java.util.Calendar.getInstance;

public class GetShareLocationService extends Service implements LocationListener {
    String formattedDate, add;
    String emplid;
    double lat = 0.0, lon = 0.0, latitude, longitude;
    String MARK_URl = URL + "HRMS/Attendance";
    MarkAttendanceActivity markInstance = new MarkAttendanceActivity();
    public static final int notify = 30*60000;  //interval between two services(Here Service run every 30 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling
    LocationManager locationManager;
    LocationTrack locationTrack;
    private TimerTask task;
    String CHANNEL_ID = "com.axpresslogistics.it2.axpresslogisticapp";
    Intent notificationIntent;
    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationTrack.showSettingsAlert();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30 * 60 * 1000, 0, this);

        notificationIntent = new
                Intent(GetShareLocationService.this, MarkAttendanceActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Location Service")
                .setContentText("Location Update")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .build();
        if(isOfftime()){
            startForeground(R.string.app_name, notification);

        }else{
            stopService(new Intent(this,GetShareLocationService.class));
        }
        return START_STICKY;

    }

    private boolean isOfftime() {
        Date timeLogoutTime = Calendar.getInstance().getTime();
        int timeL = timeLogoutTime.getHours();
        Log.e("timeLogout", String.valueOf(timeL));
        if(timeL>=20){
            stopService(notificationIntent);
            mTimer.cancel();
            task.cancel();
            stopSelf();
            return false;
        }else{
            return true;

        }
    }

    private void getPermission() {
    }

    @Override
    public void onCreate() {
        emplid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        fetchLocation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundLevelO();
        }
        else {
            startForeground(1, new Notification());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundLevelO() {
        String NOTIFICATION_CHANNEL_ID = "com.axpresslogistics.it2.axpresslogisticapp";
        String channelName = "Location Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        Intent notificationIntent = new
                Intent(GetShareLocationService.this, MarkAttendanceActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,notificationIntent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Location Service")
                .setContentText("Location Update")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build();
        Log.e("startForegroundLevelO","startForegroundLevelO");
        startForeground(2, notification);

    }

    private void fetchLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(task = new TimerTask(){
            public void run()
            {
                Log.e("Services", "Service is running");
                pushAttendance();
            }
        }, 30000, notify);
    }

    private void pushAttendance() {
        ApiKey apiKey = new ApiKey();
        final String method = "attendance";
        final String apikey = apiKey.saltStr();
        date_time();
        startService(new Intent(this, GetShareLocationService.class));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MARK_URl, new Response.
                Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("MarkResponse", response);
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("response");
                    String apiKEYresponse = object.optString("key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", emplid);
                params.put("method", method);
                params.put("key", apikey.trim());
                params.put("date_time", formattedDate);
                params.put("longitude", String.valueOf(lon));
                params.put("latitude", String.valueOf(lat));
                params.put("location", String.valueOf(add));
                Log.e("Params", new Gson().toJson(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void date_time() {
        Calendar c = getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    }

    @Override
    public void onLocationChanged(Location location) {
        String latLong = location.toString();
        lat = location.getLatitude();
        lon = location.getLongitude();
        add = getAddress(lat, lon);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(GetShareLocationService.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = new Geocoder(GetShareLocationService.this, Locale.getDefault()).getFromLocation(lat, lng, 1);
            Log.e("Address>>", new Gson().toJson(addresses));
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
        Toast.makeText(GetShareLocationService.this, "Please Enable GPS and Internet",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean stopService(Intent name) {
        // TODO Auto-generated method stub
        mTimer.cancel();
        task.cancel();
        return super.stopService(name);
    }
}
