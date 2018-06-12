package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    String url = "http://webapi.axpresslogistics.com/api/webapi/Reset_Pass";
    EditText employee_code, emailId;
    Button submit_button;
    String employeeCodeValue, emailIdvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        employee_code = findViewById(R.id.input_employee_id);
        emailId = findViewById(R.id.input_email_id);
        submit_button = findViewById(R.id.btn_forgetPasswordBtn);


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeCodeValue = employee_code.getText().toString().trim();
                emailIdvalue = emailId.getText().toString().trim();

                if (employeeCodeValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the employee code!", Toast.LENGTH_SHORT).show();
                } else if (emailIdvalue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the email Id!", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class));
                    finish();
//                    }
                }
            }
        });

    }

}
