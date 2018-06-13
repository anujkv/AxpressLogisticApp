package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.it2.axpresslogisticapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class DocketEnquiry extends AppCompatActivity {
    private String url = "http://webapi.axpresslogistics.com/api/Operations/Docket_Invoice";
    Boolean FLAG = false;
    RadioGroup radio_group_id;
    RadioButton radio_btn_id;
    EditText input_editSearch_text_id;
    Button submit_docket_btn;
    String method, strInput_editSearch_text;
    Intent intent;
    String jsonString;
    JSONObject jObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_enquiry);

        //initialize_data_types..
        initialize_dataType();
        radio_group_id.clearCheck();

        submit_docket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strInput_editSearch_text = input_editSearch_text_id.getText().toString().trim();
                radio_group_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        radio_btn_id = group.findViewById(checkedId);
                        if (radio_btn_id != null) {
                            method = radio_btn_id.toString();
                        } else {
                            Toast.makeText(getApplicationContext(), "Choose the search type.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if (strInput_editSearch_text.isEmpty() || strInput_editSearch_text == null) {
                    Toast.makeText(getApplicationContext(), "Enter the Docket/Invoice No.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), method, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), strInput_editSearch_text, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DocketEnquiry.this,DocketTracking.class));
                }
            }
        });
    }

    private String saltStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private void initialize_dataType() {
        radio_group_id = findViewById(R.id.radio_group_id);
        input_editSearch_text_id = findViewById((R.id.input_editSearch_text_id));
        submit_docket_btn = findViewById(R.id.btn_search);
    }

}
