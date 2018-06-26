package com.example.it2.axpresslogisticapp.acitvities;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LeaveInfoActivity extends AppCompatActivity {

    ArrayList<String> datafetchlist = new ArrayList<>();
    EditText input_editText;
    Button search_btn;
    String input_text = "";
    LinearLayout info;

    TextView empName, empId, branch, department, designation, contact, error_msg;
    String name,empid,branchname,dept,desig,contactno,errormsg;
    ImageView empImg;

    String url = "https://api.myjson.com/bins/9sp2i";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_info);
//        Toolbar toolbar =  findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);

        info = findViewById(R.id.info);

        input_editText = findViewById(R.id.input_edit_text_id);
        search_btn = findViewById(R.id.btn_search);
        error_msg = findViewById(R.id.employee_not_found_text_Id);

        empName = findViewById(R.id.user_nameId);
        empId = findViewById(R.id.user_id);
        branch = findViewById(R.id.branch_id);
        department = findViewById(R.id.department_id);
        designation = findViewById(R.id.designation_id);
        contact = findViewById(R.id.contact_id);

        requestQueue = Volley.newRequestQueue(this);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_editText.getText().toString().trim().toLowerCase().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the input!", Toast.LENGTH_SHORT).show();
                    error_msg.setVisibility(View.INVISIBLE);
                    info.setVisibility(View.INVISIBLE);
                } else {
                    input_text = input_editText.getText().toString().toLowerCase().trim();
                    //jsonfunction calling...
                    sendJsonRequest(input_text);

//                    get_data(input_text);
                    input_editText.setText("");
                }
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    public void sendJsonRequest(String input_text) {


        boolean VISIABLE_FLAG = false;
        boolean VISIABLITY_ERROR_FLAG  = false;
        this.input_text = input_text;
        info.setVisibility(View.VISIBLE);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response);

                    name = response.getString("name");
                    empid = response.getString("empid");
                    branchname = response.getString("branch");
                    dept = response.getString("department");
                    desig = response.getString("designation");
                    contactno = response.getString("contact");


                    empName.setText(name);
                    empId.setText(empid);
                    branch.setText(branchname);
                    department.setText(dept);
                    designation.setText(desig);
                    contact.setText(contactno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void get_data(String input_text) {
        boolean VISIABLE_FLAG = false;
        boolean VISIABLITY_ERROR_FLAG  = false;
        this.input_text = input_text;
        String json = null;
        JSONObject jsonObject = null;

        try {
            InputStream inputStream = getAssets().open("employeedetails.json");
            byte[] buffer = new byte[inputStream.available()];

            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("name").toLowerCase().equals(input_text) ||
                        jsonObject.getString("empid").toLowerCase().equals(input_text)) {
                    empId.setText(jsonObject.getString("empid"));
                    empName.setText(jsonObject.getString("name"));
                    branch.setText(jsonObject.getString("branch"));
                    department.setText(jsonObject.getString("department"));
                    designation.setText(jsonObject.getString("designation"));
                    contact.setText(jsonObject.getString("contact"));
                    VISIABLE_FLAG = true;

                }

            }
            if(VISIABLE_FLAG == true){
                info.setVisibility(View.VISIBLE);
                error_msg.setVisibility(View.INVISIBLE);
            }
            else{
                if(input_text.isEmpty()){
                    error_msg.setText(R.string.blank_field_error);
                    error_msg.setVisibility(View.VISIBLE);
                    info.setVisibility(View.INVISIBLE);
                }
                else {
                    error_msg.setText(R.string.employee_not_found);
                    error_msg.setVisibility(View.VISIBLE);
                    info.setVisibility(View.INVISIBLE);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
