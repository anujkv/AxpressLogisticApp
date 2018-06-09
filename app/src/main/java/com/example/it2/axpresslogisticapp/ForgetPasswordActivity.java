package com.example.it2.axpresslogisticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.acitvities.LoginActivity;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText employee_code, name, emailId, contactNo, password, confirmPassword;
    Button submit_button;
    String employeeCodeValue, nameValue, emailIdvalue, contactNoValue, passwordValue, confirmPasswordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        employee_code = findViewById(R.id.input_employee_id);
        name = findViewById(R.id.input_name_id);
        emailId = findViewById(R.id.input_email_id);
        contactNo = findViewById(R.id.input_contact_id);
        password = findViewById(R.id.input_password_id);
//        confirmPassword = findViewById(R.id.input_confirm_password_id);
        submit_button = findViewById(R.id.btn_registration);


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeCodeValue = employee_code.getText().toString().trim();
                nameValue = name.getText().toString().trim();
                emailIdvalue = emailId.getText().toString().trim();
                contactNoValue = contactNo.getText().toString().trim();
                passwordValue = password.getText().toString().trim();
//                confirmPasswordValue = confirmPassword.getText().toString().trim();

                if (employeeCodeValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the employee code!", Toast.LENGTH_SHORT).show();
                } else if (nameValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the employee name!", Toast.LENGTH_SHORT).show();
                } else if (emailIdvalue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the email Id!", Toast.LENGTH_SHORT).show();
                } else if (contactNoValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the contact No.!", Toast.LENGTH_SHORT).show();
                } else if (passwordValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the password", Toast.LENGTH_SHORT).show();
                }
//                else if (confirmPasswordValue.isEmpty()){
//                    Toast.makeText(getApplication(),"Enter the confirm password",Toast.LENGTH_SHORT).show();
//                }

//                else{
//                    if(passwordValue!=confirmPasswordValue){
//
//                        Toast.makeText(getApplication(),"Password not match!!!",Toast.LENGTH_SHORT).show();
//                    }
                else {
                    Toast.makeText(getApplication(), "Mailed sent Successfully!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
//                    }
                }
            }
        });

    }

}
