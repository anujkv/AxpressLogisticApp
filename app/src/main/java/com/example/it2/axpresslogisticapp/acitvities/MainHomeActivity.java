package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.it2.axpresslogisticapp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String[] gridViewStrings = {
            "Operations",
            "HRMS",
            "CRM",
            "Activities",
            "Financial",
            "My Tickets",
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_operation,
            R.drawable.icon_hrms,
            R.drawable.icon_crm,
            R.drawable.icon_activities,
            R.drawable.icon_financial,
            R.drawable.icon_tickets,
    };
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    GridView gridView;
    Toolbar toolbar;
    Context context;
    ArrayList arrayList;
    TextView empEmailId,empName;
    ImageView empImage;
    String employeeID,employeeNAME,empEmail;
    Intent intent;
    String jsonString;
    JSONObject jObj;

    public Bundle getBundle = null;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);


        try {
            intent = getIntent();
            jsonString = intent.getStringExtra("response");
            jObj = new JSONObject(jsonString);
            employeeNAME = jObj.optString("Employee_Name");
            empEmail = jObj.optString("Employee_Email");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView = findViewById(R.id.grid);
//        Toast.makeText(getApplicationContext(),"I M IN MAIN: "+jsonString,Toast.LENGTH_SHORT).show();
        GridViewAdaptor gridViewAdaptor = new GridViewAdaptor(MainHomeActivity.this,
                gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewAdaptor);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        Intent intent_opt = new Intent(MainHomeActivity.this,
                                OperationActivity.class);
                        // passing array index
                        intent_opt.putExtra("id", position);
                        startActivity(intent_opt);
                        break;
                    case 1:
                        Intent intent_hrms = new Intent(MainHomeActivity.this,HrmsActivity.class);
                        intent_hrms.putExtra("response", jObj.toString());
                        startActivity(intent_hrms);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "No Activity " +
                                String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "No Activity " +
                                String.valueOf(position), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent profiledataIntent = new Intent(getApplicationContext(),EmpProfileActivity.class);
            profiledataIntent.putExtra("response", jObj.toString());
            startActivity(profiledataIntent);
//            startActivity(new Intent(getApplicationContext(),EmpProfileActivity.class));
        } else if (id == R.id.nav_operations) {
//            startActivity(new Intent(getApplicationContext(),OperationActivity.class));

        } else if (id == R.id.nav_hrms) {
//            startActivity(new Intent(getApplicationContext(),HrmsActivity.class));

        } else if (id == R.id.nav_crm) {

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
        finish();
    }


}
