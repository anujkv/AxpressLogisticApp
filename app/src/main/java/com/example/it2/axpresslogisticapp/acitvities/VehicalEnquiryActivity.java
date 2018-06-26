package com.example.it2.axpresslogisticapp.acitvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.it2.axpresslogisticapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class VehicalEnquiryActivity extends AppCompatActivity {

    TextView search_title_text,vehical_no,driver_name,driver_contact_no,vendor_name,vendor_contact_no
            ,branch_code;
    TextView vehical_no_title,driver_name_title,driver_contact_no_title,vendor_name_title,
            vendor_contact_no_title, branch_code_title,error_msg;
    EditText search_input;
    Button search_btn,edit_btn;
    LinearLayout view,image_layout;
    String input_text = "";

    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_enquiry);
//        Toolbar toolbar =  findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);

        search_input = findViewById(R.id.input_edit_text_id);
        search_title_text = findViewById(R.id.title);
        vehical_no_title = findViewById(R.id.name_title_txt);
        driver_name_title = findViewById(R.id.id_title_text);
        driver_contact_no_title = findViewById(R.id.branch_title_txt);
        vendor_name_title = findViewById(R.id.dept_title_txt);
        vendor_contact_no_title = findViewById(R.id.desig_title_txt);
        branch_code_title = findViewById(R.id.contact_title_txt);
//        imageView = findViewById(R.id.);
        view = findViewById(R.id.info);
        image_layout = findViewById(R.id.image_layout);
        error_msg = findViewById(R.id.employee_not_found_text_Id);


        search_btn = findViewById(R.id.btn_search);
        search_title_text.setText("Search Vehical");

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_input.getText().toString().trim().toLowerCase().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Enter the input!", Toast.LENGTH_SHORT).show();
                    error_msg.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.INVISIBLE);
                } else {
                    input_text = search_input.getText().toString().toLowerCase().trim();
                    get_data(input_text);
                    search_input.setText("");
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
            InputStream inputStream = getAssets().open("vehical_info.json");
            byte[] buffer = new byte[inputStream.available()];

            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("name").toLowerCase().equals(input_text) ||
                        jsonObject.getString("empid").toLowerCase().equals(input_text)) {

                    vehical_no_title.setText("Vehical No.");
                    driver_name_title.setText("Driver Name");
                    driver_contact_no_title.setText("Driver No.");
                    vendor_name_title.setText("Vendor Name");
                    vendor_contact_no_title.setText("Contact No.");
                    branch_code_title.setText("Branch Code");

                    vehical_no.setText("");
                    driver_name.setText("");
                    driver_contact_no.setText("");
                    vendor_name.setText("");
                    vendor_contact_no.setText("");
                    branch_code.setText("");
                    image_layout.removeAllViews();

                    view.setVisibility(View.VISIBLE);

                    vehical_no.setText(jsonObject.getString("empid"));
                    driver_name.setText(jsonObject.getString("name"));
                    driver_contact_no.setText(jsonObject.getString("branch"));
                    vendor_name.setText(jsonObject.getString("department"));
                    vendor_contact_no.setText(jsonObject.getString("designation"));
                    branch_code.setText(jsonObject.getString("contact"));
                    VISIABLE_FLAG = true;

                }

            }
            if(VISIABLE_FLAG == true){
                view.setVisibility(View.VISIBLE);
                error_msg.setVisibility(View.INVISIBLE);
            }
            else{
                if(input_text.isEmpty()){
                    error_msg.setText(R.string.blank_field_error);
                    error_msg.setVisibility(View.VISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
                else {
                    error_msg.setText(R.string.employee_not_found);
                    error_msg.setVisibility(View.VISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
