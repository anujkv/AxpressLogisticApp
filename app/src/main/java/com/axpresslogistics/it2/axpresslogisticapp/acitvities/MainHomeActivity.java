package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
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
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ImageConverter;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.DEVELOPMENT_URL;

public class MainHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener {
//    String USER_URL = URL + "HRMS/app_user";
    String USER_URL = DEVELOPMENT_URL + "HRMS/app_user";

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
    LocationManager locationManager;
    double lat,lon;
    ArrayList<String> list = new ArrayList<String>();


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

        user_permission_checks();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        check("Operations");
//                        if(list.contains("Operations")){
//                            startActivity(new Intent(getApplicationContext(),OperationActivity.class));
//                        }else{
//                            Toast.makeText(getApplicationContext(),"Sorry, you don't have permission!,contact with IT Department.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
                        break;
                    case 1:
                        check("hrms");

                        break;
                    case 2:
                        check("crm");

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
        ImageConverter imageConverter = new ImageConverter();
        String image_profile = Preferences.getPreference(MainHomeActivity.this, CONSTANT.EMPLOYEE_IMAGE);
        Bitmap image = imageConverter.StringToBitMap("iVBORw0KGgoAAAANSUhEUgAACZAAAAzACAIAAADYRynpAAAAA3NCSVQICAjb4U%252FgAAAgAElEQVR4nIy97ZLluo0tuAAq67j9qjMR87g35j26267KTWB%252BgAAXQeXxKOw6Sm2JxDcBECTl%252F%252Fyf%252F%252Ffz%252BZiZu%252F%252F6%252BscYY34%252B7v48z5wzbh7ReMHMRMTMAIwx4gkAERGROWfc5wNpL8QT5KWq7jbnVFVV%252FXw%252BAJ7nATDnrOfx8pzf7v6Pf%252FzDzOacIgLg169fcI8P3V1ExhiWXbj7xNGju%252F8az5xzjCEivz%252Ff0YuI6BjxZqGpUHf%252Fr79%252BxZOgRoFEyCLwCgCCPmMMAH%252FmR1Wf53H37%252B%252Fv53lE9fv7O96M7iQhV1UnOLmLgDZ6d%252Fu4%252BxhDVQMkF8w5f%252F365e5%252F%252Fvw7fjKzP3%252F%252BjDHG86saFBiDGmgWwUVcRIY80en393fwiF%252BLbxUIIlRT0UJIi6q6%252B5zTzIJ7hYiZBXHincAoPo9e4jXVp%252BD8nj7nfJ7neZ7P5zPnlKHxa4iciDzj15zz8%252Fmo6jNGtLNQzvvoyIHi%252BKJnXvWwPgyA3V0HAkIzE18MDSkq6rn75%252FNxn4XR4h0kaDjndF0wBxFEBBpvagEc0hu%252FRu%252FB7m%252Bb7h4AwiykQlWhT7wGANOW6KbWaDYSfAlNYQir3%252FjVzELCQ%252Bbd%252Ffn6CgTNTGSIiMmiZ1CSeQpgwAFMkGD4J16IxktCluJMMzPzT%252Bmpqo4xgEBKzez7%252BzskMOhZihby4CqhbkXYMUbcf319uUkxN7EQAGJS6LN2hwnSAXdXLOH566%252B%252F2MgA9vl83P3Xr19C6s8NigRVHwBii8JhxwBgqKra4qmUlIbdIyLAfDMrWLPkcGh94i5llxRS3Jlz%252FvOf%252F%252Fz9%252B3fZqNUOpIyYqD%252FP8%252F35hJap6rRvd%252F%252F1fIUAhD5%252BPp%252FP7z%252FB5QKYrXqwg2WvLOHn8zH7PM8jz%252Fj%252B%252Fv6en%252F2VQ1W%252FQsGwBc9Swpu9mtjkWixWKTWPrkM%252BzWZB%252BP37t%252BYlUDNz2CYmMMaY9h0G5Ovra41cE3NOgxc7ik1hz7%252B%252BvgCwTonIEI0hj4eJBViqcwHm7l9j8BBZ%252BlgdBQELwY95kZetnKp%252Bf38DXsoVnYrhGG5EHMYQxj0A8bDD3zWCfGyGgQ37U7aluG9mIPl0QWifu4%252FxFCKwPTgWBYKr0ZqZiXmJE2BF0iAvXyNJzexgakQHjZ4hFUW3usSklAVDR7oBX19fYRP%252B%252FPnzpaN0qohDA9kMmks6PB%252B30tYQgONSKRjCJMbY8TWegDkEu9gaOh7je%252BljIa5uJS3xSdjeAJKNxp%252FP95zz1%252FMlIh%252BbQdi%252F%252FvprPM%252F39%252FfzNQD8%252BfOnBr4QPIUU01l0QzD4IUsR4GY2%252FSMion4IpIjKIyI25%252Ff3t%252BgTGld6VJafjWrp17%252F%252B9e%252ByQuWCFt9LnDxdNZ%252FGNhbkuhRDSgUAqG9TWW%252BKSPnDqhqjUnYBd4%252FRmVV%252BfD1hhYRcrKCQqoYSMl7B5XX%252FWbbL3d2M3ZXwPT6fj7uN09VZN6J1P%252BGMJlmGB%252BVxpWGJcTYkf4zhZmW71K1IFLRkGsZIt%252FxYDOxRcos3i78lVDVSJH%252BWVW%252BemOcVA%252BLn83l0%252FPXXX3POf%252F3rX3%252F99Ze7m318%252B3iLF%252B4usnvJpgLsbYcNbmZjLH%252FY3cP%252FrCG%252BYBCokD9ZSsosEBmlp80EuXs4HSG3y5g4iLaHKrHwl60eoiEnquqCGqMl%252FXmR5doEGL9kGbTlTuT73Kn6djhLGLY06h7T4%252FPlKjzjf%252F7nf349X%252BGYkeephS9gbCVKMEo8kmjimGbmJBXrTfpqNUL01HGIorvDUG4DDxBuy8IE8d094gjEeKpL3spRjNeihY9vOB8%252FeCoiLubumhQAAF9uzBiDZbuoyu59I4iMQwxioCnA2JFYNlBxteOlO2F8QUMJXOec00PUd0j78XL2Nj15ENlaIKMkh8VvydtYYekYQ%252FUp17ok0ynUAqBjNPgdPfaPRsaIRxrhQMUUwGFtnudxR%252BkIpkW%252FJZ%252Fui1MFmKdGjzF8UjivLiLlmYQjHRA9z%252FPf%252F%252F3fz%252FOwQoX3WNamxjXP%252BGvOCRqMmOBbec1KfooIyxRot6vTPgAkx0FJY%252B4r8bIbN2x1EOy4VZ8vAH%252F%252B%252FLHMWpS08AjF7ZfdK5Wpl80%252BqhqONMzHGIqhqhHfBQUKLxKtNb6MMRzq7joO%252BTczBcYYqltaBgTA5xO6gAKehVNkhJCU3jmlpA45ly3%252FYeKwgmIrI5x02PEsZI135ew9Z3IDQESpX79%252BlZ8Z6h%252FPf%252F1jxdcB3hjj959%252FAwiTU3keMxvjS0Q%252Bf75LL8rQlao6JosWgIKwWPZnfszs169fQck%252Ff%252F6EfDp0jDGkhGe1LyGK8pTfWyOUqgrs%252B%252Fs7IA9EIlrh4YbHr%252FDuQtSXp%252FSIiNhcKkBRlQb6oUEcDAIY46v8ZORw%252FjwP0ihFa%252F%252F4xz9%252B%252F%252F69eU2WiqFaeKlXbkockc8Rkch7pEDCzCS%252FjdY%252Bn08NggEq6wtbPDaVnl6E%252BUfSwYMtQxStFY4t%252FAwlGmPAp5%252F%252BlcjRXRDT3XWN41JMTIYu6o0xRrnoqQXz4yKC9AEiR%252FQ8z%252Fdc7nrp3bKZvvN7y6ESq1FgmR1sj%252BKR7ecLpWhGPheKPtz9GaNMHIJE9q1XRqtIrWuM3t7U5%252FP5%252Fv7%252B5z%252F%252FWf0elyHw3b%252B6AnABRSg7BxL%252Bg4ytNaVullk%252BT8Pu7nIGIwtIVazcQDlO%252B1fyrED3VkQrJ0FEAoQmAOGwRVrs3%252F%252F%252B93%252F9139tgvv%252BdqntgLsP%252FXLKjJXcDmxdWC9QvBn8jRiq4A%252BjF40HqGOMz%252Fwz5%252Fz6%252Bgq8SmAi5%252Fbn810Alwa5%252ByRIsGITNTMMNbPfv3%252F%252F9ddfAUnlACWvEgymraePOucecFPqHgCVrkdGcKHpdQVsYcz%252F%252Bc9%252FquN%252F%252F%252Fd%252Fwx6GPLhK2PNqZFHj6wEg8G3WKNn158%252BfcicWg3BQtWHkmX8QkUHzL4C0HNTzPF9fX3%252F%252B%252FKnnTi4iaExcpgZw9zDywV9LD1NEFHtE9lTBZmCdRh9xADifu4h4MlpEVLcuW%252BaOnuf58%252Be3u2cmZLq7CYplZhCRf3z9%252Bve%252F%252Fx29%252F%252Fr16%252FP5hGdbBi0o8DyPhcJa9GIARnYrp2%252BgIBIlvgAG5VFBHhHGEcLMOcNglBp%252Bf39PM9boHduSG%252BM%252BkdlaSXeodM3syJZU78GF%252BjPG7jGGweeckXGVhLDQ%252FDM%252Fc86%252Ffv2XZ2Qajfx6nhAVVR1JoNAaNs5kQ7bHVV1sXfCVd%252Fr165eI%252FP79O7wadw%252BnMTxwVY1s2PLcbMv5kw2tNHENw%252ByeTjjjVpzgJ43NQuNHaVeNlGW83G2LfnpmpZbcmmU2oVoLUbD0gBs8Zf6bhSoLyMZreXvl5C3npiPIHxYkDCfTgSkQV7oGXh2pKii5YzSBV1EfKLAZY0yfJYjsO3rO15ahKTUoTCWHbc%252FBtQxHmAlVtY%252FdGIEUYM7p5%252Fxitdm4XyJeANRwVeOTk52tFkJRi7ZxU%252FgWUuUfGE2pWsZdTUTLexBK1gyaLWC2NvQPjkNwikEBIyIix3QOYXRo0C0b9dDPDBQzN25BI5aIfGjivCDePRIRPKd4WUR3O59PkaIgZ7IwqJqJeM4NSWZdVVWU%252BC572luoiGG9QPiWhLh7THE9z5HOCDicNFpVMbTwaujHfz39%252B4ZdMY6ZskyNirvblVxIglTu48UeklVIGc6E180pSYsfyeL6RDORbdd8R8lGGT0GMlouTfEc3hhUwSYCUwCnbxFP5pwxHNb4eqcGCnGKS1HyUHYj7A8DFtOrftL5hgEn06Wiu83tbUYaIprX%252BtC2BrEM3OisaOVS4fiwrBAHS020NrvJNjLX2k8lgY3dZ%252FjRk9qlOLjEmzuif4932vul8kztRocivoiUQ9nePCbqbH8y506phG6mDZkMwyvwN93ui%252BnJl1GWqjW1xTUZigxUbpBYPpsI1chb0ticCgay3qn7G7wbr%252BqriGA5oYV0Z8M55ndazFn3QfmwPK%252BmDIdBu6vN");
        empImage.setImageBitmap(image);

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void check(String call) {
        if(list.contains(call)){
//            if(call!= null){
                if(call.equals("Operations")){
                startActivity(new Intent(getApplicationContext(),OperationActivity.class));
            }
            else if(call.equals("hrms")){
                    startActivity(new Intent(getApplicationContext(),HrmsActivity.class));
                }
            else if(call.equals("crm")){
                startActivity(new Intent(getApplicationContext(),CRMActivity.class));
            }
        }else{
            Toast.makeText(getApplicationContext(),"Sorry, you don't have permission!,contact with IT Department.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void user(){

    }

    private void user_permission_checks() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        final String method = CONSTANT.APP_USER;
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.optString("status");
                    String apiResponse = object.optString("key");
                    Log.e("Module : ",response);

                    if(status.equals(CONSTANT.TRUE) && apiResponse.equals(key)){
                        progressDialog.dismiss();

                        JSONArray array = object.getJSONArray("Module");
                        for(int i = 0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            String operation = jsonObject.getString("module");
                            list.add(operation);
                        }
                        Log.e("list: ",list.toString());
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"credinital not found",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(CONSTANT.METHOD,method);
                params.put(CONSTANT.KEY,key);
                params.put(CONSTANT.EMPID,Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
                Log.e("method : ",method);
                Log.e("key : ",key);
                Log.e("EMPID : ",Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
            check("Operations");
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
