package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.example.it2.axpresslogisticapp.Utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener {
    public static String[] gridViewStrings = {
            "Operations",
            "HRMS",
            "CRM",
            "Activities",
            "Financial",
            "My Tickets"
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_operation,
            R.drawable.icon_hrms,
            R.drawable.icon_crm,
            R.drawable.icon_activities,
            R.drawable.icon_financial,
            R.drawable.icon_tickets
    };
    GridView gridView;
    Toolbar toolbar;
    TextView empEmailId,empName;
    ImageView empImage;
    String employeeID,employeeNAME,empEmail;
    Intent intent;
    JSONObject jObj;
    LocationManager locationManager;
    double lat,lon;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        employeeNAME= Preferences.getPreference(MainHomeActivity.this, CONSTANT.USER_NAME);
        empEmail= Preferences.getPreference(MainHomeActivity.this, CONSTANT.EMAIL);
        employeeID = Preferences.getPreference(MainHomeActivity.this, CONSTANT.EMPID);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView = findViewById(R.id.grid);
        GridViewAdaptor gridViewAdaptor = new GridViewAdaptor(MainHomeActivity.this,
                gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewAdaptor);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        startActivity(new Intent(getApplicationContext(),OperationActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),HrmsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(),CRMActivity.class));
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Activities",
                                Toast.LENGTH_SHORT).show();
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

        View navView =  navigationView.getHeaderView(0);
        //reference to views

        empEmailId = navView.findViewById(R.id.user_email);
        empName = navView.findViewById(R.id.user_name);
        empImage = navView.findViewById(R.id.user_imageView);
        //set views
        empName.setText(employeeNAME);
        empEmailId.setText(empEmail);

        navigationView.setNavigationItemSelectedListener(this);

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
            Intent profiledataIntent = new Intent(getApplicationContext(),EmpProfileActivity.class);
            profiledataIntent.putExtra("response", Preferences.getPreference(MainHomeActivity.this,"response"));
            startActivity(profiledataIntent);
//            startActivity(new Intent(getApplicationContext(),EmpProfileActivity.class));
        } else if (id == R.id.nav_operations) {
            startActivity(new Intent(MainHomeActivity.this,OperationActivity.class));
        } else if (id == R.id.nav_hrms) {
           startActivity(new Intent(MainHomeActivity.this,HrmsActivity.class));
        } else if (id == R.id.nav_crm) {
            startActivity(new Intent(MainHomeActivity.this,CRMActivity.class));
        } else if (id == R.id.nav_activities) {
//            startActivity(new Intent(getApplicationContext(),HrmsActivity.class));
        } else if (id == R.id.nav_financial) {

        } else if (id == R.id.nav_mytickets) {

        } else if (id == R.id.nav_Logout) {
            logout();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        Preferences.removeAllPreference(MainHomeActivity.this);
        Intent intent =new Intent(MainHomeActivity.this,LoginActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon= location.getLongitude();
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
}
