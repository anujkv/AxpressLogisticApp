package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.acitvities.ForgetPasswordActivity;
import com.example.it2.axpresslogisticapp.acitvities.LoginActivity;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    String url = "http://webapi.axpresslogistics.com/api/HRMS/Reset_Pass";
//    EditText editTextResetEmail,editTextResetID;
//    TextView textViewBackLinkId,textViewSubmitLinkId;
//    String strEmail,strEmpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

//        editTextResetEmail = findViewById(R.id.editTextResetEmail);
//        editTextResetID = findViewById(R.id.editTextResetID);
//        textViewBackLinkId = findViewById(R.id.textViewBackLinkId);
//        textViewSubmitLinkId = findViewById(R.id.textViewSubmitLinkId);
//
//        textViewBackLinkId.setOnClickListener(this);
//        textViewSubmitLinkId.setOnClickListener(this);
//
//        strEmail = editTextResetEmail.getText().toString().trim();
//        strEmpId = editTextResetID.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewBackLinkId:
//                if(strEmpId.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Kindly fill the Employee ID!.",Toast.LENGTH_SHORT).show();
//                } else if (strEmail.isEmpty())
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                finish();
                break;
            case R.id.textViewSubmitLinkId:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Password is default, same as Employee ID!.",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
