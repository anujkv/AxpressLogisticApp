package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.BuildConfig;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.CRMActivity;
import com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS.HrmsActivity;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.OperationActivity;
import com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module.ActivitiesActivity;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.GridViewAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.AppRoleResponse.AppRoleResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.AppRoleResponse.Module;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.AppRolePresenter.AppRolePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.AppRoleView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener,
        AppRoleView {
    public static String[] gridViewStrings = {
            "Operations",
            "HRMS",
            "CRM",
            "Activities",
            "Financial",
            "My Tickets"
    };
    public static int[] gridViewIcons = {
            R.drawable.operation,
            R.drawable.hrms,
            R.drawable.crm,
            R.drawable.activities,
            R.drawable.financial,
            R.drawable.ticket
    };

    Context context;

    byte[] image = null;
    @BindView(R.id.grid)
    GridView gridView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView empEmailId;
    TextView empName;

    ImageView empImage;
    String employeeID, employeeNAME, empEmail, image_profile, imageP = "";
    String appVersion;
    LocationManager locationManager;
    double lat, lon;
    ArrayList<String> list = new ArrayList<>();
    String currentVersion;
    boolean connected = false;
    CoordinatorLayout coordinatorLayout;
    Bitmap imageBitmap;
    String key;
    ProgressDialog progressDialog;
    MainPresenter presenter;
    List<Module> moduleList = new ArrayList<>();
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ButterKnife.bind(this);
        coordinatorLayout = findViewById(R.id.android_coordinator_layoutId);
        appVersion = BuildConfig.VERSION_NAME;
        Log.e("VERSION", appVersion);
        // get version................................
        checkNetworkconnection();
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("NEW_UPDATE");
        FirebaseMessaging.getInstance().subscribeToTopic("NEW_REMINDER");

        //Notification channel..
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CONSTANT.CHANNEL_ID, CONSTANT.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription(CONSTANT.CHANNEL_DEC);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        try {
            employeeNAME = Preferences.getPreference(MainHomeActivity.this, CONSTANT.USER_NAME);
            empEmail = Preferences.getPreference(MainHomeActivity.this, CONSTANT.EMAIL);
            employeeID = Preferences.getPreference(MainHomeActivity.this, CONSTANT.EMPID);
            image_profile = Preferences.getPreference(getApplicationContext(), CONSTANT.USER_IMAGE);
            imageP = Preferences.getPreference(getApplicationContext(), CONSTANT.IMAGEURL);
            if ((empEmailId == null || employeeID.equals("")) && (employeeNAME == null || employeeNAME.equals(""))) {
                logout();
            }

            Log.e("image_profile", imageP);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.e("URL : ", image_profile);
        setSupportActionBar(toolbar);
        GridViewAdaptor gridViewAdaptor = new GridViewAdaptor(MainHomeActivity.this,
                gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewAdaptor);

        user_permission_checks();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        check("operations");
                        break;
                    case 1:
                        check("hrms");
                        break;
                    case 2:
                        check("crm");
                        break;
                    case 3:
                        check("activities");
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Financial",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "My Tickets",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        //Drawerlayout functionality*************
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        View navView = navigationView.getHeaderView(0);
        //reference to views

        MenuItem item;
        empEmailId = navView.findViewById(R.id.user_email);
        empName = navView.findViewById(R.id.user_name);
        empImage = navView.findViewById(R.id.user_imageView);

        try {
            empImage.setImageBitmap(convertBasetoBitmap(imageP));
            if (imageP == null) {
                Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(empImage);
            }
            if (!imageP.equals("null")) {
                empImage.setImageBitmap(convertBasetoBitmap(imageP));

            } else if (!image_profile.equals("null")) {
                Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(empImage);
            } else {
                empImage.setImageDrawable(getResources().getDrawable(R.drawable.userimage));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //set views
        empName.setText(employeeNAME);
        empEmailId.setText(empEmail);

        Log.e("image_profile", image_profile);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private Bitmap convertBasetoBitmap(String image_profile) {
        byte[] decodedString = Base64.decode(image_profile, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public boolean checkNetworkconnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            getVersion();

        } else {
            connected = false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkNetworkconnection();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);

            snackbar.show();
        }
        return connected;
    }

    public void reconnect() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Reconnect");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        user_permission_checks();

                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void networkConnectionAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Internet Connection");
        builder.setCancelable(true);

        builder.setPositiveButton(

                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getVersion() {
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new GetVersionCode().execute();
//...............................................
    }

    private void check(String call) {
        if (list.contains(call)) {
            if (call.equals("operations")) {
                Intent intent = new Intent(getApplicationContext(), OperationActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            } else if (call.equals("hrms")) {
                Intent intent = new Intent(getApplicationContext(), HrmsActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            } else if (call.equals("crm")) {
                Intent intent = new Intent(getApplicationContext(), CRMActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            } else if (call.equals("activities")) {
                Intent intent = new Intent(getApplicationContext(), ActivitiesActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry, you don't have permission!,contact with IT Department.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void user_permission_checks() {
        final ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();

        presenter = new AppRolePresenterImpl(this);
        presenter.init();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent profiledataIntent = new Intent(getApplicationContext(), Profile.class);
            profiledataIntent.putExtra("response", Preferences.getPreference(MainHomeActivity.this, "response"));
            startActivity(profiledataIntent);

//            startActivity(new Intent(getApplicationContext(),EmpProfileActivity.class));
        } else if (id == R.id.nav_operations) {
            check("operations");
        } else if (id == R.id.nav_hrms) {
            check("hrms");
        } else if (id == R.id.nav_crm) {
            check("crm");
        } else if (id == R.id.nav_activities) {
//            startActivity(new Intent(getApplicationContext(),HrmsActivity.class));
        } else if (id == R.id.nav_financial) {

        } else if (id == R.id.nav_mytickets) {

        } else if (id == R.id.nav_Logout) {
            logout();
        } else if (id == R.id.nav_share) {
            share_it();
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_info) {
            startActivity(new Intent(getApplicationContext(), Version.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void share_it() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = CONSTANT.SHARE_IT_MSG;
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, CONSTANT.SHARE_IT_MSG);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void logout() {
        Preferences.removeAllPreference(MainHomeActivity.this);
        Intent intent = new Intent(MainHomeActivity.this, LoginActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkNetworkconnection();
        try {
            if (!imageP.equals("null")) {
                empImage.setImageBitmap(convertBasetoBitmap(imageP));

            } else if (!image_profile.equals("null")) {
                Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(empImage);
            } else {
                empImage.setImageDrawable(getResources().getDrawable(R.drawable.userimage));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!image_profile.equals("")) {
                Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(empImage);
            } else {
                Picasso.get().load(R.drawable.grayshade).into(empImage);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_home_drawer, menu);
        MenuItem itemInfo = menu.findItem(R.id.nav_info);
        itemInfo.setTitle("Version " + appVersion);
        return true;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(MainHomeActivity.this, CONSTANT.EMPID);
    }

    @Override
    public void showLoadingLayout() {
        progressDialog = new ProgressDialog(this) {
            @Override
            public void onBackPressed() {
                progressDialog.dismiss();
            }
        };
        progressDialog.setTitle("Data Fetching");
        progressDialog.setMessage("Wait for data loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void hideLoadingLayout() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof AppRoleResponse) {
            ArrayList<Module> modules = new ArrayList<>();
            AppRoleResponse appRoleResponse = (AppRoleResponse) o;
            if (appRoleResponse.getStatus().equals(CONSTANT.TRUE)) {
                moduleList.addAll(appRoleResponse.getModule());
                for (int i = 0; i < moduleList.size(); i++) {
                    String operation = moduleList.get(i).getFormName();
                    list.add(operation.toLowerCase());
                }
                Log.e("list: ", list.toString());

            }
            Log.e("ModuleList", new Gson().toJson(moduleList));

        }

    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }
}
